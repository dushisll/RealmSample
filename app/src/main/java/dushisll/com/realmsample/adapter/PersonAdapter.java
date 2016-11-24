package dushisll.com.realmsample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import dushisll.com.realmsample.R;
import dushisll.com.realmsample.bean.Person;
import dushisll.com.realmsample.util.RealmUtil;

/**
 * Created by we on 2016/11/23.
 */

public class PersonAdapter extends BaseAdapter<Person>{

    private RealmUtil mRealmUtil;

    public PersonAdapter(Context context, List<Person> datas, int layoutId) {
        super(context, datas, layoutId);
        mRealmUtil = new RealmUtil(context);
    }

    @Override
    protected void convert(Context mContext, BaseViewHolder holder, final Person person) {
        holder.setText(R.id.name_tv,person.getName())
                .setText(R.id.age_tv,person.getAge()+"")
                .setText(R.id.id_tv,person.getId());

        final ImageView iv = holder.getView(R.id.iv_like);
        if(mRealmUtil.isPersonExit(person.getId())){
            iv.setSelected(true);
        }else {
            iv.setSelected(false);
        }

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iv.isSelected()){
                    iv.setSelected(false);
                    mRealmUtil.delPerson(person.getId());
                }else{
                    iv.setSelected(true);
                    mRealmUtil.addPerson(person);
                }
            }
        });
    }
}
