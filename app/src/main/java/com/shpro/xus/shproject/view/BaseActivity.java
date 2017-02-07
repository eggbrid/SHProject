package com.shpro.xus.shproject.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.interfaces.views.RefreshListener;
import com.shpro.xus.shproject.shprojectHttp.HttpUtil;
import com.shpro.xus.shproject.util.FileUtil;
import com.shpro.xus.shproject.view.views.PhotoDialog;
import com.shpro.xus.shproject.view.views.ProLoadingDialog;
import com.shpro.xus.shproject.view.views.RefreshLayout;
import com.shpro.xus.shproject.view.views.RefreshLayoutFooter;
import com.wilddog.client.SyncReference;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by xus on 2016/11/8.
 */

public abstract class BaseActivity extends Activity implements View.OnClickListener, TakePhoto.TakeResultListener, InvokeListener {

    protected ImageButton left;
    protected ImageButton right;
    protected LinearLayout title;
    protected TextView titleText;
    protected InvokeParam invokeParam;
    protected TakePhoto takePhoto;
    protected PhotoDialog photoDialog;
    protected ProLoadingDialog proLoadingDialog;

    public abstract int setContentView();

    public abstract void initView() throws Exception;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhoto = getTakePhoto();
        takePhoto.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePhoto.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        takePhoto.onSaveInstanceState(outState);

    }

    public void addPushListener() {

    }

    public void setCommentTitleView(String titles) {
        initTitleView();
        left.setImageResource(R.drawable.back);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
        left.setVisibility(View.VISIBLE);
        titleText.setText(titles);
    }

    public void initTitleView() {
        titleText = (TextView) findViewById(R.id.title_text);
        left = (ImageButton) findViewById(R.id.left);
        right = (ImageButton) findViewById(R.id.right);
        title = (LinearLayout) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.left) {

        } else if (view.getId() == R.id.right) {

        } else if (view.getId() == R.id.title) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    public void showPhoto(View v) {
        if (photoDialog == null) {
            photoDialog = new PhotoDialog(this);
        }
//        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
//        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//      final   Uri imageUri = Uri.fromFile(file);
        photoDialog.show(this, v);
        photoDialog.setOnPhotoClickListener(new PhotoDialog.onPhotoClickListener() {
            @Override
            public void onPhotoClick() {
                takePhoto.onPickFromDocumentsWithCrop(FileUtil.getFileUri(), getCropOptions());

            }

            @Override
            public void onCameraClick() {
                takePhoto.onPickFromCaptureWithCrop(FileUtil.getFileUri(), getCropOptions());

            }
        });

    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(200).setOutputY(200);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    @Override
    public void takeSuccess(TResult result) {
        photoDialog.dismiss();
        onFileGet(new File(result.getImage().getPath()));
    }

    @Override
    public void takeFail(TResult result, String msg) {
        photoDialog.dismiss();
    }

    @Override
    public void takeCancel() {
        photoDialog.dismiss();

    }

    public void onFileGet(File file) {

    }

    public void showPross(String s) {
        if (proLoadingDialog == null) {
            proLoadingDialog = new ProLoadingDialog(this);
        }
        proLoadingDialog.show(s);
    }

    public void dissPross() {
        if (proLoadingDialog != null) {
            proLoadingDialog.dismiss();
        }
    }
public void setRefresh(final RefreshLayout mRefreshLayout,final ListView mListView,boolean isautoRefresh, boolean isopenLoad,final RefreshListener refreshListener){
    mRefreshLayout.setChildView(mListView, isopenLoad);
    mRefreshLayout.setColorSchemeResources(R.color.black,
            R.color.white);

    //使用SwipeRefreshLayout的下拉刷新监听
    //use SwipeRefreshLayout OnRefreshListener
    mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (refreshListener != null) {
                refreshListener.onRefresh();
            }
        }
    });

    //使用自定义的RefreshLayout加载更多监听
    //use customed RefreshLayout OnLoadListener
    mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
        @Override
        public void onLoad() {
            mRefreshLayout.getRefreshLayoutFooter().closefooter();
            if (refreshListener != null) {
                refreshListener.onLoadMore();
            }
        }
    });

    if (isautoRefresh) {

        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                if (refreshListener != null) {
                    refreshListener.onRefresh();
                }
            }
        });
    }
}

   public  void stopRefresh(final RefreshLayout mRefreshLayout,boolean isNoData){
        if (mRefreshLayout == null)
            return;
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setLoading(false);
        RefreshLayoutFooter refreshLayoutFooter = mRefreshLayout.getRefreshLayoutFooter();
        if (refreshLayoutFooter != null) {
            refreshLayoutFooter.setViewVis();
            refreshLayoutFooter.openfooter();
        }
        mRefreshLayout.setNoData(isNoData);
    }



    public void addPush() {

    }

    public interface OnMessageGet {
        void onmessageGet();
    }

}
