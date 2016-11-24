package dushisll.com.realmsample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dushisll.com.realmsample.R;

/**
 * Created by we on 2016/11/24.
 */

public class AsyncActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.btn_query)
    Button mBtnQuery;
    @BindView(R.id.btn_async)
    Button mBtnAsync;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(mToolBar,"异步操作");

        mBtnAdd.setText("异步增、删");
        mBtnQuery.setText("异步改、查");
        mBtnAsync.setVisibility(View.GONE);
    }

    @OnClick({R.id.btn_add, R.id.btn_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                startActivity(new Intent(AsyncActivity.this,AsyncAddActivity.class));
                break;
            case R.id.btn_query:
                startActivity(new Intent(AsyncActivity.this,AsyncQueryActivity.class));
                break;
        }
    }
}
