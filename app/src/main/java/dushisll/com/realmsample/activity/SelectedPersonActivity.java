package dushisll.com.realmsample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dushisll.com.realmsample.R;
import dushisll.com.realmsample.adapter.BaseAdapter;
import dushisll.com.realmsample.adapter.SelectedPersonAdapter;
import dushisll.com.realmsample.bean.Person;
import dushisll.com.realmsample.util.DefaultItemTouchHelpCallback;
import dushisll.com.realmsample.util.RealmUtil;

/**
 * Created by we on 2016/11/23.
 */

public class SelectedPersonActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<Person> persons = new ArrayList<>();
    private SelectedPersonAdapter mAdapter;
    private RealmUtil mRealmUtil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(mToolbar,"查询所有");
        initData();
        initListener();
    }

    private void initData() {
        mRealmUtil = new RealmUtil(this);
        persons = mRealmUtil.quaryAllPerson();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new SelectedPersonAdapter(this,persons, R.layout.item_person);
        mRecyclerView.setAdapter(mAdapter);

        setSwipeDelete();

        Snackbar.make(mRecyclerView,"滑动删除Item，点击Item进入修改界面",Snackbar.LENGTH_SHORT).show();
    }

    private void setSwipeDelete() {
        DefaultItemTouchHelpCallback callback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
                mRealmUtil.delPerson(persons.get(adapterPosition).getId());

                persons.remove(adapterPosition);
                mAdapter.notifyItemRemoved(adapterPosition);
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                mAdapter.notifyItemMoved(srcPosition,targetPosition);
                return true;
            }
        });

        callback.setDragEnable(true);
        callback.setSwipeEnable(true);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initListener() {
            mAdapter.setItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(SelectedPersonActivity.this,UpdatePersonActivity.class);
                    intent.putExtra("id",persons.get(position).getId());
                    startActivityForResult(intent,100);
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100 ){
            persons.clear();
            List<Person> p = mRealmUtil.quaryAllPerson();
            persons.addAll(p);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }
}
