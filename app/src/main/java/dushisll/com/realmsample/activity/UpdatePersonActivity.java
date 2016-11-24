package dushisll.com.realmsample.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dushisll.com.realmsample.R;
import dushisll.com.realmsample.util.RealmUtil;

/**
 * Created by we on 2016/11/24.
 */

public class UpdatePersonActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.et_name)
    EditText mEtName;

    private RealmUtil mRealmUtil;
    private String id;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_update;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(mToolBar,"改");
        initData();
    }

    private void initData() {
        mRealmUtil = new RealmUtil(this);
        id = getIntent().getStringExtra("id");
    }

    @OnClick(R.id.btn_update)
    public void onClick() {
        String name = mEtName.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"请输入名字",Toast.LENGTH_SHORT).show();
            return;
        }

        mRealmUtil.updatePerson(id,name);
        setResult(RESULT_OK);
        finish();
    }
}
