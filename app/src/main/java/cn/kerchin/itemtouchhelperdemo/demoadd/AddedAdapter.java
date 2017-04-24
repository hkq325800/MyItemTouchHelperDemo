package cn.kerchin.itemtouchhelperdemo.demoadd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.kerchin.itemtouchhelperdemo.R;
import cn.kerchin.itemtouchhelperdemo.demochannel.ChannelEntity;
import cn.kerchin.itemtouchhelperdemo.helper.OnDragVHListener;
import cn.kerchin.itemtouchhelperdemo.helper.OnItemMoveListener;

/**
 * Created by hkq325800 on 2017/4/21.
 */

public class AddedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemMoveListener {
    Context mContext;
    LayoutInflater mInflater;
    List<ChannelEntity> list;
    private ItemTouchHelper mItemTouchHelper;
    boolean isEditMode;

    public AddedAdapter(Context context, ItemTouchHelper helper, List<ChannelEntity> list) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mItemTouchHelper = helper;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_my, null);
        final OtherViewHolder viewHolder = new OtherViewHolder(view);
        View.OnLongClickListener longClick = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                if (!isEditMode) {//setOnLongClickListener
                    RecyclerView recyclerView = ((RecyclerView) parent);
                    startEditMode(recyclerView);

                    // header 按钮文字 改成 "完成"

//                    View view = recyclerView.getChildAt(0);
//                    if (view == recyclerView.getLayoutManager().findViewByPosition(0)) {
//                        TextView tvBtnEdit = (TextView) view.findViewById(R.id.tv_btn_edit);
//                        tvBtnEdit.setText(R.string.finish);
//                    }
                }

                mItemTouchHelper.startDrag(viewHolder);
                return true;
            }
        };
        viewHolder.itemView.setOnLongClickListener(longClick);
        return viewHolder;
    }

    /**
     * 开启编辑模式
     *
     * @param parent
     */
    public void startEditMode(RecyclerView parent) {
        isEditMode = true;

        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            if (imgEdit != null) {
                imgEdit.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 完成编辑模式
     *
     * @param parent
     */
    public void cancelEditMode(RecyclerView parent) {
        isEditMode = false;

        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            if (imgEdit != null) {
                imgEdit.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OtherViewHolder viewHolder = (OtherViewHolder) holder;
        viewHolder.textView.setText(list.get(position).getName());
        viewHolder.editImg.setVisibility(isEditMode?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(ChannelEntity model) {
        list.add(model);
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        ChannelEntity item = list.get(fromPosition);
        list.remove(fromPosition);
        list.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 其他频道
     */
    class OtherViewHolder extends RecyclerView.ViewHolder implements OnDragVHListener {
        private TextView textView;
        private ImageView editImg;

        public OtherViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
            editImg = (ImageView) itemView.findViewById(R.id.img_edit);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundResource(R.drawable.bg_channel_p);
        }

        @Override
        public void onItemFinish() {
            itemView.setBackgroundResource(R.drawable.bg_channel);
        }
    }
}
