package com.example.macbook.habitforteeth.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.macbook.habitforteeth.R;
import com.example.macbook.habitforteeth.model.Star;

/**
 * Created by MacBook on 2018/12/2.
 */

public class PickDialog extends Dialog{

    enum WhichClick {
        REWARD, LAST, NEXT
    }

    public PickDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public PickDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected PickDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private int which=1;
    private ImageView next, last,reward;
    private TextView rewardNum;
    private ScaleAnimation animation1, animation2;
    private WhichClick click = WhichClick.NEXT;
//    private LinearLayout bg;//!!!
    private IPickDialog iPickDialog;

    public void setiPickDialog(IPickDialog iPickDialog) {
        this.iPickDialog = iPickDialog;
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        setContentView(view);
        last=findViewById(R.id.last);
        next=findViewById(R.id.next);
        reward=findViewById(R.id.reward);
        rewardNum=findViewById(R.id.reward_num);

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPickDialog.onLast();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPickDialog.onNext();
            }
        });

        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPickDialog.onReward(getRewardNum());
                dismiss();
            }
        });
    }

    public String getRewardNum() {
        return rewardNum.getText().toString().trim();
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        return super.onTouchEvent(event);
//    }

    public void init() {
        if(which==1){
            reward.setImageResource(R.drawable.reward_daytime);
            rewardNum.setText("4");
            which=2;
        }else{
            reward.setImageResource(R.drawable.reward_night);
            rewardNum.setText("2");
            which=1;
        }
    }

}
