package com.shpro.xus.shproject.view.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.shpro.xus.shproject.R;

/**
 * Created by xus on 2016/11/17.
 */

public class ProLoadingDialog extends Dialog {
    private TaiJiView taiji;
    private TextView text;
    public ProLoadingDialog(Context context) {
        super(context, R.style.Dialog_Fullscreen);
    }

    public ProLoadingDialog(Context context, int themeResId) {
        super(context, R.style.Dialog_Fullscreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_proload);
        taiji=(TaiJiView)findViewById(R.id.taiji);
        text=(TextView)findViewById(R.id.text);
    }

    public void show(String s) {
        super.show();
        taiji.start();
        text.setText(s);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        taiji.stop();
    }
}
