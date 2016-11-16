package com.shpro.xus.shproject.view.other;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.view.BaseActivity;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.views.PhotoDialog;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by xus on 2016/11/15.
 */

public class ImageActivity extends CommentActivity implements View.OnClickListener {
    protected Button photo;
    protected Button cr;
    protected TakePhoto takePhoto;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        super.setContentView(R.layout.image_activity);
//        initView();
//    }

    @Override
    public void onClick(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        if (view.getId() == R.id.photo) {
            showPhoto(view);
//            takePhoto.onPickMultipleWithCrop(1,getCropOptions());
        } else if (view.getId() == R.id.cr) {
            showPhoto(view);

//            takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());

        }
    }

    @Override
    public int setContentView() {
        return R.layout.image_activity;
    }

    @Override
    public void initView() {
        photo = (Button) findViewById(R.id.photo);
        photo.setOnClickListener(ImageActivity.this);
        cr = (Button) findViewById(R.id.cr);
        cr.setOnClickListener(ImageActivity.this);
        takePhoto = getTakePhoto();
    }

    @Override
    public void onFileGet(File file) {
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Log.e("上传文件成功:" ,bmobFile.getFileUrl());
                }else{
                    Log.e("上传文件失败：" , e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                Log.e("onProgress：" ,value+"");

            }
        });
    }

//    private CropOptions getCropOptions() {
//        CropOptions.Builder builder = new CropOptions.Builder();
//        builder.setOutputX(200).setOutputY(200);
//        builder.setWithOwnCrop(false);
//        return builder.create();
//    }
//    @Override
//    public void takeCancel() {
//        super.takeCancel();
//    }
//
//    @Override
//    public void takeFail(TResult result, String msg) {
//        super.takeFail(result, msg);
//    }
//
//    @Override
//    public void takeSuccess(TResult result) {
//        super.takeSuccess(result);
//    }

}
