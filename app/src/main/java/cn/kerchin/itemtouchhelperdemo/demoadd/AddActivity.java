package cn.kerchin.itemtouchhelperdemo.demoadd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.kerchin.itemtouchhelperdemo.DisplayUtil;
import cn.kerchin.itemtouchhelperdemo.R;
import cn.kerchin.itemtouchhelperdemo.demochannel.ChannelEntity;
import cn.kerchin.itemtouchhelperdemo.helper.ItemDragHelperCallback;

/**
 * Created by hkq325800 on 2017/4/21.
 */

public class AddActivity extends AppCompatActivity {
    RecyclerView mRecy, mNotAddedReV;
    List<ChannelEntity> listNotAdded;
    TextView tv_btn_edit;
    AddedAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViewById(R.id.tv_next).setVisibility(View.INVISIBLE);
        mRecy = (RecyclerView) findViewById(R.id.recy);
        mNotAddedReV = (RecyclerView) findViewById(R.id.mNotAddedReV);
        tv_btn_edit = (TextView) findViewById(R.id.tv_btn_edit);
        tv_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!adapter.isEditMode) {//setOnClickListener
                    adapter.startEditMode(mRecy);
                    tv_btn_edit.setText(R.string.finish);
                } else {
                    adapter.cancelEditMode(mRecy);
                    tv_btn_edit.setText(R.string.edit);
                    String str = "";
                    for (ChannelEntity entity : adapter.list) {
                        str += entity.getName() + " ";
                    }
                    Toast.makeText(AddActivity.this, str, Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }
        });
        initData();
    }

    private void initData() {
        listNotAdded = new ArrayList<>();
        ChannelEntity entity1 = new ChannelEntity();
        entity1.setName("图片");
        listNotAdded.add(entity1);
        ChannelEntity entity2 = new ChannelEntity();
        entity2.setName("文字");
        listNotAdded.add(entity2);
        ChannelEntity entity3 = new ChannelEntity();
        entity3.setName("语音");
        listNotAdded.add(entity3);
        ChannelEntity entity4 = new ChannelEntity();
        entity4.setName("位置");
        listNotAdded.add(entity4);
        mNotAddedReV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mNotAddedReV.setAdapter(new NotAddedAdapter(this, listNotAdded, new NotAddedAdapter.OnItemClick() {
            @Override
            public void onClick(int pos, ChannelEntity model) {
                if(mRecy.getChildCount()<5){
                    mRecy.getLayoutParams().height = mRecy.getHeight() + DisplayUtil.dp2px(AddActivity.this, 54);
                }
                else {
                    mRecy.smoothScrollToPosition(mRecy.getAdapter().getItemCount());
                }
                ChannelEntity newOne = new ChannelEntity();
                newOne.setName(model.getName() + mRecy.getAdapter().getItemCount());
                adapter.addItem(newOne);
            }
        }));
        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecy);
        mRecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new AddedAdapter(this, helper, new ArrayList<ChannelEntity>());
        mRecy.setAdapter(adapter);
//        mRecy.setNestedScrollingEnabled(false);
        mNotAddedReV.setNestedScrollingEnabled(false);
    }

    public void next(View view){

    }
}
