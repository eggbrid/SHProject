package com.shpro.xus.shproject.enums;

/**
 * Created by xus on 2016/11/7.
 */

public enum  Skill {
    HIT(1,0,3),GUARD(2,1,0),BIGR(3,5,10);
    public int flag;
    public int useMp;
    public int hitHp;
     Skill(int f,int um,int uh){
        this.flag=f;
        this.useMp=um;
        this.hitHp=uh;
    }
}
