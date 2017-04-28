package com.shpro.xus.shproject.view.start;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import com.shpro.xus.shproject.R;
import com.shpro.xus.shproject.view.CommentActivity;
import com.shpro.xus.shproject.view.views.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

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
        initWeekView(month);
        list.add(month);
        year = inflater.inflate(R.layout.item_year_layout, null);
        initYearView(year);
        list.add(year);

        adapter = new StartDetailAdapter(list);
        viewpage.setAdapter(adapter);


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
}
