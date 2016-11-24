package dushisll.com.realmsample.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dushisll.com.realmsample.R;
import dushisll.com.realmsample.bean.Person;
import dushisll.com.realmsample.util.RealmUtil;
import io.realm.Realm;

/**
 * Created by we on 2016/11/24.
 */

public class OtherQueryActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.tv_average_age)
    TextView mTvAverageAge;
    @BindView(R.id.tv_sum_age)
    TextView mTvSumAge;
    @BindView(R.id.tv_max_age)
    TextView mTvMaxAge;

    private Realm mRealm;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_query;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(mToolBar,"其他查询");
        mRealm = Realm.getDefaultInstance();

        getAverageAge();

        getSumAge();

        getMaxId();
    }

    private void getMaxId() {
        Number max = mRealm.where(Person.class).findAll().max("age");
        mTvMaxAge.setText(max +"岁");
    }


    private void getSumAge() {
        Number sum = mRealm.where(Person.class).findAll().sum("age");
        mTvSumAge.setText(sum+"岁");
    }

    private void getAverageAge() {
        double age = mRealm.where(Person.class).findAll().average("age");
        mTvAverageAge.setText(age + "岁");
    }
    
}
