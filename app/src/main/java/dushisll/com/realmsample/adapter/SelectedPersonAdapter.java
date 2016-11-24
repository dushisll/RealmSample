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

public class SelectedPersonAdapter extends BaseAdapter<Person>{

    public SelectedPersonAdapter(Context context, List<Person> datas, int layoutId) {
        super(context, datas, layoutId);

    }

    @Override
    protected void convert(Context mContext, BaseViewHolder holder, final Person person) {
        holder.setText(R.id.name_tv,person.getName())
                .setText(R.id.age_tv,person.getAge()+"")
                .setText(R.id.id_tv,person.getId())
                .setVisible(R.id.iv_like,View.INVISIBLE);

    }
}
