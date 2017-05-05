package com.shpro.xus.shproject.bean.hai;

/**
 * Created by xus on 2017/5/5.
 */

public class Weather {
    private String airQuality;//良private String ,
    private String sourceName;//中国天气网private String ,
    private String date;//2017-05-05private String ,
    private String lastUpdateTime;//2017-05-05 14:34:28private String ,
    private String dateLongprivate;// String : 1493913600,
    private String pm25;//58private String ,
    private String city;//北京private String ,
    private String humidity;//11%private String ,
    private String windLevelprivate;// String : 3,
    private String weather;//晴private String ,
    private String tempRange;//11℃~21℃private String ,
    private String wind;//北风5-6级private String

    public String getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getDateLongprivate() {
        return dateLongprivate;
    }

    public void setDateLongprivate(String dateLongprivate) {
        this.dateLongprivate = dateLongprivate;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindLevelprivate() {
        return windLevelprivate;
    }

    public void setWindLevelprivate(String windLevelprivate) {
        this.windLevelprivate = windLevelprivate;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTempRange() {
        return tempRange;
    }

    public void setTempRange(String tempRange) {
        this.tempRange = tempRange;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
