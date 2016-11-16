package com.shpro.xus.shproject.enums;

/**
 * Created by xus on 2016/11/15.
 */

public enum SelfType {
    DEMON(1, 3, 5, 10),
    HUMAN(2, 6, 8, 4),
    NATURAL(3, 9, 0, 9);

    SelfType(int type, int lucky, int strive, int sh) {
        this.type = type;
        this.lucky = lucky;
        this.strive = strive;
        this.sh = sh;
    }

    private int type;//类型
    private int lucky;//幸运值
    private int strive;//努力值
    private int sh;//山海值

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLucky() {
        return lucky;
    }

    public void setLucky(int lucky) {
        this.lucky = lucky;
    }

    public int getStrive() {
        return strive;
    }

    public void setStrive(int strive) {
        this.strive = strive;
    }

    public int getSh() {
        return sh;
    }

    public void setSh(int sh) {
        this.sh = sh;
    }

    public static SelfType getSelfType(int i) {
        switch (i) {
            case 1:
                return DEMON;
            case 2:
                return HUMAN;
            default:
                return NATURAL;
        }
    }
}
