package com.shpro.xus.shproject.bean;

import com.shpro.xus.shproject.interfaces.HP;
import com.shpro.xus.shproject.interfaces.MP;
import com.shpro.xus.shproject.interfaces.Skill;

/**
 * Created by xus on 2016/11/7.
 */

public abstract class Player implements HP,MP,Skill{
    public String name;
    public int maxHp;
    public int nowHp;
    public int maxMp;
    public int nowMp;
    public int Skill1;
    public int Skill2;
    public abstract void beAttacked(com.shpro.xus.shproject.enums.Skill skill);
    public abstract void attacked(Player player);
    public abstract void died();
}
