package com.example.arobius.payrate.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class customRecyclerView extends RecyclerView {
    private View view;
    AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (mObserver != null) {
            adapter.registerAdapterDataObserver(mObserver);
            mObserver.onChanged();
        }

    }

    private void toggleViews() {
        if (getAdapter() != null && view != null) {

            if (getAdapter().getItemCount() == 0) {
                view.setVisibility(View.VISIBLE);
                setVisibility(View.GONE);//hide recy view
            } else {
                view.setVisibility(View.GONE);
                setVisibility(View.VISIBLE);

            }

        }
    }

    public void getView(View view){
        this.view = view;
    }

    public customRecyclerView(Context context) {
        super(context);
    }

    public customRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public customRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
