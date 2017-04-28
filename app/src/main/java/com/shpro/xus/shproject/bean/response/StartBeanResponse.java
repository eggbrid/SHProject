package com.shpro.xus.shproject.bean.response;


import com.shpro.xus.shproject.bean.start.Start;

/**
 * 公司:  云筑网 
 * -------------- 
 * 作者:  王旭
 * 
 * 日期:  2017年4月24日
 * 说明:
 * 
 * 网站:	 http://www.yunzhuw.com/
 * 修订:
 * 日期:      作者:
 * 说明：
 * 
 */

public class StartBeanResponse {
private Start tomorrow;
private Start month;
private Start year;
private Start day;
private Start week;
private String star;
public String getStar() {
	return star;
}
public void setStar(String star) {
	this.star = star;
}
public Start getTomorrow() {
	return tomorrow;
}
public void setTomorrow(Start tomorrow) {
	this.tomorrow = tomorrow;
}
public Start getMonth() {
	return month;
}
public void setMonth(Start month) {
	this.month = month;
}
public Start getYear() {
	return year;
}
public void setYear(Start year) {
	this.year = year;
}
public Start getDay() {
	return day;
}
public void setDay(Start day) {
	this.day = day;
}
public Start getWeek() {
	return week;
}
public void setWeek(Start week) {
	this.week = week;
}

}
