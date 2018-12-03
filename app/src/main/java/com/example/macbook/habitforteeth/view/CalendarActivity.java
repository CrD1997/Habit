package com.example.macbook.habitforteeth.view;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.macbook.habitforteeth.R;
import com.example.macbook.habitforteeth.contract.CalendarContract;
import com.example.macbook.habitforteeth.model.DatabaseHelper;
import com.example.macbook.habitforteeth.model.Star;
import com.example.macbook.habitforteeth.presenter.CalendarPresenter;
import com.example.macbook.habitforteeth.utils.IPickDialog;
import com.example.macbook.habitforteeth.utils.ImageRoundView;
import com.example.macbook.habitforteeth.utils.PickDialog;
import com.example.macbook.habitforteeth.utils.StarAdapter;

import java.util.ArrayList;

import static android.support.v4.util.Preconditions.checkNotNull;

public class CalendarActivity extends AppCompatActivity implements CalendarContract.View,StarAdapter.OnMyClick,IPickDialog,View.OnClickListener {

    int width ;
    int height ;

    int themeID;

    private ArrayList<Star> starList=new ArrayList<>();
    StarAdapter adapter;
    public static DatabaseHelper databaseHelper;
    CalendarContract.Presenter presenter;
    private PickDialog pickDialog;
    private PopupWindow popupwindow;
    private ImageButton homeBtn;

    int pos=0;

    @Override
    public void setPresenter(CalendarContract.Presenter p) {
        presenter = p;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        themeID=pref.getInt("theme",0);
        if(themeID==1){
            setTheme(R.style.BlueTheme);
        }else{
            setTheme(R.style.PinkTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        //创建Presenter对象
        new CalendarPresenter(this);
        //创建数据库
        databaseHelper=new DatabaseHelper(this);

        //初始化数据库
        //initStarData();
        //初始化view-list
        initStarList();
        //渲染recyclerview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.calendar_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StarAdapter(starList,this);
        recyclerView.setAdapter(adapter);

        //初始化对话框，设置接口
        pickDialog = new PickDialog(this);
        pickDialog.setiPickDialog(this);
        pickDialog.getWindow().setBackgroundDrawable(new BitmapDrawable());

        //切换主题弹窗
        homeBtn = (ImageButton) findViewById(R.id.button_home);
        homeBtn.setOnClickListener(this);
    }

    //初始化数据库
    public void initStarData(){
        presenter.deleteAll();
        presenter.initData();
    }

    //初始化view-list
    private void initStarList(){
        starList= presenter.initList();
    }

    //点击一个日历item
    @Override
    public void onItemClick(View view, int position) {
        pos = position;
        Star star = starList.get(position);
        if (pickDialog != null && pickDialog.isShowing()) {
            return;
        }
        dialog(pos);
    }

    //初始化dialog
    public void dialog(int pos) {
        pickDialog.init();
        pickDialog.show();
    }

    @Override
    public void onLast() {
        pickDialog.init();
    }

    @Override
    public void onNext() {
        pickDialog.init();
    }

    @Override
    public void onReward(String address) {
        Star s= starList.get(pos);
        s.number+=Integer.parseInt(pickDialog.getRewardNum());
        //Toast.makeText(this, s.id+" "+s.week+" "+s.day+" "+s.number+"", Toast.LENGTH_SHORT).show();
        presenter.updateData(s);
        adapter.notifyDataSetChanged();
    }

    //切换主题相关按钮
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_home:
                if (popupwindow != null&&popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    return;
                } else {
                    initmPopupWindowView();
                    popupwindow.showAsDropDown(v,0,0);
//                    popupBg.setBackgroundColor(Color.parseColor("#7f999999"));
                }
                break;
            case R.id.theme_button:
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                if(themeID==1){
                    editor.putInt("theme",0);
                    editor.apply();
                    recreate();
                }else{
                    editor.putInt("theme",1);
                    editor.apply();
                    recreate();
                }
                //Toast.makeText(this, "Change Theme", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void initmPopupWindowView() {
        // // 获取自定义布局文件pop.xml的视图
        View customView = getLayoutInflater().inflate(R.layout.popup_window, null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView,width,height);
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        popupwindow.setAnimationStyle(R.style.AnimationFade);
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
//                    popupBg.setBackgroundColor(Color.parseColor("#00999999"));
                    popupwindow.dismiss();
                    popupwindow = null;
                }
                return false;
            }
        });
        /** 在这里可以实现自定义视图的功能 */
        ImageRoundView themeBtn = (ImageRoundView) customView.findViewById(R.id.theme_button);
        themeBtn.setOnClickListener(this);
    }
}
