package dushisll.com.realmsample.util;

import android.content.Context;

import java.util.List;

import dushisll.com.realmsample.bean.Person;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by we on 2016/11/23.
 */

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
}
