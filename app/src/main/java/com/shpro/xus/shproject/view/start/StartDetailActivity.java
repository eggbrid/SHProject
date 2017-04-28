package com.shpro.xus.shproject.view.start;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.bean.response.StartBeanResponse;
import com.shpro.xus.shproject.bean.start.Start;
import com.shpro.xus.shproject.bean.tb.TBOneDay;
import com.shpro.xus.shproject.shprojectHttp.Url.UrlUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.OkHttpUtil;
import com.shpro.xus.shproject.shprojectHttp.okhttp.interfaces.CallBack;
import com.shpro.xus.shproject.util.ToastUtil;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.taobao.TBOneDayActivity;
import com.shpro.xus.shproject.view.views.VerticalViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.grantland.widget.AutofitTextView;

/**
 * Created by xus on 2017/4/26.
 */

public class StartDetailActivity extends CommentActivity {
    protected VerticalViewPager viewpage;
    protected AutofitTextView generalTxt;
    protected RatingBar summaryStar;
    protected AutofitTextView loveTxt;
    protected RatingBar loveStar;
    protected AutofitTextView workTxt;
    protected RatingBar workStar;
    protected AutofitTextView moneyTxt;
    protected RatingBar moneyStar;
    protected AutofitTextView luckyColor;
    protected AutofitTextView luckyTime;
    protected AutofitTextView luckyDirection;
    protected AutofitTextView luckyNum;
    protected AutofitTextView grxz;
    protected AutofitTextView dayNotice;
    protected AutofitTextView tomorrowGeneralTxt;
    protected RatingBar tomorrowSummaryStar;
    protected AutofitTextView tomorrowLoveTxt;
    protected RatingBar tomorrowLoveStar;
    protected AutofitTextView tomorrowWorkTxt;
    protected RatingBar tomorrowWorkStar;
    protected AutofitTextView tomorrowMoneyTxt;
    protected RatingBar tomorrowMoneyStar;
    protected AutofitTextView tomorrowLuckyColor;
    protected AutofitTextView tomorrowLuckyTime;
    protected AutofitTextView tomorrowLuckyDirection;
    protected AutofitTextView tomorrowLuckyNum;
    protected AutofitTextView tomorrowGrxz;
    protected AutofitTextView tomorrowDayNotice;
    protected AutofitTextView weekGeneralTxt;
    protected RatingBar weekSummaryStar;
    protected AutofitTextView weekLoveTxt;
    protected RatingBar weekLoveStar;
    protected AutofitTextView weekWorkTxt;
    protected RatingBar weekWorkStar;
    protected AutofitTextView weekMoneyTxt;
    protected RatingBar weekMoneyStar;
    protected AutofitTextView weekLuckyColor;
    protected AutofitTextView weekLuckyDay;
    protected AutofitTextView weekLuckyDirection;
    protected AutofitTextView weekLuckyNum;
    protected AutofitTextView weekGrxz;
    protected AutofitTextView weekXrxz;
    protected AutofitTextView weekWeekNotice;
    protected AutofitTextView monthGeneralTxt;
    protected RatingBar monthSummaryStar;
    protected AutofitTextView monthLoveTxt;
    protected RatingBar monthLoveStar;
    protected AutofitTextView monthWorkTxt;
    protected RatingBar monthWorkStar;
    protected AutofitTextView monthMoneyTxt;
    protected RatingBar monthMoneyStar;
    protected AutofitTextView monthMonthWeakness;
    protected AutofitTextView monthLuckyDirection;
    protected AutofitTextView monthYfxz;
    protected AutofitTextView monthGrxz;
    protected AutofitTextView monthXrxz;
    protected AutofitTextView monthMonthAdvantage;
    protected AutofitTextView yearGeneralTxt;
    protected AutofitTextView yearGeneralIndex;
    protected AutofitTextView yearLoveTxt;
    protected AutofitTextView yearLoveIndex;
    protected AutofitTextView yearWorkTxt;
    protected AutofitTextView workIndex;
    protected AutofitTextView yearMoneyTxt;
    protected AutofitTextView moneyIndex;
    protected AutofitTextView healthTxt;
    private String id;
    private StartDetailAdapter adapter;
    private List<View> list = new ArrayList<>();
    private View day;
    private View tomorrow;
    private View week;
    private View month;
    private View year;

    @Override
    public int setContentView() {
        return R.layout.activity_start_detail;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("星座运势");
        id = getIntent().getStringExtra("id");
        viewpage = (VerticalViewPager) findViewById(R.id.viewpage);
        LayoutInflater inflater = getLayoutInflater();
        day = inflater.inflate(R.layout.item_day_layout, null);
        initDayView(day);
        list.add(day);
        tomorrow = inflater.inflate(R.layout.item_tomorrow_layout, null);
        initTomorrowView(tomorrow);
        list.add(tomorrow);

        week = inflater.inflate(R.layout.item_week_layout, null);
        initWeekView(week);
        list.add(week);

        month = inflater.inflate(R.layout.item_month_layout, null);
        initMonthView(month);
        list.add(month);
        year = inflater.inflate(R.layout.item_year_layout, null);
        initYearView(year);
        list.add(year);

        adapter = new StartDetailAdapter(list);
        viewpage.setAdapter(adapter);
        getData();

    }

    public void initDayView(View day) {
        generalTxt = (AutofitTextView) day.findViewById(R.id.general_txt);
        summaryStar = (RatingBar) day.findViewById(R.id.summary_star);
        loveTxt = (AutofitTextView) day.findViewById(R.id.love_txt);
        loveStar = (RatingBar) day.findViewById(R.id.love_star);
        workTxt = (AutofitTextView) day.findViewById(R.id.work_txt);
        workStar = (RatingBar) day.findViewById(R.id.work_star);
        moneyTxt = (AutofitTextView) day.findViewById(R.id.money_txt);
        moneyStar = (RatingBar) day.findViewById(R.id.money_star);
        luckyColor = (AutofitTextView) day.findViewById(R.id.lucky_color);
        luckyTime = (AutofitTextView) day.findViewById(R.id.lucky_time);
        luckyDirection = (AutofitTextView) day.findViewById(R.id.lucky_direction);
        luckyNum = (AutofitTextView) day.findViewById(R.id.lucky_num);
        grxz = (AutofitTextView) day.findViewById(R.id.grxz);
        dayNotice = (AutofitTextView) day.findViewById(R.id.day_notice);
    }

    public void initDayData(Start day) {
        generalTxt.setText(day.getGeneral_txt());
        summaryStar.setRating(Float.parseFloat(day.getSummary_star()));
        loveTxt.setText(day.getLove_txt());
        loveStar.setRating(Float.parseFloat(day.getLove_star()));
        workTxt.setText(day.getWork_txt());
        workStar.setRating(Float.parseFloat(day.getWork_star()));
        moneyTxt.setText(day.getMoney_txt());
        moneyStar.setRating(Float.parseFloat(day.getMoney_star()));
        luckyColor.setText(day.getLucky_color());
        luckyTime.setText(day.getLucky_time());
        luckyDirection.setText(day.getLucky_direction());
        luckyNum.setText(day.getLucky_num());
        grxz.setText(day.getGrxz());
        dayNotice.setText(day.getDay_notice());
    }
    public void initTomorrowView(View tomorrow) {
        tomorrowGeneralTxt = (AutofitTextView) tomorrow.findViewById(R.id.tomorrow_general_txt);
        tomorrowSummaryStar = (RatingBar) tomorrow.findViewById(R.id.tomorrow_summary_star);
        tomorrowLoveTxt = (AutofitTextView) tomorrow.findViewById(R.id.tomorrow_love_txt);
        tomorrowLoveStar = (RatingBar) tomorrow.findViewById(R.id.tomorrow_love_star);
        tomorrowWorkTxt = (AutofitTextView) tomorrow.findViewById(R.id.tomorrow_work_txt);
        tomorrowWorkStar = (RatingBar) tomorrow.findViewById(R.id.tomorrow_work_star);
        tomorrowMoneyTxt = (AutofitTextView) tomorrow.findViewById(R.id.tomorrow_money_txt);
        tomorrowMoneyStar = (RatingBar) tomorrow.findViewById(R.id.tomorrow_money_star);
        tomorrowLuckyColor = (AutofitTextView) tomorrow.findViewById(R.id.tomorrow_lucky_color);
        tomorrowLuckyTime = (AutofitTextView) tomorrow.findViewById(R.id.tomorrow_lucky_time);
        tomorrowLuckyDirection = (AutofitTextView) tomorrow.findViewById(R.id.tomorrow_lucky_direction);
        tomorrowLuckyNum = (AutofitTextView) tomorrow.findViewById(R.id.tomorrow_lucky_num);
        tomorrowGrxz = (AutofitTextView) tomorrow.findViewById(R.id.tomorrow_grxz);
        tomorrowDayNotice = (AutofitTextView) tomorrow.findViewById(R.id.tomorrow_day_notice);
    }
    public void initTomorrowData(Start tomorrow) {
        tomorrowGeneralTxt.setText(tomorrow.getGeneral_txt());
        tomorrowSummaryStar.setRating(Float.parseFloat(tomorrow.getSummary_star()));
        tomorrowLoveTxt.setText(tomorrow.getLove_txt());
        tomorrowLoveStar.setRating(Float.parseFloat(tomorrow.getLove_star()));
        tomorrowWorkTxt.setText(tomorrow.getWork_txt());
        tomorrowWorkStar.setRating(Float.parseFloat(tomorrow.getWork_star()));
        tomorrowMoneyTxt.setText(tomorrow.getMoney_txt());
        tomorrowMoneyStar.setRating(Float.parseFloat(tomorrow.getMoney_star()));
        tomorrowLuckyColor.setText(tomorrow.getLucky_color());
        tomorrowLuckyTime.setText(tomorrow.getLucky_time());
        tomorrowLuckyDirection.setText(tomorrow.getLucky_direction());
        tomorrowLuckyNum.setText(tomorrow.getLucky_num());
        tomorrowGrxz.setText(tomorrow.getGrxz());
        tomorrowDayNotice.setText(tomorrow.getDay_notice());
    }
    private void initWeekView(View week) {
        weekGeneralTxt = (AutofitTextView) week.findViewById(R.id.week_general_txt);
        weekSummaryStar = (RatingBar) week.findViewById(R.id.week_summary_star);
        weekLoveTxt = (AutofitTextView) week.findViewById(R.id.week_love_txt);
        weekLoveStar = (RatingBar) week.findViewById(R.id.week_love_star);
        weekWorkTxt = (AutofitTextView) week.findViewById(R.id.week_work_txt);
        weekWorkStar = (RatingBar) week.findViewById(R.id.week_work_star);
        weekMoneyTxt = (AutofitTextView) week.findViewById(R.id.week_money_txt);
        weekMoneyStar = (RatingBar) week.findViewById(R.id.week_money_star);
        weekLuckyColor = (AutofitTextView) week.findViewById(R.id.week_lucky_color);
        weekLuckyDay = (AutofitTextView) week.findViewById(R.id.week_lucky_day);
        weekLuckyDirection = (AutofitTextView) week.findViewById(R.id.week_lucky_direction);
        weekLuckyNum = (AutofitTextView) week.findViewById(R.id.week_lucky_num);
        weekGrxz = (AutofitTextView) week.findViewById(R.id.week_grxz);
        weekXrxz = (AutofitTextView) week.findViewById(R.id.week_xrxz);
        weekWeekNotice = (AutofitTextView) week.findViewById(R.id.week_week_notice);
    }
    public void initWeekData(Start week) {
        weekGeneralTxt.setText(week.getGeneral_txt());
        weekSummaryStar.setRating(Float.parseFloat(week.getSummary_star()));
        weekLoveTxt.setText(week.getLove_txt());
        weekLoveStar.setRating(Float.parseFloat(week.getLove_star()));
        weekWorkTxt.setText(week.getWork_txt());
        weekWorkStar.setRating(Float.parseFloat(week.getWork_star()));
        weekMoneyTxt.setText(week.getMoney_txt());
        weekMoneyStar.setRating(Float.parseFloat(week.getMoney_star()));
        weekLuckyColor.setText(week.getLucky_color());
        weekLuckyDay.setText(week.getLucky_day());
        weekLuckyDirection.setText(week.getLucky_direction());
        weekLuckyNum.setText(week.getLucky_num());
        weekGrxz.setText(week.getGrxz());
        weekXrxz.setText(week.getXrxz());
        weekWeekNotice.setText(week.getWeek_notice());
    }
    private void initMonthView(View month) {
        monthGeneralTxt = (AutofitTextView) month.findViewById(R.id.month_general_txt);
        monthSummaryStar = (RatingBar) month.findViewById(R.id.month_summary_star);
        monthLoveTxt = (AutofitTextView) month.findViewById(R.id.month_love_txt);
        monthLoveStar = (RatingBar) month.findViewById(R.id.month_love_star);
        monthWorkTxt = (AutofitTextView) month.findViewById(R.id.month_work_txt);
        monthWorkStar = (RatingBar) month.findViewById(R.id.month_work_star);
        monthMoneyTxt = (AutofitTextView) month.findViewById(R.id.month_money_txt);
        monthMoneyStar = (RatingBar) month.findViewById(R.id.month_money_star);
        monthMonthWeakness = (AutofitTextView) month.findViewById(R.id.month_month_weakness);
        monthLuckyDirection = (AutofitTextView) month.findViewById(R.id.month_lucky_direction);
        monthYfxz = (AutofitTextView) month.findViewById(R.id.month_yfxz);
        monthGrxz = (AutofitTextView) month.findViewById(R.id.month_grxz);
        monthXrxz = (AutofitTextView) month.findViewById(R.id.month_xrxz);
        monthMonthAdvantage = (AutofitTextView) month.findViewById(R.id.month_month_advantage);
    }
    public void initMonthData(Start month) {
        monthGeneralTxt.setText(month.getGeneral_txt());
        monthSummaryStar.setRating(Float.parseFloat(month.getSummary_star()));
        monthLoveTxt.setText(month.getLove_txt());
        monthLoveStar.setRating(Float.parseFloat(month.getLove_star()));
        monthWorkTxt.setText(month.getWork_txt());
        monthWorkStar.setRating(Float.parseFloat(month.getWork_star()));
        monthMoneyTxt.setText(month.getMoney_txt());
        monthMoneyStar.setRating(Float.parseFloat(month.getMoney_star()));
        monthMonthWeakness.setText(month.getMonth_weakness());
        monthLuckyDirection.setText(month.getLucky_direction());
        monthYfxz.setText(month.getYfxz());
        monthGrxz.setText(month.getGrxz());
        monthXrxz.setText(month.getXrxz());
        monthMonthAdvantage.setText(month.getMonth_advantage());
    }
    private void initYearView(View year) {
        yearGeneralTxt = (AutofitTextView) year.findViewById(R.id.year_general_txt);
        yearGeneralIndex = (AutofitTextView) year.findViewById(R.id.year_general_index);
        yearLoveTxt = (AutofitTextView) year.findViewById(R.id.year_love_txt);
        yearLoveIndex = (AutofitTextView) year.findViewById(R.id.year_love_index);
        yearWorkTxt = (AutofitTextView) year.findViewById(R.id.year_work_txt);
        workIndex = (AutofitTextView) year.findViewById(R.id.work_index);
        yearMoneyTxt = (AutofitTextView) year.findViewById(R.id.year_money_txt);
        moneyIndex = (AutofitTextView) year.findViewById(R.id.money_index);
        healthTxt = (AutofitTextView) year.findViewById(R.id.health_txt);
    }

    public void initYearData(Start year) {
        yearGeneralTxt.setText(year.getGeneral_txt());
        yearGeneralIndex.setText(year.getGeneral_index());
        yearLoveTxt.setText(year.getLove_txt());
        yearLoveIndex .setText(year.getLove_index());
        yearWorkTxt  .setText(year.getWork_txt());
        workIndex  .setText(year.getWork_index());
        yearMoneyTxt   .setText(year.getMoney_txt());
        moneyIndex .setText(year.getMoney_index());
        healthTxt .setText(year.getHealth_txt());
    }
    public void getData() {
        showPross("正在获取星座信息");
        Map<String, String> map = new HashMap<>();
        map.put("xz", id);
        OkHttpUtil.doGet(this, UrlUtil.START_DETAIL_URL, map, new CallBack<StartBeanResponse>() {
            @Override
            public void onSuccess(StartBeanResponse startBeanResponse) {
                dissPross();
                Start day=startBeanResponse.getDay();
                initDayData(day);
                Start tomorrow=startBeanResponse.getTomorrow();
                initTomorrowData(tomorrow);
                Start week=startBeanResponse.getWeek();
                initWeekData(week);
                Start month=startBeanResponse.getMonth();
                initMonthData(month);
                Start year=startBeanResponse.getYear();
                initYearData(year);
            }

            @Override
            public void onError(String s) {
                dissPross();
                StartDetailActivity.this.finish();
                ToastUtil.makeTextShort(StartDetailActivity.this, s);
            }
        }, StartBeanResponse.class);
    }

}
