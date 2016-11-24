package dushisll.com.realmsample.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by we on 2016/11/22.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{

    SparseArray<View> mViews;
    View mItemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mItemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    public BaseViewHolder setText(int viewId,String text){
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public BaseViewHolder setVisible(int viewId,int visible){
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }
}
