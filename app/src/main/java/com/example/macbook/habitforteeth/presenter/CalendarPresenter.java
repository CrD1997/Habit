package com.example.macbook.habitforteeth.presenter;

import android.database.Cursor;
import android.util.Log;

import com.example.macbook.habitforteeth.contract.CalendarContract;
import com.example.macbook.habitforteeth.model.Star;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;
import static com.example.macbook.habitforteeth.view.CalendarActivity.databaseHelper;

/**
 * Created by MacBook on 2018/12/2.
 */

public class CalendarPresenter implements CalendarContract.Presenter{

    private ArrayList<Star> starList;
    private CalendarContract.View mView;

    //连接view与presenter
    public CalendarPresenter(CalendarContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start(){}

    //初始化数据库
    @Override
    public void initData(){
        for(int i=1;i<8;i++){
            Star s=new Star(i,1,i,0);
            databaseHelper.insert(s);
        }
        for(int i=1;i<8;i++){
            Star s=new Star(i+7,2,i,0);
            databaseHelper.insert(s);
        }
        for(int i=1;i<8;i++){
            Star s=new Star(i+14,3,i,0);
            databaseHelper.insert(s);
        }
    }

    @Override
    public void updateData(Star s){
        databaseHelper.update(s);
    }

    @Override
    public void deleteAll(){
        databaseHelper.deleteAllData();
    }

    //初始化列表
    @Override
    public ArrayList<Star> initList(){
        starList= new ArrayList<>();
        Cursor cursor=databaseHelper.getAllData();
        if(cursor!=null){
            while(cursor.moveToNext()){
                Star s=new Star();
                s.id= cursor.getInt(cursor.getColumnIndex("id"));
                s.week= cursor.getInt(cursor.getColumnIndex("week"));
                s.day= cursor.getInt(cursor.getColumnIndex("day"));
                s.number= cursor.getInt(cursor.getColumnIndex("number"));
                Log.d("id", "initList: "+s.id);
                starList.add(s);
            }
            cursor.close();
        }
        return starList;
    }

}
