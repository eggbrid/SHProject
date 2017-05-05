package com.shpro.xus.shproject.view.hai;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.shpro.xus.shproject.APP;
import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.baisi.ShowBaiSiBean;
import com.shpro.xus.shproject.bean.hai.HaiBean;
import com.shpro.xus.shproject.bean.hai.Weather;
import com.shpro.xus.shproject.bean.response.FindBagResponse;
import com.shpro.xus.shproject.bean.user.UserDetail;
import com.shpro.xus.shproject.db.DBHelper;
import com.shpro.xus.shproject.db.bean.QILing;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.ImageLoaderUtil;
import com.shpro.xus.shproject.util.SntpTime;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.CommentActivity;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by xus on 2017/5/4.
 */

public class HaiActivity extends CommentActivity implements View.OnClickListener, InitListener, RecognizerDialogListener, TextUnderstanderListener,TencentLocationListener {
    protected TextView mySay;
    protected TextView airQuality;
    protected TextView pm25;
    protected TextView txtWeather;
    protected TextView tempRange;
    protected TextView wind;
    protected LinearLayout weather;
    protected ImageView avatar;
    protected TextView name;
    protected ImageView typeImg;
    protected TextView type;
    protected TextView lv;
    protected LinearLayout peoples;
    protected JCVideoPlayerStandard videoplayer;
    protected LinearLayout video;
    protected EditText contentText;
    protected Button send;
    protected RelativeLayout txtLayout;
    protected ImageView btnAudio;
    protected ImageView btnSmall;
    protected ImageView btnWeather;
    protected ImageView btnVideo;
    protected ImageView btnInfo;
    protected LinearLayout buttom;
    protected TextView qlSay;
    protected TextView noSay;
    protected ProgressBar pro;
    private TencentLocationManager locationManager;

    String[] noSays = {"器灵似乎很不解", "器灵卖着萌", "器灵听到后懒得理你", "器灵变换着样子，不想和你说话", "器灵觉得你说的太多了，很生气，不想理你", "器灵不知道该和你说点啥"};
    String[] white = {"器灵发生了故障", "器灵出现了微微的裂痕", "器灵灵力不足，12小时内无法答复你", "器灵消耗着仅有的灵力"};

    @Override
    public int setContentView() {
        return R.layout.activity_hai;
    }

    @Override
    public void initView() throws Exception {

        mySay = (TextView) findViewById(R.id.my_say);
        qlSay = (TextView) findViewById(R.id.ql_say);

        airQuality = (TextView) findViewById(R.id.airQuality);
        pm25 = (TextView) findViewById(R.id.pm25);
        txtWeather = (TextView) findViewById(R.id.txt_weather);
        tempRange = (TextView) findViewById(R.id.tempRange);
        wind = (TextView) findViewById(R.id.wind);
        weather = (LinearLayout) findViewById(R.id.weather);

        avatar = (ImageView) findViewById(R.id.avatar);
        name = (TextView) findViewById(R.id.name);
        typeImg = (ImageView) findViewById(R.id.type_img);
        type = (TextView) findViewById(R.id.type);
        lv = (TextView) findViewById(R.id.lv);
        peoples = (LinearLayout) findViewById(R.id.peoples);

        videoplayer = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        video = (LinearLayout) findViewById(R.id.video);

        contentText = (EditText) findViewById(R.id.content_text);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(HaiActivity.this);

        txtLayout = (RelativeLayout) findViewById(R.id.txt_layout);

        btnAudio = (ImageView) findViewById(R.id.btn_audio);
        btnAudio.setOnClickListener(HaiActivity.this);
        btnSmall = (ImageView) findViewById(R.id.btn_small);
        btnSmall.setOnClickListener(HaiActivity.this);
        btnWeather = (ImageView) findViewById(R.id.btn_weather);
        btnWeather.setOnClickListener(HaiActivity.this);
        btnVideo = (ImageView) findViewById(R.id.btn_video);
        btnVideo.setOnClickListener(HaiActivity.this);
        btnInfo = (ImageView) findViewById(R.id.btn_info);
        btnInfo.setOnClickListener(HaiActivity.this);

        buttom = (LinearLayout) findViewById(R.id.buttom);
        setCommentTitleView("上古器灵");

        noSay = (TextView) findViewById(R.id.no_say);
        pro = (ProgressBar) findViewById(R.id.pro);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            if (TextUtils.isEmpty(contentText.getText().toString())) {
                ToastUtil.makeTextShort(this, "器灵与你对视，场面一度尴尬");
            } else {
                TextUnderstander.createTextUnderstander(this, this).understandText(contentText.getText().toString(), this);
                setWating(contentText.getText().toString());
                contentText.setText("");

            }

        } else if (view.getId() == R.id.btn_audio) {
            RecognizerDialog mDialog = new RecognizerDialog(this, this);
            mDialog.setParameter("asr_sch", "1");
            mDialog.setParameter("nlp_version", "2.0");
            mDialog.setListener(this);
            mDialog.show();
            setWating("");
        } else if (view.getId() == R.id.btn_small) {
            setWating("讲个笑话");
            TextUnderstander.createTextUnderstander(this, this).understandText("讲个笑话", this);
            contentText.setText("");
        } else if (view.getId() == R.id.btn_weather) {
            setWating("今天天气");
                TencentLocationRequest request = TencentLocationRequest.create();
                request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
                request.setInterval(1000);
                locationManager = TencentLocationManager.getInstance(this);
                locationManager.requestLocationUpdates(request, this);
        } else if (view.getId() == R.id.btn_video) {
            setWating("看个视频");
            initVedioData();

        } else if (view.getId() == R.id.btn_info) {
            setWating("看个查看我的属性");
            setPeopledata();

        }
    }

    public void setWating(String string) {
        pro.setVisibility(View.VISIBLE);
        weather.setVisibility(View.GONE);
        peoples.setVisibility(View.GONE);
        video.setVisibility(View.GONE);
        noSay.setVisibility(View.GONE);
        mySay.setVisibility(View.VISIBLE);
        mySay.setText(string);
        qlSay.setVisibility(View.GONE);
    }

    public void setSayView() {
        try {
            if (DBHelper.getInstance(this).getQILingTimes(APP.getInstance().getUser().getId()) >= 8) {
                setNoSayView();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pro.setVisibility(View.GONE);

        weather.setVisibility(View.GONE);
        peoples.setVisibility(View.GONE);
        video.setVisibility(View.GONE);
        noSay.setVisibility(View.GONE);
        mySay.setVisibility(View.VISIBLE);
        qlSay.setVisibility(View.VISIBLE);

    }

    public void setPeoplesView() {
        try {
            if (DBHelper.getInstance(this).getQILingTimes(APP.getInstance().getUser().getId()) >= 8) {
                setNoSayView();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pro.setVisibility(View.GONE);

        weather.setVisibility(View.GONE);
        peoples.setVisibility(View.VISIBLE);
        video.setVisibility(View.GONE);
        noSay.setVisibility(View.GONE);
        qlSay.setVisibility(View.GONE);

        mySay.setVisibility(View.VISIBLE);
    }

    public void setPeopledata() {
        setPeoplesView();
        UserDetail userDetail = APP.getInstance().getUserDetail();
        ImageLoaderUtil.getInstance().loadCircleImage(userDetail.getAvatar(), avatar, R.drawable.avatar);
        name.setText(userDetail.getName());
        if (userDetail.getType().equals("1")) {
            typeImg.setImageResource(R.drawable.demon);
            type.setText("山海神兽");
        } else if (userDetail.getType().equals("2")) {
            typeImg.setImageResource(R.drawable.people);
            type.setText("山海师");
        } else {
            typeImg.setImageResource(R.drawable.natural);
            type.setText("先天之灵");
        }
        lv.setText("lv" + userDetail.getLv());
    }
private void setWeatherData(Weather weatherData){
    airQuality.setText(weatherData.getAirQuality());
    pm25.setText(weatherData.getPm25());
    txtWeather.setText(weatherData.getWeather());
    tempRange.setText(weatherData.getTempRange());
    wind.setText(weatherData.getWind());
    setWeatherView();
}
    public void setWeatherView() {
        try {
            if (DBHelper.getInstance(this).getQILingTimes(APP.getInstance().getUser().getId()) >= 8) {
                setNoSayView();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pro.setVisibility(View.GONE);

        weather.setVisibility(View.VISIBLE);
        peoples.setVisibility(View.GONE);
        video.setVisibility(View.GONE);
        noSay.setVisibility(View.GONE);
        qlSay.setVisibility(View.GONE);

        mySay.setVisibility(View.VISIBLE);
    }
public  void setVideodata(ShowBaiSiBean showBaiSiBean){
    videoplayer.thumbImageView.setImageResource(R.drawable.black_white_btn);
    videoplayer.setUp(showBaiSiBean.getVideo_uri()
            , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
    ImageLoaderUtil.getInstance().loadMp4Image(showBaiSiBean.getVideo_uri(),videoplayer.thumbImageView,this);
    setVideoView();
}
    public void setVideoView() {

        try {
            if (DBHelper.getInstance(this).getQILingTimes(APP.getInstance().getUser().getId()) >= 8) {
                setNoSayView();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pro.setVisibility(View.GONE);
        weather.setVisibility(View.GONE);
        peoples.setVisibility(View.GONE);
        video.setVisibility(View.VISIBLE);
        noSay.setVisibility(View.GONE);
        qlSay.setVisibility(View.GONE);

        mySay.setVisibility(View.VISIBLE);
    }

    public void setNoSayView() {
        pro.setVisibility(View.GONE);

        weather.setVisibility(View.GONE);
        peoples.setVisibility(View.GONE);
        video.setVisibility(View.GONE);
        noSay.setVisibility(View.VISIBLE);
        qlSay.setVisibility(View.GONE);
        mySay.setVisibility(View.VISIBLE);
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    /////////////////////////////////Handler消息分类
                    //Long这里一定要用 Long  代替long 否则linux源码mm编译的时候会报错
                    case 1:
                        long netTimeslong = (long) msg.obj;
                        try {
                            QILing qiLing = DBHelper.getInstance(HaiActivity.this).setQILingTime(APP.getInstance().getUser().getId(), netTimeslong);
                            if (qiLing.getTimes() >= 20) {
                                deleteThis();
                                return;
                            } else if (qiLing.getTimes() >= 8) {
                                noSay.setText(white[3]);
                                ToastUtil.makeTextShort(HaiActivity.this, "器灵灵气减少了");
                                if (qiLing.getTimes() >= 19) {
                                    ToastUtil.makeTextShort(HaiActivity.this, "器灵灵气见底，貌似再用一次就会碎掉");

                                }
                                return;
                            } else if (qiLing.getTimes() >= 5 && qiLing.getTimes() < 8) {
                                noSay.setText(white[qiLing.getTimes() - 5]);
                            } else {
                                noSay.setText(noSays[qiLing.getTimes()]);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        SntpTime currentTime = new SntpTime(30000, mHandler, 1);
        currentTime.getTime();

    }

    @Override
    public void onInit(int i) {

    }
//语音返回结果
    @Override
    public void onResult(RecognizerResult recognizerResult, boolean b) {
        HaiBean haiBean = OkHttpUtil.fromJson(recognizerResult.getResultString(), HaiBean.class);
        if (haiBean.getAnswer() != null) {
            setSayView();
            mySay.setText(haiBean.getText());
            qlSay.setText(haiBean.getAnswer().getText());

        } else {
            setNoSayView();

//noSay.setText();
            mySay.setText(haiBean.getText());
        }
    }
//文字返回结果
    @Override
    public void onResult(UnderstanderResult understanderResult) {
        HaiBean haiBean = OkHttpUtil.fromJson(understanderResult.getResultString(), HaiBean.class);
        if (haiBean.getAnswer() != null) {
            setSayView();
            mySay.setText(haiBean.getText());
            qlSay.setText(haiBean.getAnswer().getText());

        }else if(haiBean.getData()!=null&&haiBean.getData().getResult()!=null&&haiBean.getData().getResult().size()>0){
            setWeatherData(haiBean.getData().getResult().get(0));
        }  else {
            setNoSayView();
            mySay.setText(haiBean.getText());
        }
    }

    @Override
    public void onError(SpeechError speechError) {
        ToastUtil.makeTextShort(this, speechError.getErrorDescription());

    }
    public void initVedioData() {
        Map<String, String> map = new HashMap<>();
        OkHttpUtil.doGet(this, UrlUtil.BAISI_RANDOMONE_URL, map, new CallBack<ShowBaiSiBean>() {
            @Override
            public void onSuccess(ShowBaiSiBean showBaiSiBean) {
                if (showBaiSiBean!=null){
                    setVideodata(showBaiSiBean);
                }else{
                    setSayView();
                    qlSay.setText("等下，我有点忙，你一会再找我");
                }

            }

            @Override
            public void onError(String s) {
                setSayView();
                qlSay.setText(s);
            }
        }, ShowBaiSiBean.class);
    }
    public void deleteThis() {
        showPross("器灵的样子有些怪...");
        List<Bag> bags = APP.getInstance().getBags();
        Bag bag = null;
        for (Bag b : bags) {
            if (b.getBagTemplateId().equals("1006")) {
                bag = b;
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("userId", APP.getInstance().getUser().getId());
        map.put("bagId", bag.getId());

        OkHttpUtil.doPost(this, UrlUtil.DELETEBAG_URL, map, new CallBack<FindBagResponse>() {
            @Override
            public void onSuccess(FindBagResponse findBagResponse) {
                dissPross();
                if (findBagResponse != null) {
                    List<Bag> bags = findBagResponse.getList();
                    if (bags == null) {
                        bags = new ArrayList<Bag>();
                    }
                    APP.getInstance().setBags(bags);
                    HaiActivity.this.finish();
                    ToastUtil.makeTextShort(HaiActivity.this, "器灵破碎....");
                }
            }

            @Override
            public void onError(String s) {
            }
        }, FindBagResponse.class);
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
//        Log.e("wangxu",tencentLocation.getCity());
        if (tencentLocation==null||TextUtils.isEmpty(tencentLocation.getCity())){
            setSayView();
           qlSay.setText("此地有神秘力量，阻止了我窥视天相");
        }else{
            locationManager.removeUpdates(this);
            TextUnderstander.createTextUnderstander(this, this).understandText("今天"+tencentLocation.getCity()+"天气", this);
            contentText.setText("");
        }

    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
//
//setSayView();
//        qlSay.setText("此地有神秘力量，阻止了我窥视天相");

    }
}
