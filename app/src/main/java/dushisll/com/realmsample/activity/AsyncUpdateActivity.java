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
import dushisll.com.realmsample.bean.Person;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * Created by we on 2016/11/24.
 */

public class AsyncUpdateActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.et_name)
    EditText mEtName;

    private Realm mRealm;
    private String mId;
    private RealmAsyncTask updateTask;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_async_update;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(mToolBar,"异步更新");
        mId=getIntent().getStringExtra("id");
        mRealm=Realm.getDefaultInstance();
    }

    @OnClick(R.id.btn_update)
    public void onClick() {
        final String name=mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(AsyncUpdateActivity.this,"请输入新的名字",Toast.LENGTH_SHORT).show();
            return;
        }

        updateTask=   mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person p=realm.where(Person.class).equalTo("id",mId).findFirst();
                p.setName(name);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(AsyncUpdateActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(AsyncUpdateActivity.this,"更新失败",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (updateTask!=null&&!updateTask.isCancelled()){
            updateTask.cancel();
        }
    }
}
