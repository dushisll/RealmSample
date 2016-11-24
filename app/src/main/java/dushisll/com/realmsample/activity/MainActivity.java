package dushisll.com.realmsample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import dushisll.com.realmsample.R;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolBar.setTitle("RealmSample");
        setSupportActionBar(mToolBar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.btn_add, R.id.btn_query, R.id.btn_async})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                startActivity(new Intent(MainActivity.this,PersonListActivity.class));
                break;
            case R.id.btn_query:
                startActivity(new Intent(MainActivity.this,QueryActivity.class));
                break;
            case R.id.btn_async:
                startActivity(new Intent(MainActivity.this,AsyncActivity.class));
                break;
        }
    }
}
