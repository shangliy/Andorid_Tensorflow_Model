/* Copyright 2015 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package org.tensorflow.demo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.graphics.Paint;

import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import junit.framework.Assert;

import org.tensorflow.demo.env.ImageUtils;
import org.tensorflow.demo.env.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Class that takes in preview frames and converts the image to Bitmaps to process with Tensorflow.
 */
public class TensorFlowImageListener implements OnImageAvailableListener {
  private static final Logger LOGGER = new Logger();

  private static final boolean SAVE_PREVIEW_BITMAP = false;

  // These are the settings for the original v1 Inception model. If you want to
  // use a model that's been produced from the TensorFlow for Poets codelab,
  // you'll need to set IMAGE_SIZE = 299, IMAGE_MEAN = 128, IMAGE_STD = 128,
  // INPUT_NAME = "Mul:0", and OUTPUT_NAME = "final_result:0".
  // You'll also need to update the MODEL_FILE and LABEL_FILE paths to point to
  // the ones you produced.
  private static final int NUM_CLASSES = 2;
  private static final int INPUT_SIZE = 299;
  private static final int IMAGE_MEAN = 128;
  private static final float IMAGE_STD = 128;
  private static final String INPUT_NAME = "div_1";
  private static final String OUTPUT_NAME = "decoder_2/output_boxes";

  private static final String MODEL_FILE = "file:///android_asset/tensor_12_stripped.pb";
  private static final String LABEL_FILE =
      "file:///android_asset/imagenet_comp_graph_label_strings.txt";

  private Integer sensorOrientation;

  private final TensorFlowClassifier tensorflow = new TensorFlowClassifier();

  private int previewWidth = 0;
  private int previewHeight = 0;
  private byte[][] yuvBytes;
  private int[] rgbBytes = null;
  private Bitmap rgbFrameBitmap = null;
  private Bitmap croppedBitmap = null;
  private Bitmap uploadcroppedBitmap = null;
  private Bitmap uploadresizedBitmap = null;

  private boolean computing = false;
  private Handler handler;

  private RecognitionScoreView scoreView;
  private SurfaceView boxView;
  protected SurfaceHolder sh;
  public RectF location;
  public boolean flag = false;
  public boolean usingcropped_flag = false;
  private  boolean feature_ready;
  private  boolean result_ready = false;

  int real_width;
  int real_height;
  public String Features;
  public String classids;
  public RectF cropped_cor;

  public void initialize(
      final AssetManager assetManager,
      final RecognitionScoreView scoreView,
      final SurfaceView boxView,
      final Handler handler,
      final Integer sensorOrientation,
      final int truewidth,
      final int trueheight
      ) {

    Assert.assertNotNull(sensorOrientation);
    tensorflow.initializeTensorFlow(
        assetManager, MODEL_FILE, LABEL_FILE, NUM_CLASSES, INPUT_SIZE, IMAGE_MEAN, IMAGE_STD,
        INPUT_NAME, OUTPUT_NAME);
    this.scoreView = scoreView;
    this.boxView = boxView;
    this.handler = handler;
    this.sensorOrientation = sensorOrientation;
    this.real_width =truewidth;
    this.real_height =trueheight;
  }

  private void drawResizedBitmap(final Bitmap src, final Bitmap dst) {
    Assert.assertEquals(dst.getWidth(), dst.getHeight());
    final float minDim = Math.min(src.getWidth(), src.getHeight());

    final Matrix matrix = new Matrix();

    // We only want the center square out of the original rectangle.
    final float translateX = -Math.max(0, (src.getWidth() - minDim) / 2);
    final float translateY = -Math.max(0, (src.getHeight() - minDim) / 2);
    matrix.preTranslate(translateX, translateY);


    final float scaleFactor = dst.getHeight() / minDim;
    matrix.postScale(scaleFactor, scaleFactor);

    // Rotate around the center if necessary.
    if (sensorOrientation != 0) {
      matrix.postTranslate(-dst.getWidth() / 2.0f, -dst.getHeight() / 2.0f);
      matrix.postRotate(sensorOrientation);
      matrix.postTranslate(dst.getWidth() / 2.0f, dst.getHeight() / 2.0f);
    }

    final Canvas canvas = new Canvas(dst);
    canvas.drawBitmap(src, matrix, null);

  }


  @Override
  public void onImageAvailable(final ImageReader reader) {
    Image image = null;
    try {
      image = reader.acquireLatestImage();

      if (image == null) {
        return;
      }

      // No mutex needed as this method is not reentrant.
      if (computing) {
        image.close();
        return;
      }
      computing = true;

      Trace.beginSection("imageAvailable");

      final Plane[] planes = image.getPlanes();
      LOGGER.i("width is %d",previewWidth );
      // Initialize the storage bitmaps once when the resolution is known.
      if (previewWidth != image.getWidth() || previewHeight != image.getHeight()) {
        previewWidth = image.getWidth();

        previewHeight = image.getHeight();

        LOGGER.i("Initializing at size %dx%d", previewWidth, previewHeight);
        rgbBytes = new int[previewWidth * previewHeight];
        rgbFrameBitmap = Bitmap.createBitmap(previewWidth, previewHeight, Config.ARGB_8888);
        croppedBitmap = Bitmap.createBitmap(INPUT_SIZE, INPUT_SIZE, Config.ARGB_8888);

        yuvBytes = new byte[planes.length][];
        for (int i = 0; i < planes.length; ++i) {
          yuvBytes[i] = new byte[planes[i].getBuffer().capacity()];
        }
      }

      for (int i = 0; i < planes.length; ++i) {
        planes[i].getBuffer().get(yuvBytes[i]);
      }

      final int yRowStride = planes[0].getRowStride();
      final int uvRowStride = planes[1].getRowStride();
      final int uvPixelStride = planes[1].getPixelStride();
      ImageUtils.convertYUV420ToARGB8888(
          yuvBytes[0],
          yuvBytes[1],
          yuvBytes[2],
          rgbBytes,
          previewWidth,
          previewHeight,
          yRowStride,
          uvRowStride,
          uvPixelStride,
          false);

      image.close();
    } catch (final Exception e) {
      if (image != null) {
        image.close();
      }
      LOGGER.e(e, "Exception!");
      Trace.endSection();
      return;
    }

    rgbFrameBitmap.setPixels(rgbBytes, 0, previewWidth, 0, 0, previewWidth, previewHeight);
    drawResizedBitmap(rgbFrameBitmap, croppedBitmap);

    if (usingcropped_flag)
    {
      int ctop = (int)cropped_cor.top;
      int cleft = (int)cropped_cor.left;
      int cright = (int)cropped_cor.right;
      int cbot = (int)cropped_cor.bottom;
      LOGGER.i(" cropped left: " + ctop);
      LOGGER.i(" cropped cright: " + cbot);
      if (cbot>298) cbot = 298;

      uploadcroppedBitmap = Bitmap.createBitmap(croppedBitmap,cleft,ctop,cright-cleft,cbot-ctop);
      uploadresizedBitmap = Bitmap.createScaledBitmap(uploadcroppedBitmap, INPUT_SIZE, INPUT_SIZE, true);

    }
    // For examining the actual TF input.
    if (SAVE_PREVIEW_BITMAP) {
      if (usingcropped_flag)
      {
        ImageUtils.saveBitmap(uploadresizedBitmap);

      }
      else
      ImageUtils.saveBitmap(croppedBitmap);
    }

    handler.post(
        new Runnable() {
          @Override
          public void run() {
            final List<Classifier.Recognition> results;
            if (usingcropped_flag)
            {
              //results = tensorflow.recognizeImage(uploadresizedBitmap,1);
              results = tensorflow.recognizeImage(uploadresizedBitmap,1);
              result_ready = true;
            }
            else
            {
               results = tensorflow.recognizeImage(croppedBitmap,0);
            }

            sh = boxView.getHolder();
            Canvas canvas = sh.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(10);

            //canvas.drawPoint(100.0f, 100.0f, p);
            float confi = 0;
            //LOGGER.v("%d results", results.size());
            for (final Classifier.Recognition result : results) {
              confi =  result.getConfidence();
              if (confi > 0.5)
              {
               p.setColor(Color.GREEN);
                flag = true;
              }
              else
              {
                if (!usingcropped_flag) flag = false;
                p.setColor(Color.RED);
              }
              String s = result.getTitle();
              if (usingcropped_flag)
              {
                if (result_ready) {
                  Features = result.getFeatures();
                  classids = result.getClassid();
                  feature_ready = true;
                  result_ready = false;
                }


              }
              //Features = result.getFeatures();
              //LOGGER.i(" Features: " + result.getFeatures());

              final StringTokenizer st = new StringTokenizer(s);

              if (!st.hasMoreTokens()) {
                continue;
              }


              final String object = st.nextToken();
              //final String confin = st.nextToken();
              final float left = Float.parseFloat(st.nextToken());
              final float right = Float.parseFloat(st.nextToken());
              final float  top = Float.parseFloat(st.nextToken());
              final float  bot = Float.parseFloat(st.nextToken());
              /*
              float left_fin = (left)*real_width/INPUT_SIZE ;
              float top_fin = top *real_height/(INPUT_SIZE)+real_height/4;

              float right_fin = (right )*real_width/INPUT_SIZE;
              float bot_fin = bot*real_height/INPUT_SIZE+real_height/4;
              if (bot_fin > real_height) bot_fin = real_height-1;

              float left_fin_x = left_fin;
              float right_fin_x = right_fin;

              //left_fin_x = left_fin - (right_fin-left_fin)/2;
              //right_fin_x = 3*right_fin/2 - left_fin/2;
              */
              float resolution =30;

              float left_fin = ((float)Math.round(left/resolution)*resolution*real_width/INPUT_SIZE) ;
              float top_fin = (float)Math.round(top/resolution)*resolution *real_height/(INPUT_SIZE)+real_height/6;

              float right_fin = ((float)Math.round(right/resolution)*resolution )*real_width/INPUT_SIZE;
              float bot_fin = (float)Math.round(bot/resolution)*resolution*real_height/INPUT_SIZE+real_height/6;
              if (bot_fin > real_height) bot_fin = real_height-1;

              float left_fin_x = left_fin;
              float right_fin_x = right_fin;

              if (!usingcropped_flag)
              {
                if (confi > 0.15 ) {
                  canvas.drawRect(left_fin_x, top_fin, right_fin_x, bot_fin, p);
                  location = new RectF();
                  location.set(left_fin_x, top_fin, right_fin_x, bot_fin);
                }
              }


              //LOGGER.v("Result: " + result.getTitle());
            }
            /*
            if (confi <= 0.5) {
              ArrayList<Classifier.Recognition> black = new ArrayList<Classifier.Recognition>();
              scoreView.setResults(black);
            }
            else {
              scoreView.setResults(results);
            }
            */

            sh.unlockCanvasAndPost(canvas);
            computing = false;
          }
        });

    Trace.endSection();
  }

  public String getFeatures() {

    return Features;  }

  public String getclass() {

    return classids;  }

  public boolean featureset(){
    if (feature_ready )
    {
      feature_ready = false;
      return true;
    }
    else return false;

  }
}
