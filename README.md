#### Realm 简介

数据库 Reaml , 是用来替代sqlite的一种解决方案。

官方地址：[https://realm.io/docs/java/latest/](http://note.youdao.com/)


#### 环境配置

在项目的 build.gradle 文件中加上

```
repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.2'
        classpath "io.realm:realm-gradle-plugin:2.2.0"
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
```
在app的build.gradle的文件加上

```
apply plugin: 'realm-android'
```

#### 初始化Realm

- 1、在 Application的onCreate()方法中Realm.init()

```
public class XApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
```

- 2、在Application的onCreate()方法中对Realm进行相关配置

> 1、使用默认配置

```
public class XApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        //默认配置
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        
    }
}
```
> 2、使用自定义配置

```
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
                .name("myRealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

    }
}
```

- 3、在AndroidManifest.xml 配置自定义的Application

```
<application
        android:name=".XApp"
        >
    ......    
</application>

```

#### 创建实体

- 1、新建一个类继承RealmObject

```
package dushisll.com.realmsample;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by we on 2016/11/22.
 */

public class Person extends RealmObject implements Parcelable {
    private String name;
    private int age;

    @PrimaryKey
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.id);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}

```
(parcelable接口采用插件自动生成)

- 2、其他相关说明

> 1、支持的数据类型

boolean ,byte ,short ,int , long ,float ,double ,String ,Date ,byte[]
在Realm中byte,short,int,long最终都被映射成long类型

> 2、注解说明

@PrimaryKey

①字段必须是String、integer、byte、short、 int、long 以及它们的封装类Byte, Short, Integer, and Long

②使用了该注解之后可以使用copyToRealmOrUpdate()方法，通过主键查询它的对象，如果查询到了，则更新它，否则新建一个对象来代替。

③使用了该注解将默认设置@index注解

④使用了该注解之后，创建和更新数据将会慢一点，查询数据会快一点。

@Required

数据不能为null

@Ignore

忽略，即该字段不被存储到本地


@Index

 为这个字段添加一个搜索引擎，这将使插入数据变慢、数据增大，但是查询会变快。建议在需要优化读取性能的情况下使用。
 
 
#### 具体操作

- 1、普通的增删该查

```
public class RealmUtil {
    public static final String DB_NAME = "myRealm1.realm";
    private Realm mRealm;

    public RealmUtil(Context context){
        mRealm = Realm.getDefaultInstance();
    }

    public void addPerson(Person p){
        mRealm.beginTransaction();
        mRealm.copyToRealm(p);
        mRealm.commitTransaction();
    }

    public void delPerson(String id){
        Person p = mRealm.where(Person.class).equalTo("id",id).findFirst();
        mRealm.beginTransaction();
        p.deleteFromRealm();
        mRealm.commitTransaction();
    }

    public void updatePerson(String id,String newName){
        Person p = mRealm.where(Person.class).equalTo("id",id).findFirst();
        mRealm.beginTransaction();
        p.setName(newName);
        mRealm.commitTransaction();
    }

    public List<Person> quaryAllPerson(){
        RealmResults<Person> persons = mRealm.where(Person.class).findAll();
        //降序
        persons.sort("id", Sort.DESCENDING);
        return  mRealm.copyFromRealm(persons);
    }

    public Person queryPersonById(String id){
        Person p = mRealm.where(Person.class).equalTo("id",id).findFirst();
        return p;
    }

    public List<Person> queryPersonByAge(int age){
        RealmResults<Person> persons = mRealm.where(Person.class).equalTo("age",age).findAll();
        return mRealm.copyFromRealm(persons);
    }

    public boolean isPersonExit(String id){
        Person p = mRealm.where(Person.class).equalTo("id",id).findFirst();
        if(p == null){
            return  false;
        }else{
            return  true;
        }
    }

    public Realm getRealm(){
        return  mRealm;
    }

    public void close(){
        if(mRealm != null){
            mRealm.close();
        }
    }

```

- 2、异步增删该查

1、 增

```
    private void addPerson(final Person p, final ImageView iv) {
        addTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(p);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext,"添加成功",Toast.LENGTH_SHORT).show();
                iv.setSelected(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(mContext,"添加失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

```

2、删

```
    private void deletePerson(final String id, final ImageView iv) {
        deleteTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person p = realm.where(Person.class).equalTo("id",id).findFirst();
                p.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext,"取消添加成功",Toast.LENGTH_SHORT).show();
                iv.setSelected(false);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(mContext,"取消添加失败",Toast.LENGTH_SHORT).show();
            }
        });

    }
```

3、改

        updateTask =   mRealm.executeTransactionAsync(new Realm.Transaction() {
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
    
    
4、查

```
 persons = mRealmUtil.getRealm().where(Person.class).findAllAsync();
        persons.addChangeListener(new RealmChangeListener<RealmResults<Person>>() {
            @Override
            public void onChange(RealmResults<Person> element) {
                element.sort("id", Sort.DESCENDING);

                List<Person> datas = mRealmUtil.getRealm().copyFromRealm(element);
                mPersons.clear();
                mPersons.addAll(datas);
                mAdapter.notifyDataSetChanged();
            }
        });
```

注意，在Activity销毁的时候得取消任务

```
        persons = mRealmUtil.getRealm().where(Person.class).findAllAsync();
        persons.addChangeListener(new RealmChangeListener<RealmResults<Person>>() {
            @Override
            public void onChange(RealmResults<Person> element) {
                element.sort("id", Sort.DESCENDING);

                List<Person> datas = mRealmUtil.getRealm().copyFromRealm(element);
                mPersons.clear();
                mPersons.addAll(datas);
                mAdapter.notifyDataSetChanged();
            }
        });
        
```
