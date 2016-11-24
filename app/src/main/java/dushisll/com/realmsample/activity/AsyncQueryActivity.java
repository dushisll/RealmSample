package dushisll.com.realmsample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dushisll.com.realmsample.R;
import dushisll.com.realmsample.adapter.BaseAdapter;
import dushisll.com.realmsample.adapter.SelectedPersonAdapter;
import dushisll.com.realmsample.bean.Person;
import dushisll.com.realmsample.util.DefaultItemTouchHelpCallback;
import dushisll.com.realmsample.util.RealmUtil;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by we on 2016/11/24.
 */

public class AsyncQueryActivity extends BaseActivity{
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private RealmUtil mRealmUtil;
    private List<Person> mPersons = new ArrayList<>();
    private RealmResults<Person> persons;
    private SelectedPersonAdapter mAdapter;
    private RealmAsyncTask deleteTask;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(mToolbar,"异步查、改");
        initData();
        initRecyclerView();
        getData();
        addListener();
    }

    private void getData() {
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
    }

    private void addListener() {
        mAdapter.setItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(AsyncQueryActivity.this,AsyncUpdateActivity.class);
                intent.putExtra("id",mPersons.get(position).getId());
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 100){
            mPersons.clear();
            getData();
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SelectedPersonAdapter(this,mPersons,R.layout.item_person);
        mRecyclerView.setAdapter(mAdapter);
        setSwipeDelete();

    }

    private void setSwipeDelete() {
        DefaultItemTouchHelpCallback callback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
                deletePerson(mPersons.get(adapterPosition).getId());
                mPersons.remove(adapterPosition);
                mAdapter.notifyItemRemoved(adapterPosition);
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                mAdapter.notifyItemMoved(srcPosition,targetPosition);
                return true;
            }
        });

        callback.setDragEnable(true);
        callback.setSwipeEnable(true);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void deletePerson(final String id) {
        deleteTask = mRealmUtil.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person p = realm.where(Person.class).equalTo("id",id).findFirst();

                if(p != null){
                    p.deleteFromRealm();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(AsyncQueryActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(AsyncQueryActivity.this,"删除失败",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initData() {
        mRealmUtil = new RealmUtil(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persons.removeChangeListeners();
        if(deleteTask!=null && !deleteTask.isCancelled()){
            deleteTask.cancel();
        }
    }
}
