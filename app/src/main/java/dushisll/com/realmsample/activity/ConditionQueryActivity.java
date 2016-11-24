package dushisll.com.realmsample.activity;

import android.media.DrmInitData;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dushisll.com.realmsample.R;
import dushisll.com.realmsample.adapter.SelectedPersonAdapter;
import dushisll.com.realmsample.bean.Person;
import dushisll.com.realmsample.util.RealmUtil;

/**
 * Created by we on 2016/11/24.
 */

public class ConditionQueryActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.et_id)
    EditText mEtId;
    @BindView(R.id.et_age)
    EditText mEtAge;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private RealmUtil mRealmUtil;
    private List<Person> persons = new ArrayList<>();
    private SelectedPersonAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_condition_query;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setToolbar(mToolBar,"条件查询");
        initData();
    }

    private void initData() {
        mRealmUtil = new RealmUtil(this);
        mAdapter = new SelectedPersonAdapter(this,persons,R.layout.item_person);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.setAdapter(mAdapter);
    }

    @OnClick({R.id.btn_query_id, R.id.btn_query_age})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query_id:
                queryById();
                break;
            case R.id.btn_query_age:
                queryByAge();
                break;
        }
    }

    private void queryById() {
        String id = mEtId.getText().toString().trim();
        if(TextUtils.isEmpty(id)){
            Toast.makeText(ConditionQueryActivity.this,"请输入ID",Toast.LENGTH_SHORT).show();
            return;
        }

        persons.clear();

        Person p = mRealmUtil.queryPersonById(id);
        if(p != null){
            persons.add(p);
        }else{
            Toast.makeText(ConditionQueryActivity.this,"无该ID",Toast.LENGTH_SHORT).show();
        }
        mAdapter.notifyDataSetChanged();
    }

    private void queryByAge() {
        String age = mEtAge.getText().toString().trim();
        if(TextUtils.isEmpty(age)){
            Toast.makeText(ConditionQueryActivity.this,"请输入Age",Toast.LENGTH_SHORT).show();
            return;
        }

        persons.clear();

        List<Person> ps = mRealmUtil.queryPersonByAge(Integer.parseInt(age));
        if(ps != null){
            persons.addAll(ps);
        }else{
            Toast.makeText(ConditionQueryActivity.this,"无该Age",Toast.LENGTH_SHORT).show();
        }
        mAdapter.notifyDataSetChanged();
    }

}
