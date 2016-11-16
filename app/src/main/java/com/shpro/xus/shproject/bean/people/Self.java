package com.shpro.xus.shproject.bean.people;

import com.shpro.xus.shproject.enums.SelfType;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by xus on 2016/11/7.
 */

public class Self extends People {
    public static Self creatSelf(int selfType) {
        Self self = new Self();
        int i = 24;
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        if (h <= 4 || h >= 21) {
            //夜间
            self.sh = h <= 4 ? h + 10 : h / 2;
            i = i - self.sh;
            self.lucky=new Random().nextInt(i);
            self.strive=i-self.lucky;
        } else {
            //白天
            self.sh =new Random().nextInt(h);
            i = i - self.sh;
            self.lucky=i/2;
            self.strive=i-self.lucky;
        }
        SelfType st=SelfType.getSelfType(selfType);
        self.lucky= self.lucky+st.getLucky();
        self.strive= self.strive+st.getStrive();
        self.sh= self.sh+st.getSh();
        return self;
    }
}
