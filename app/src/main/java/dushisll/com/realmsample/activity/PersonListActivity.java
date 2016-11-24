package dushisll.com.realmsample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dushisll.com.realmsample.R;
import dushisll.com.realmsample.adapter.PersonAdapter;
import dushisll.com.realmsample.bean.Person;

/**
 * Created by we on 2016/11/22.
 */

public class PersonListActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<Person> persons  = new ArrayList<>();
    private PersonAdapter mPersonAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(mToolBar,"增删");

        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mPersonAdapter = new PersonAdapter(this,persons,R.layout.item_person);
        mRecyclerView.setAdapter(mPersonAdapter);
    }

    private void initData() {
        Person p = new Person();
        p.setName("aaa");
        p.setAge(1);
        p.setId("001");
        persons.add(p);

        p = new Person();
        p.setName("bbb");
        p.setAge(2);
        p.setId("002");
        persons.add(p);

        p = new Person();
        p.setName("ccc");
        p.setAge(3);
        p.setId("003");
        persons.add(p);

        p = new Person();
        p.setName("ddd");
        p.setAge(4);
        p.setId("004");
        persons.add(p);

        p = new Person();
        p.setName("eee");
        p.setAge(5);
        p.setId("005");
        persons.add(p);

        p = new Person();
        p.setName("fff");
        p.setAge(6);
        p.setId("006");
        persons.add(p);

        p = new Person();
        p.setName("ggg");
        p.setAge(7);
        p.setId("007");
        persons.add(p);

        p = new Person();
        p.setName("hhh");
        p.setAge(8);
        p.setId("008");
        persons.add(p);

        p = new Person();
        p.setName("iii");
        p.setAge(9);
        p.setId("009");
        persons.add(p);

        p = new Person();
        p.setName("jjj");
        p.setAge(9);
        p.setId("010");
        persons.add(p);

        p = new Person();
        p.setName("jjj");
        p.setAge(10);
        p.setId("011");
        persons.add(p);

        p = new Person();
        p.setName("kkk");
        p.setAge(9);
        p.setId("012");
        persons.add(p);

        p = new Person();
        p.setName("lll");
        p.setAge(13);
        p.setId("013");
        persons.add(p);

    }

    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }


}
