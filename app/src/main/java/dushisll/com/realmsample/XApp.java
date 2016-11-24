package dushisll.com.realmsample;

import android.app.Application;

import dushisll.com.realmsample.util.RealmUtil;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by we on 2016/11/22.
 */

public class XApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        //默认配置
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(config);
        //自定义配置
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(RealmUtil.DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

    }
}
