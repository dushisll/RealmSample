package dushisll.com.realmsample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import dushisll.com.realmsample.R;

/**
 * Created by we on 2016/11/23.
 */

public class QueryActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.btn_query)
    Button mBtnQuery;
    @BindView(R.id.btn_async)
    Button mBtnAsync;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(mToolBar,"改、查");
        mBtnAdd.setText("改、查询全部");
        mBtnQuery.setText("条件查询");
        mBtnAsync.setText("其他查询");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.btn_add, R.id.btn_query, R.id.btn_async})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                startActivity(new Intent(QueryActivity.this,SelectedPersonActivity.class));
                break;
            case R.id.btn_query:
                startActivity(new Intent(QueryActivity.this,ConditionQueryActivity.class));
                break;
            case R.id.btn_async:
                startActivity(new Intent(QueryActivity.this,OtherQueryActivity.class));
                break;
        }
    }
}
