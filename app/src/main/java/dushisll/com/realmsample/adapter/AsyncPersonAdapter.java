package dushisll.com.realmsample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import dushisll.com.realmsample.R;
import dushisll.com.realmsample.bean.Person;
import dushisll.com.realmsample.util.RealmUtil;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * Created by we on 2016/11/24.
 */

public class AsyncPersonAdapter extends BaseAdapter<Person>{

    private Realm mRealm;
    private Context mContext;
    private RealmAsyncTask addTask;
    private RealmAsyncTask deleteTask;

    public AsyncPersonAdapter(Context context, List<Person> datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
        mRealm = Realm.getDefaultInstance();
    }


    @Override
    protected void convert(Context mContext, BaseViewHolder holder, final Person person) {
        holder.setText(R.id.name_tv,person.getName())
                .setText(R.id.age_tv,person.getAge()+"")
                .setText(R.id.id_tv,person.getId());

        final ImageView iv = holder.getView(R.id.iv_like);
        if(isLiked(person.getId())){
            iv.setSelected(true);
        }else {
            iv.setSelected(false);
        }

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iv.isSelected()){
                    deletePerson(person.getId(),iv);
                }else{
                    addPerson(person,iv);
                }
            }
        });
    }

    private boolean isLiked(String id) {
        Person p=  mRealm.where(Person.class).equalTo("id",id).findFirst();
        if (p==null){
            return false;
        }else {
            return  true;
        }
    }

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

    public void CancelTask(){
        if(addTask != null && !addTask.isCancelled()){
            addTask.cancel();
        }

        if(deleteTask != null && !deleteTask.isCancelled()){
            deleteTask.cancel();
        }
    }
    
}
