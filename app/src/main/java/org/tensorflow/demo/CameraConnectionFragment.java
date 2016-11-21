/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.demo.env.Logger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CameraConnectionFragment extends Fragment {
  private static final Logger LOGGER = new Logger();

  /**
   * The camera preview size will be chosen to be the smallest frame by pixel size capable of
   * containing a DESIRED_SIZE x DESIRED_SIZE square.
   */
  private static final int MINIMUM_PREVIEW_SIZE = 320;


  private RecognitionScoreView scoreView;


  /**
   * Conversion from screen rotation to JPEG orientation.
   */
  private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
  private static final String FRAGMENT_DIALOG = "dialog";
  private Button takePictureButton;

    private Handler myBackgroundHandler;
    private HandlerThread myBackgroundThread;



  static {
    ORIENTATIONS.append(Surface.ROTATION_0, 90);
    ORIENTATIONS.append(Surface.ROTATION_90, 0);
    ORIENTATIONS.append(Surface.ROTATION_180, 270);
    ORIENTATIONS.append(Surface.ROTATION_270, 180);
  }

  //ImageLoader imageLoader=new ImageLoader();


  /**
   * {@link android.view.TextureView.SurfaceTextureListener} handles several lifecycle events on a
   * {@link TextureView}.
   */
  private final TextureView.SurfaceTextureListener surfaceTextureListener =
      new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(
            final SurfaceTexture texture, final int width, final int height) {
          openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(
            final SurfaceTexture texture, final int width, final int height) {
          configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(final SurfaceTexture texture) {
          return true;
        }

        @Override
        public void onSurfaceTextureUpdated(final SurfaceTexture texture) {}
      };

  /**
   * ID of the current {@link CameraDevice}.
   */
  private String cameraId;

  /**
   * An {@link AutoFitTextureView} for camera preview.
   */
  private AutoFitTextureView textureView;
  //private AutoFitTextureView boxView;

  /**
   * A {@link CameraCaptureSession } for camera preview.
   */
  private CameraCaptureSession captureSession;

  /**
   * A reference to the opened {@link CameraDevice}.
   */
  private CameraDevice cameraDevice;

  /**
   * The rotation in degrees of the camera sensor from the display. 
   */
  private Integer sensorOrientation;
  
  /**
   * The {@link android.util.Size} of camera preview.
   */
  private Size previewSize;

  /**
   * {@link android.hardware.camera2.CameraDevice.StateCallback}
   * is called when {@link CameraDevice} changes its state.
   */
  private final CameraDevice.StateCallback stateCallback =
      new CameraDevice.StateCallback() {
        @Override
        public void onOpened(final CameraDevice cd) {
          // This method is called when the camera is opened.  We start camera preview here.
          cameraOpenCloseLock.release();
          cameraDevice = cd;
          createCameraPreviewSession();
        }

        @Override
        public void onDisconnected(final CameraDevice cd) {
          cameraOpenCloseLock.release();
          cd.close();
          cameraDevice = null;
        }

        @Override
        public void onError(final CameraDevice cd, final int error) {
          cameraOpenCloseLock.release();
          cd.close();
          cameraDevice = null;
          final Activity activity = getActivity();
          if (null != activity) {
            activity.finish();
          }
        }
      };

  /**
   * An additional thread for running tasks that shouldn't block the UI.
   */
  private HandlerThread backgroundThread;

  /**
   * A {@link Handler} for running tasks in the background.
   */
  private Handler backgroundHandler;

  /**
   * An additional thread for running inference so as not to block the camera.
   */
  private HandlerThread inferenceThread;

  /**
   * A {@link Handler} for running tasks in the background.
   */
  private Handler inferenceHandler;

  /**
   * An {@link ImageReader} that handles preview frame capture.
   */
  private ImageReader previewReader;

  /**
   * {@link android.hardware.camera2.CaptureRequest.Builder} for the camera preview
   */
  private CaptureRequest.Builder previewRequestBuilder;

  /**
   * {@link CaptureRequest} generated by {@link #previewRequestBuilder}
   */
  private CaptureRequest previewRequest;

  /**
   * A {@link Semaphore} to prevent the app from exiting before closing the camera.
   */
  private final Semaphore cameraOpenCloseLock = new Semaphore(1);

  /**
   * Shows a {@link Toast} on the UI thread.
   *
   * @param text The message to show
   */
  private void showToast(final String text) {
    final Activity activity = getActivity();
    if (activity != null) {
      activity.runOnUiThread(
          new Runnable() {
            @Override
            public void run() {
              Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
          });
    }
  }



  /**
   * Given {@code choices} of {@code Size}s supported by a camera, chooses the smallest one whose
   * width and height are at least as large as the respective requested values, and whose aspect
   * ratio matches with the specified value.
   *
   * @param choices     The list of sizes that the camera supports for the intended output class
   * @param width       The minimum desired width
   * @param height      The minimum desired height
   * @param aspectRatio The aspect ratio
   * @return The optimal {@code Size}, or an arbitrary one if none were big enough
   */
  private static Size chooseOptimalSize(
      final Size[] choices, final int width, final int height, final Size aspectRatio) {
    // Collect the supported resolutions that are at least as big as the preview Surface
    final List<Size> bigEnough = new ArrayList<Size>();
    for (final Size option : choices) {
      if (option.getHeight() >= MINIMUM_PREVIEW_SIZE && option.getWidth() >= MINIMUM_PREVIEW_SIZE) {
        LOGGER.i("Adding size: " + option.getWidth() + "x" + option.getHeight());
        bigEnough.add(option);
      } else {
        LOGGER.i("Not adding size: " + option.getWidth() + "x" + option.getHeight());
      }
    }

    // Pick the smallest of those, assuming we found any
    if (bigEnough.size() > 0) {
      final Size chosenSize = Collections.min(bigEnough, new CompareSizesByArea());
      LOGGER.i("Chosen size: " + chosenSize.getWidth() + "x" + chosenSize.getHeight());
      return chosenSize;
    } else {
      LOGGER.e("Couldn't find any suitable preview size");
      return choices[0];
    }
  }

  public static CameraConnectionFragment newInstance() {
    return new CameraConnectionFragment();
  }

  @Override
  public View onCreateView(
      final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    return inflater.inflate(R.layout.camera_connection_fragment, container, false);
  }



  private SurfaceView boxView;
  private SurfaceView cropImageView;
  WebView[] web = new WebView[4];
  String img_url;
  boolean show_imge = false;
  String show_list = "";
  @Override
  public void onViewCreated(final View view, final Bundle savedInstanceState) {
    textureView = (AutoFitTextureView) view.findViewById(R.id.texture);
    scoreView = (RecognitionScoreView) view.findViewById(R.id.results);
    cropImageView = (SurfaceView) view.findViewById(R.id.cropImage);
    boxView =  (SurfaceView) view.findViewById(R.id.box);
    web[0] = (WebView) view.findViewById(R.id.similar_img_1);
    web[1] = (WebView) view.findViewById(R.id.similar_img_2);
    web[2] = (WebView) view.findViewById(R.id.similar_img_3);
    web[3] = (WebView) view.findViewById(R.id.similar_img_4);

    boxView.setZOrderOnTop(true);    // necessary
    SurfaceHolder sfhTrackHolder = boxView.getHolder();
    sfhTrackHolder.setFormat(PixelFormat.TRANSPARENT);
    cropImageView.setZOrderOnTop(true);    // necessary
    SurfaceHolder crTrackHolder = cropImageView.getHolder();
    crTrackHolder.setFormat(PixelFormat.TRANSPARENT);

      // Button usage
      takePictureButton = (Button)view.findViewById(R.id.btnUpload);
      assert takePictureButton!=null;
      takePictureButton.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
            takePicturenet feedTask = new takePicturenet();




            feedTask.execute();
          }
      });



  }

  public void webviewimag(){
    if (show_list != null){

      final String[] tokens = show_list.split(" ");
      show_list = "";
      int width=200;
      int height = 200;
      String data = "";
      for (int i = 0; i < Math.min(tokens.length,4);i++)

      {
        web[i].getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        data="<img src="+tokens[i]+" style='width:"+width+"px;height:"+height+"px' />";
        web[i].loadData(data, "text/html", "utf-8");
      }



    }
    else{
      for (int i = 0; i < 4;i++)

      {
        web[i].loadUrl("about:blank");
      }
    }
    onPause();

  }
  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onResume() {
    super.onResume();
    startBackgroundThread();

    // When the screen is turned off and turned back on, the SurfaceTexture is already
    // available, and "onSurfaceTextureAvailable" will not be called. In that case, we can open
    // a camera and start preview from here (otherwise, we wait until the surface is ready in
    // the SurfaceTextureListener).
    if (textureView.isAvailable()) {
      openCamera(textureView.getWidth(), textureView.getHeight());
    } else {
      textureView.setSurfaceTextureListener(surfaceTextureListener);
    }
  }

  @Override
  public void onPause() {
    closeCamera();
    stopBackgroundThread();
    super.onPause();
  }

  /**
   * Sets up member variables related to camera.
   *
   * @param width  The width of available size for camera preview
   * @param height The height of available size for camera preview
   */
  private void setUpCameraOutputs(final int width, final int height) {
    final Activity activity = getActivity();
    final CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
    try {
      for (final String cameraId : manager.getCameraIdList()) {
        final CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

        // We don't use a front facing camera in this sample.
        final Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
        if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
          continue;
        }

        final StreamConfigurationMap map =
            characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

        if (map == null) {
          continue;
        }

        // For still image captures, we use the largest available size.
        final Size largest =
            Collections.max(
                Arrays.asList(map.getOutputSizes(ImageFormat.YUV_420_888)),
                new CompareSizesByArea());

        sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        
        // Danger, W.R.! Attempting to use too large a preview size could  exceed the camera
        // bus' bandwidth limitation, resulting in gorgeous previews but the storage of
        // garbage capture data.
        previewSize =
            chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height, largest);

        // We fit the aspect ratio of TextureView to the size of preview we picked.
        final int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
          textureView.setAspectRatio(previewSize.getWidth(), previewSize.getHeight());
        } else {
          textureView.setAspectRatio(previewSize.getHeight(), previewSize.getWidth());
        }

        CameraConnectionFragment.this.cameraId = cameraId;
        return;
      }
    } catch (final CameraAccessException e) {
      LOGGER.e(e, "Exception!");
    } catch (final NullPointerException e) {
      // Currently an NPE is thrown when the Camera2API is used but not supported on the
      // device this code runs.
      ErrorDialog.newInstance(getString(R.string.camera_error))
          .show(getChildFragmentManager(), FRAGMENT_DIALOG);
    }
  }
  private Uri picUri;
  protected SurfaceHolder crop;
  protected SurfaceHolder box_sh;
  boolean isPlaying = true;
  boolean isUploading = true;
  RectF corped_location;





  private class takePicturenet extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPostExecute(Void result) {
      //Task you want to do on UIThread after completing Network operation
      //onPostExecute is called after doInBackground finishes its task.
      if (isPlaying) {
        if (tfPreviewListener.flag)
        {

          isUploading = true;
          webviewimag();


          box_sh = boxView.getHolder();

          Canvas canvas_box = box_sh.lockCanvas();
          canvas_box.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
          box_sh.unlockCanvasAndPost(canvas_box);

          crop = cropImageView.getHolder();
          Canvas canvas = crop.lockCanvas();
          canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
          Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
          Paint paint = new Paint();
          paint.setColor(0xcc000000);

          Paint  transparentPaint = new Paint();
          transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
          transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
          canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
          canvas.drawRect( corped_location, transparentPaint);
          Paint p = new Paint();
          canvas.drawBitmap(bitmap, 0, 0, p);
          crop.unlockCanvasAndPost(canvas);
        }
        show_imge = false;
        isPlaying = !isPlaying;


      }

      else
      {
        for (int i = 0; i < 4;i++)

        {
          web[i].loadUrl("about:blank");
        }
        crop = cropImageView.getHolder();
        Canvas canvas = crop.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        crop.unlockCanvasAndPost(canvas);
        isPlaying = !isPlaying;
        onResume();
      }


    }

    @Override
    protected Void doInBackground(Void... params) {
      //Do your network operation here

      if ( isUploading)
      {
        try {
          corped_location = tfPreviewListener.location;
          tfPreviewListener.cropped_cor = new RectF(corped_location.left*299/1440,corped_location.top*299/1920,corped_location.right*299/1440,corped_location.bottom*299/1920);

          tfPreviewListener.usingcropped_flag = true;


          URL url = new URL("http://54.183.239.12:3000/annapi/");
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setDoOutput(true);
          conn.setRequestMethod("POST");
          conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

          if (isPlaying) {
            while (!tfPreviewListener.featureset()) {
            }
          }
          String feature = tfPreviewListener.getFeatures();
          String class_ids = tfPreviewListener.getclass();
          tfPreviewListener.usingcropped_flag = false;
          closeCamera();


          StringBuilder sb = new StringBuilder();
          sb.append("feature").append("=").append(URLEncoder.encode(feature, "utf-8"));
          sb.append("&");
          sb.append("prediction").append("=").append(URLEncoder.encode(class_ids, "utf-8"));

          System.out.println(feature);
          System.out.println(class_ids);
          JSONObject jsonObject = new JSONObject();
          byte[] entity = sb.toString().getBytes();

          try {
            jsonObject.put("feature",feature);
            jsonObject.put("prediction",class_ids);
          }
          catch (JSONException e)
          {}
          String input = jsonObject.toString();
          OutputStream os = conn.getOutputStream();
          //os.write(input.getBytes());
          os.write(entity);
          os.flush();

          //if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
          //  throw new RuntimeException("Failed : HTTP error code : "
          //          + conn.getResponseCode());
          //}

          BufferedReader br = new BufferedReader(new InputStreamReader(
                  (conn.getInputStream())));

          String output="";
          String results = "";
          show_list = "";

          System.out.println("Output from Server .... \n");


          while ((output = br.readLine()) != null) {

            //System.out.println(output);
            results = results + output;

          }

          System.out.println(results);
          try {

            JSONObject obj = new JSONObject(results);


            try {

              JSONArray jasondata = obj.getJSONArray("data");
              String[] arr=new String[jasondata.length()];

              for(int i=0;i<jasondata.length();i++) {
                JSONObject obj_each = jasondata.optJSONObject(i);
                JSONArray image_list = obj_each.getJSONArray("reference_image_links");

                arr[i]=image_list.getString(0);
                show_list = show_list + arr[i] +" ";



                Log.d("My App", arr[i]);
              }


            }
            catch (JSONException e)
            {}

          } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + results + "\"");
          }

          //imageLoader.displayImage(imageUrls[position], holder.image, null);


          conn.disconnect();
          //isUploading = false;
          //isPlaying = !isPlaying;
          tfPreviewListener.usingcropped_flag = false;


        } catch (MalformedURLException e) {
          e.printStackTrace();
        } catch (ProtocolException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      return null;
    }
  }

  public void takePicture()
  {


    if (isPlaying) {

      box_sh = boxView.getHolder();

      Canvas canvas_box = box_sh.lockCanvas();
      canvas_box.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
      box_sh.unlockCanvasAndPost(canvas_box);

      crop = cropImageView.getHolder();
      Canvas canvas = crop.lockCanvas();
      canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
      Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
      Paint paint = new Paint();
      paint.setColor(0xcc000000);

      Paint  transparentPaint = new Paint();
      transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
      transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
      canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
      canvas.drawRect(tfPreviewListener.location, transparentPaint);
      Paint p = new Paint();
      canvas.drawBitmap(bitmap, 0, 0, p);
      crop.unlockCanvasAndPost(canvas);

      try {
        URL url = new URL("http://50.23.125.197:3000/ibmannapi");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        //os.write(input.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
          throw new RuntimeException("Failed : HTTP error code : "
                  + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
          System.out.println(output);
        }

        conn.disconnect();

      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (ProtocolException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      //onPause();


    }

    else
    {
      onResume();
      crop = cropImageView.getHolder();
      Canvas canvas = crop.lockCanvas();
      canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
      crop.unlockCanvasAndPost(canvas);
    }



    //
  }
/*
  @Override
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode){
      case 1: {
        //Wysie_Soh: After an image is taken and saved to the location of mImageCaptureUri, come here
        //and load the crop editor, with the necessary parameters (96x96, 1:1 ratio)

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setClassName("org.tensorflow.demo", "org.tensorflow.demo.CameraConnectionFragment");

        intent.setData(picUri);
        intent.putExtra("outputX", 96);
        intent.putExtra("outputY", 96);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);

        break;

      }

      case 2:{

        final Bundle extras = data.getExtras();

        if (extras != null) {
          Bitmap photo = extras.getParcelable("data");

          cropImageView.setImageBitmap(photo);
        }

        File f = new File(picUri.getPath());
        if (f.exists()) {
          f.delete();
        }


        break;
      }
    }


  }
*/
/*
    private void performCrop(){

        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch(ActivityNotFoundException e){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    */
  /**
   * Opens the camera specified by {@link CameraConnectionFragment#cameraId}.
   */
  private void openCamera(final int width, final int height) {
    setUpCameraOutputs(width, height);
    configureTransform(width, height);
    final Activity activity = getActivity();
    final CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
    try {
      if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
        throw new RuntimeException("Time out waiting to lock camera opening.");
      }
      manager.openCamera(cameraId, stateCallback, backgroundHandler);
    } catch (final CameraAccessException e) {
      LOGGER.e(e, "Exception!");
    } catch (final InterruptedException e) {
      throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
    }
  }

  /**
   * Closes the current {@link CameraDevice}.
   */
  private void closeCamera() {
    try {
      cameraOpenCloseLock.acquire();
      if (null != captureSession) {
        captureSession.close();
        captureSession = null;
      }
      if (null != cameraDevice) {
        cameraDevice.close();
        cameraDevice = null;
      }
      if (null != previewReader) {
        previewReader.close();
        previewReader = null;
      }
    } catch (final InterruptedException e) {
      throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
    } finally {
      cameraOpenCloseLock.release();
    }
  }

  /**
   * Starts a background thread and its {@link Handler}.
   */
  private void startBackgroundThread() {
    backgroundThread = new HandlerThread("ImageListener");
    backgroundThread.start();
    backgroundHandler = new Handler(backgroundThread.getLooper());

    inferenceThread = new HandlerThread("InferenceThread");
    inferenceThread.start();
    inferenceHandler = new Handler(inferenceThread.getLooper());
  }

  /**
   * Stops the background thread and its {@link Handler}.
   */
  private void stopBackgroundThread() {
    backgroundThread.quitSafely();
    inferenceThread.quitSafely();
    try {
      backgroundThread.join();
      backgroundThread = null;
      backgroundHandler = null;

      inferenceThread.join();
      inferenceThread = null;
      inferenceThread = null;
    } catch (final InterruptedException e) {
      LOGGER.e(e, "Exception!");
    }
  }

  private final TensorFlowImageListener tfPreviewListener = new TensorFlowImageListener();

  private final CameraCaptureSession.CaptureCallback captureCallback =
      new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureProgressed(
            final CameraCaptureSession session,
            final CaptureRequest request,
            final CaptureResult partialResult) {}

        @Override
        public void onCaptureCompleted(
            final CameraCaptureSession session,
            final CaptureRequest request,
            final TotalCaptureResult result) {}
      };

  /**
   * Creates a new {@link CameraCaptureSession} for camera preview.
   */
  private void createCameraPreviewSession() {
    try {
      final SurfaceTexture texture = textureView.getSurfaceTexture();
      assert texture != null;

      // We configure the size of default buffer to be the size of camera preview we want.
      texture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());

      // This is the output Surface we need to start preview.
      final Surface surface = new Surface(texture);

      // We set up a CaptureRequest.Builder with the output Surface.
      previewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
      previewRequestBuilder.addTarget(surface);

      LOGGER.i("Opening camera preview: " + previewSize.getWidth() + "x" + previewSize.getHeight());

      // Create the reader for the preview frames.
      previewReader =
          ImageReader.newInstance(
              previewSize.getWidth(), previewSize.getHeight(), ImageFormat.YUV_420_888, 2);

      previewReader.setOnImageAvailableListener(tfPreviewListener, backgroundHandler);
      previewRequestBuilder.addTarget(previewReader.getSurface());

      // Here, we create a CameraCaptureSession for camera preview.
      cameraDevice.createCaptureSession(
          Arrays.asList(surface, previewReader.getSurface()),
          new CameraCaptureSession.StateCallback() {

            @Override
            public void onConfigured(final CameraCaptureSession cameraCaptureSession) {
              // The camera is already closed
              if (null == cameraDevice) {
                return;
              }

              // When the session is ready, we start displaying the preview.
              captureSession = cameraCaptureSession;
              try {
                // Auto focus should be continuous for camera preview.
                previewRequestBuilder.set(
                    CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                // Flash is automatically enabled when necessary.
                previewRequestBuilder.set(
                    CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

                // Finally, we start displaying the camera preview.
                previewRequest = previewRequestBuilder.build();
                captureSession.setRepeatingRequest(
                    previewRequest, captureCallback, backgroundHandler);
              } catch (final CameraAccessException e) {
                LOGGER.e(e, "Exception!");
              }
            }

            @Override
            public void onConfigureFailed(final CameraCaptureSession cameraCaptureSession) {
              showToast("Failed");
            }
          },
          null);
    } catch (final CameraAccessException e) {
      LOGGER.e(e, "Exception!");
    }

    LOGGER.i("Getting assets.");
    tfPreviewListener.initialize(
        getActivity().getAssets(), scoreView,boxView,inferenceHandler, sensorOrientation,textureView.getWidth(), textureView.getHeight());
    LOGGER.i("TensorFlow initialized.");
  }

  /**
   * Configures the necessary {@link android.graphics.Matrix} transformation to `mTextureView`.
   * This method should be called after the camera preview size is determined in
   * setUpCameraOutputs and also the size of `mTextureView` is fixed.
   *
   * @param viewWidth  The width of `mTextureView`
   * @param viewHeight The height of `mTextureView`
   */
  private void configureTransform(final int viewWidth, final int viewHeight) {
    final Activity activity = getActivity();
    if (null == textureView || null == previewSize || null == activity) {
      return;
    }
    final int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
    final Matrix matrix = new Matrix();
    final RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
    final RectF bufferRect = new RectF(0, 0, previewSize.getHeight(), previewSize.getWidth());
    final float centerX = viewRect.centerX();
    final float centerY = viewRect.centerY();
    if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
      bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
      matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
      final float scale =
          Math.max(
              (float) viewHeight / previewSize.getHeight(),
              (float) viewWidth / previewSize.getWidth());
      matrix.postScale(scale, scale, centerX, centerY);
      matrix.postRotate(90 * (rotation - 2), centerX, centerY);
    } else if (Surface.ROTATION_180 == rotation) {
      matrix.postRotate(180, centerX, centerY);
    }
    textureView.setTransform(matrix);
  }



  /**
   * Compares two {@code Size}s based on their areas.
   */
  static class CompareSizesByArea implements Comparator<Size> {
    @Override
    public int compare(final Size lhs, final Size rhs) {
      // We cast here to ensure the multiplications won't overflow
      return Long.signum(
          (long) lhs.getWidth() * lhs.getHeight() - (long) rhs.getWidth() * rhs.getHeight());
    }
  }

  /**
   * Shows an error message dialog.
   */
  public static class ErrorDialog extends DialogFragment {
    private static final String ARG_MESSAGE = "message";

    public static ErrorDialog newInstance(final String message) {
      final ErrorDialog dialog = new ErrorDialog();
      final Bundle args = new Bundle();
      args.putString(ARG_MESSAGE, message);
      dialog.setArguments(args);
      return dialog;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
      final Activity activity = getActivity();
      return new AlertDialog.Builder(activity)
          .setMessage(getArguments().getString(ARG_MESSAGE))
          .setPositiveButton(
              android.R.string.ok,
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialogInterface, final int i) {
                  activity.finish();
                }
              })
          .create();
    }
  }


}
