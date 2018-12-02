package com.example.macbook.habitforteeth.utils;

/**
 * Created by MacBook on 2018/12/1.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.habitforteeth.R;
import com.example.macbook.habitforteeth.model.Star;

import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.ViewHolder>{

    private List<Star> mStarList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View starView;
        TextView starColor;
        ImageView starImage;
        TextView starNum;

        public ViewHolder(View view) {
            super(view);
            starView = view;
            starColor=(TextView)view.findViewById(R.id.calendar_item_color);
            starImage = (ImageView) view.findViewById(R.id.calendar_item_star);
            starNum = (TextView) view.findViewById(R.id.calendar_item_num);
        }
    }

    public StarAdapter(List<Star> list,OnMyClick myclick) {
        mStarList = list;
        mClick=myclick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
//        holder.starView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Star star = mStarList.get(position);
//                Toast.makeText(v.getContext(), "you clicked view " + star.getWeek()+" "+star.getDay(), Toast.LENGTH_SHORT).show();
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Star star = mStarList.get(position);
        holder.starImage.setImageResource(R.drawable.star);
        holder.starNum.setText(star.getNumber()+"");
        if(star.getNumber()==0){
            holder.starImage.setVisibility(View.GONE);
            holder.starNum.setVisibility(View.GONE);
        }
        if(star.getWeek()==1){
            holder.starColor.setBackgroundResource(R.drawable.corner_boy_first);
        }else if(star.getWeek()==2){
            holder.starColor.setBackgroundResource(R.drawable.corner_boy_second);
        }else {
            holder.starColor.setBackgroundResource(R.drawable.corner_boy_third);
        }

        if(mClick!=null){
            holder.starView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mClick.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mStarList.size();
    }

    public interface OnMyClick {
        void onItemClick(View view, int position);
    }

    private OnMyClick mClick;
}