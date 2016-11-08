package com.shpro.xus.shproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.shpro.xus.shproject.people.SelfINS;
import com.shpro.xus.shproject.shprojectHttp.RegPlayer;

/**
 * Created by xus on 2016/11/8.
 */

public class ListActivy extends Activity{
    ListView list;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.list_activity);
        list=(ListView)findViewById(R.id.list);
        RegPlayer regPlayer=new RegPlayer();
        regPlayer.getPalyList();
    }

    public class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
