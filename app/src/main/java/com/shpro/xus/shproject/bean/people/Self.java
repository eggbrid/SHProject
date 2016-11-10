package com.shpro.xus.shproject.bean.people;

import android.util.Log;

import com.shpro.xus.shproject.bean.Player;
import com.shpro.xus.shproject.bean.skill.SkillB;

/**
 * Created by xus on 2016/11/7.
 */

public class Self extends Player {

    public Self(int mh, int mm, int s1, int s2,int attack,int mattack,int defense,int mdefense) {
        this.maxHp = mh;
        this.maxMp = mm;
        this.attack = attack;
        this.mattack = mattack;
        this.defense = defense;
        this.mdefense = mdefense;
        this.Skill1 = s1;
        this.Skill2 = s2;
    }

    @Override
    public void beAttacked(SkillB skill) {
        SubHp(skill.getHitHp());

    }

    @Override
    public void attacked(Player player) {

    }

    @Override
    public void died() {
        Log.e("wangxu", "死了");
    }

    @Override
    public void AddHp(int hp) {
        nowHp = nowHp + hp;
    }

    @Override
    public void SubHp(int hp) {
        nowHp = nowHp - hp;
        if (nowHp <= 0) {
            died();
        }
    }

    @Override
    public void AddMP(int mp) {
        nowMp = nowMp + mp;
    }

    @Override
    public void SubMP(int mp) {
        nowMp = nowMp - mp;

    }

    @Override
    public void useSkill1() {

    }

    @Override
    public void useSkill2() {

    }

    @Override
    public void useSkill3() {

    }

    @Override
    public void useSkill4() {

    }
}
