package dushisll.com.realmsample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dushisll.com.realmsample.R;
import dushisll.com.realmsample.adapter.AsyncPersonAdapter;
import dushisll.com.realmsample.bean.Person;

/**
 * Created by we on 2016/11/24.
 */

public class AsyncAddActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<Person> persons = new ArrayList<>();
    private AsyncPersonAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(mToolbar,"异步增、删");
        initData();
        setRecyclerView();
    }

    private void initData() {
        for(int i=0;i<15;i++){
            Person p = new Person();
            p.setName("shiliang"+(i%5));
            p.setAge(i%4);
            p.setId("20"+i);
            persons.add(p);
        }
    }

    private void setRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AsyncPersonAdapter(this,persons,R.layout.item_person);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.CancelTask();
    }
}
