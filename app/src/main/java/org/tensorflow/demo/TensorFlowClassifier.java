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

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Trace;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * JNI wrapper class for the Tensorflow native code.
 */
public class TensorFlowClassifier implements Classifier {
    private static final String TAG = "TensorflowClassifier";

    // jni native methods.
    public native int initializeTensorFlow(
            AssetManager assetManager,
            String model,
            String labels,
            int numClasses,
            int inputSize,
            int imageMean,
            float imageStd,
            String inputName,
            String outputName);

    private native String classifyImageBmp(Bitmap bitmap,int fea_flag);

    private native String classifyImageRgb(int[] output, int width, int height);

    static {
        System.loadLibrary("tensorflow_demo");
    }



    @Override
    public List<Recognition> recognizeImage(final Bitmap bitmap,int fea_flag) {
        // Log this method so that it can be analyzed with systrace.
        Trace.beginSection("Recognize");
        int feature_dim =2048;
        final ArrayList<Recognition> recognitions = new ArrayList<Recognition>();
        for (final String result : classifyImageBmp(bitmap,fea_flag).split("\n")) {
            //Log.i(TAG, "Parsing [" + result + "]");

            // Clean up the string as needed
            final StringTokenizer st = new StringTokenizer(result);
            if (!st.hasMoreTokens()) {
                continue;
            }

            final String id = st.nextToken();
            final String confidenceString = st.nextToken();
            final String object = st.nextToken();
            final String left = st.nextToken();
            final String right = st.nextToken();
            final String top = st.nextToken();
            final String bot = st.nextToken();
            String class_labels =" ";
            String class_probs = " ";
            String feature;
            if (fea_flag == 0){
                 feature = "1111";
            }
            else{
                feature = st.nextToken();
                for (int i = 1;i < feature_dim;i++) {
                    //Log.i(TAG, String.valueOf(i));
                    feature = feature + " " + st.nextToken();
                }

                class_labels = st.nextToken();
                Log.i(TAG, String.valueOf( class_labels));
                class_probs = st.nextToken();
                for (int j = 1; j<1;j++) {
                    class_labels = class_labels + " " + st.nextToken();
                    class_probs = class_probs + " " + st.nextToken();
                }

            }

            //String feature = st.nextToken();
            /*
            for (int i = 1;i < feature_dim;i++) {
                //Log.i(TAG, String.valueOf(i));
                feature = feature + " " + st.nextToken();
            }
            */

            RectF rectF = new RectF(Float.parseFloat(left), Float.parseFloat(top), Float.parseFloat(right), Float.parseFloat(bot));


            final float confidence = Float.parseFloat(confidenceString);

            final String title =
                    result.substring(id.length() + confidenceString.length() + 2, result.length());

            if (!title.isEmpty()) {
                recognitions.add(new Recognition(id, title, confidence, rectF,feature,class_labels));
            }
        }
        Trace.endSection();
        return recognitions;
    }

    @Override
    public void close() {}
}