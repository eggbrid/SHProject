package com.shpro.xus.shproject.view.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.shpro.xus.shproject.R;

/**
 * Created by xus on 2016/11/16.
 */

public class PhotoDialog extends Dialog implements View.OnClickListener {
    protected Button photo;
    protected Button camera;
    protected Animation leftAnimation;
    protected Animation rightAnimation;
    protected onPhotoClickListener onPhotoClickListener;

    public PhotoDialog.onPhotoClickListener getOnPhotoClickListener() {
        return onPhotoClickListener;
    }

    public void setOnPhotoClickListener(PhotoDialog.onPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public PhotoDialog(Context context) {
        super(context, R.style.Dialog_Fullscreen);
    }

    public PhotoDialog(Context context, int themeResId) {
        super(context, themeResId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo);
        initView();
        setCanceledOnTouchOutside(true);
    }

    public void show(Activity context, View v) {
        super.show();
        leftAnimation = AnimationUtils.loadAnimation(context, R.anim.left_in);
        rightAnimation = AnimationUtils.loadAnimation(context, R.anim.right_in);
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = v.getHeight();
        p.width = (int) (d.getWidth());    //宽度设置为全屏
        p.y = (int) v.getY();
        Log.e("wangxu", p.y+"");

        getWindow().setAttributes(p);
        getWindow().setGravity(Gravity.LEFT | Gravity.TOP);
        photo.startAnimation(leftAnimation);
        camera.startAnimation(rightAnimation);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.photo) {
            onPhotoClickListener.onPhotoClick();

        } else if (view.getId() == R.id.camera) {
            onPhotoClickListener.onCameraClick();
        }
    }

    private void initView() {
        photo = (Button) findViewById(R.id.photo);
        photo.setOnClickListener(PhotoDialog.this);
        camera = (Button) findViewById(R.id.camera);
        camera.setOnClickListener(PhotoDialog.this);
    }

    public interface onPhotoClickListener {
        void onPhotoClick();

        void onCameraClick();

    }

}
