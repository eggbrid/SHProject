package com.shpro.xus.shproject.bean.gone;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;

/**
 * Created by xus on 2017/2/7.
 */

public class ViewNum implements Serializable {
    public ViewNum(String name, Class classs) {
        this.name = name;
        this.classs = classs;
    }

    public Class classs;
    public String name;

    public Class getClasss() {
        return classs;
    }

    public void setClasss(Class classs) {
        this.classs = classs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void toActivity(Activity activity){
        activity.startActivity(new Intent(activity,this.classs));
    }
}
