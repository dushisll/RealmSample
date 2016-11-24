package dushisll.com.realmsample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by we on 2016/11/22.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;

    private OnItemClickListener mItemClickListener;
    private OnLongItemClickListener mLongItemClickListener;

    public BaseAdapter(Context context, List<T> datas, int layoutId) {
        mContext = context;
        mDatas = datas;
        mLayoutId = layoutId;
    }

    public void updateData(List<T> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void addAll(List<T> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId,parent,false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        convert(mContext,holder,mDatas.get(position));

        if(mItemClickListener !=null){
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v,position);
                }
            });
        }

        if(mLongItemClickListener != null){
            holder.mItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongItemClickListener.onLongItemClickListener(v,position);
                    return false;
                }
            });
        }
    }

    protected abstract void convert(Context mContext,BaseViewHolder holder,T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnLongItemClickListener {
        void onLongItemClickListener(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setLongItemClickListener(OnLongItemClickListener longItemClickListener) {
        mLongItemClickListener = longItemClickListener;
    }
}
