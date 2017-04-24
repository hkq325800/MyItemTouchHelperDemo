package cn.kerchin.itemtouchhelperdemo.demoadd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.kerchin.itemtouchhelperdemo.R;
import cn.kerchin.itemtouchhelperdemo.demochannel.ChannelEntity;

/**
 * Created by hkq325800 on 2017/4/21.
 */

public class NotAddedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    LayoutInflater mInflater;
    List<ChannelEntity> list;

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    OnItemClick onItemClick;

    public NotAddedAdapter(Context context, List<ChannelEntity> list, OnItemClick click) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.onItemClick = click;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_other, null);
        OtherViewHolder viewHolder = new OtherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        OtherViewHolder viewHolder = (OtherViewHolder) holder;
        viewHolder.textView.setText(list.get(position).getName());
        viewHolder.editImg.setImageResource(R.mipmap.ic_launcher);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(position, list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClick {
        void onClick(int pos, ChannelEntity model);
    }

    /**
     * 其他频道
     */
    class OtherViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView editImg;

        public OtherViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
            editImg = (ImageView) itemView.findViewById(R.id.img_edit);
        }
    }
}
