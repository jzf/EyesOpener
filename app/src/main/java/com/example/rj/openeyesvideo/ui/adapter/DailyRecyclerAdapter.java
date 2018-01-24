package com.example.rj.openeyesvideo.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rj.openeyesvideo.R;
import com.example.rj.openeyesvideo.component.ImageLoader;
import com.example.rj.openeyesvideo.model.DataManager;
import com.example.rj.openeyesvideo.model.bean.DailyBean;
import com.example.rj.openeyesvideo.model.bean.ItemListBean;
import com.example.rj.openeyesvideo.ui.view.ItemDailyView;
import com.example.rj.openeyesvideo.util.DiffUtilCallBack;
import com.example.rj.openeyesvideo.widget.JumpShowTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rj on 2017/12/22.
 */

public class DailyRecyclerAdapter extends BaseRecyclerAdapter<ItemListBean> {


    ViewPager viewPager;
    TopAdapter topAdapter;
    List<ItemListBean> topList;


    public enum ITEM_TYPE{
        TYPE_DATE,
        TYPE_NEW,
        TYPE_TOP
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return ITEM_TYPE.TYPE_TOP.ordinal();
        }else if(datas.get(position).getType().equals("textHeader")){
            return ITEM_TYPE.TYPE_DATE.ordinal();
        }
        return ITEM_TYPE.TYPE_NEW.ordinal();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public DailyRecyclerAdapter(Context context, List<ItemListBean> datas) {
        super(context, datas);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_TYPE.TYPE_DATE.ordinal()){
            return new DateViewHolder(mLayoutInflater.inflate(R.layout.item_date,parent,false));
        }else if(viewType==ITEM_TYPE.TYPE_TOP.ordinal()){
            topAdapter=new TopAdapter(mContext,topList);
            return new TopViewHolder(mLayoutInflater.inflate(R.layout.item_top,parent,false));
        }else {
            return new ItemViewHolder(mLayoutInflater.inflate(R.layout.item_daily,parent,false));
        }
    }

    @Override
    public void convert(ViewHolder holder, final int position) {

        if(holder instanceof DateViewHolder){
            //((DateViewHolder)holder).textView.setTypeface(mContext,"fonts/Lobster-1.4.otf");
            ((DateViewHolder)holder).textView.setText(datas.get(position-1).getData().getText());
        }else if(holder instanceof TopViewHolder){
            ((TopViewHolder)holder).topPager.setAdapter(topAdapter);
            viewPager=((TopViewHolder)holder).topPager;
            ((TopViewHolder)holder).topDes.setText(topList.get(0).getData().getDescription());
            ((TopViewHolder)holder).topTitle.setText(topList.get(0).getData().getTitle());
        } else {
            String detail;
            if(datas.get(position-1).getData().getAuthor()==null){
                detail="开眼精选 / # "+datas.get(position-1).getData().getCategory();
            }else {
                detail=datas.get(position-1).getData().getAuthor().getName()+" / #" +datas.get(position-1).getData().getCategory();
                ImageLoader.loadCircle(mContext,datas.get(position-1).getData().getAuthor().getIcon(),((ItemViewHolder)holder).mAuthorImage);
            }
            ((ItemViewHolder)holder).mAuthorText.setText(detail);
            ((ItemViewHolder)holder).mTitleTest.setText(datas.get(position-1).getData().getTitle());
            ImageLoader.load(mContext,datas.get(position-1).getData().getCover().getFeed(),((ItemViewHolder)holder).mDailyImage);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    private String getDate() {
        Date dt= new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM/dd/yyyy");
        String dateString = simpleDateFormat.format(dt);
        return dateString;
    }

    public static class DateViewHolder extends ViewHolder{

        @BindView(R.id.text_date)
        TextView textView;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class ItemViewHolder extends ViewHolder{

        @BindView(R.id.iv_daily_item_image)
        ImageView mDailyImage;
        @BindView(R.id.iv_daily_author)
        ImageView mAuthorImage;
        @BindView(R.id.item_text_author)
        TextView mAuthorText;
        @BindView(R.id.item_text_title)
        TextView mTitleTest;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class TopViewHolder extends ViewHolder{

        @BindView(R.id.vp_top)
        ViewPager topPager;
        @BindView(R.id.tv_top_des)
        JumpShowTextView topDes;
        @BindView(R.id.tv_top_title)
        JumpShowTextView topTitle;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void addDailyData(List<ItemListBean> listBeans){
        Log.d("hzj", "addDailyData: "+datas.size());
        DiffUtil.DiffResult diffResult=DiffUtil.calculateDiff(new DiffUtilCallBack(datas,listBeans),false);
        datas.clear();
        datas.addAll(listBeans);
        //datas=listBeans;
        diffResult.dispatchUpdatesTo(this);
    }

    public void addTopData(List<ItemListBean> listBeans){
        topList=listBeans;
    }

}
