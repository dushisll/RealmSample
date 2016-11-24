package dushisll.com.realmsample.util;

import android.content.ClipData;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by we on 2016/11/23.
 */

public class DefaultItemTouchHelpCallback extends ItemTouchHelper.Callback{

    private OnItemTouchCallbackListener onItemTouchCallbackListener;

    private boolean isCanDrag = true;
    private boolean isCanSwipe = true;

    public DefaultItemTouchHelpCallback(OnItemTouchCallbackListener onItemTouchCallbackListener){
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    public void setOnItemTouchCallbackListener(OnItemTouchCallbackListener onItemTouchCallbackListener) {
        this.onItemTouchCallbackListener = onItemTouchCallbackListener;
    }

    public void setDragEnable(boolean canDrag){
        isCanDrag = canDrag;
    }

    public void setSwipeEnable(boolean canSwipe){
        isCanSwipe = canSwipe;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isCanSwipe;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isCanDrag;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            int dragFlag = ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT | ItemTouchHelper.UP
                    | ItemTouchHelper.DOWN;
            int swipeFlag = 0;

            return makeMovementFlags(dragFlag,swipeFlag);
        }else if(layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();

            int dragFlag = 0;
            int swipeFlag = 0;
            if(orientation == LinearLayoutManager.HORIZONTAL){
                swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }else if(orientation == LinearLayoutManager.VERTICAL){
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
            return makeMovementFlags(dragFlag,swipeFlag);
        }
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if(onItemTouchCallbackListener != null){
            onItemTouchCallbackListener.onMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(onItemTouchCallbackListener != null){
            onItemTouchCallbackListener.onSwiped(viewHolder.getAdapterPosition());
        }
    }

    public interface OnItemTouchCallbackListener{
        void onSwiped(int adapterPosition);

        boolean onMove(int srcPosition,int targetPosition);
    }
}
