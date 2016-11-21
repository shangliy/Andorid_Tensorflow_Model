package org.tensorflow.demo;

import android.app.Application;
import android.graphics.RectF;

/**
 * Created by shangliy on 10/5/16.
 */
public class Globals extends Application {
    private RectF rect = new RectF();


    public RectF getData(){
        return this.rect;
    }

    public void setData(float left,float top,float right,float bot){
        this.rect.set(left,top,right,bot);
    }
}
