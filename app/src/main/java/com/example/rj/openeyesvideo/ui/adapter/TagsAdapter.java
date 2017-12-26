package com.example.rj.openeyesvideo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rj.openeyesvideo.R;
import com.example.rj.openeyesvideo.component.ImageLoader;
import com.example.rj.openeyesvideo.model.bean.TagsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rj on 2017/12/26.
 */

public class TagsAdapter extends BaseRecyclerAdapter<TagsBean> {


    public TagsAdapter(Context context, List<TagsBean> datas) {
        super(context, datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.item_tag,parent,false));
    }

    @Override
    public void convert(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        ((ItemViewHolder)holder).textTag.setText(datas.get(position).getName());
        ImageLoader.load(mContext,datas.get(position).getBgPicture(),((ItemViewHolder)holder).tagImageIiew);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tag_bg)
        ImageView tagImageIiew;
        @BindView(R.id.tag_name)
        TextView textTag;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void addTagsData(List<TagsBean> tagsBean){
        datas.clear();
        datas.addAll(tagsBean);
        notifyDataSetChanged();
    }
}
