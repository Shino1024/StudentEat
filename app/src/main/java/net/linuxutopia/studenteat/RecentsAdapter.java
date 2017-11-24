package net.linuxutopia.studenteat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 2017-11-23.
 */

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.ViewHolder> {

    private String[] dataSet;

    public RecentsAdapter(String[] dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public RecentsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new RecentsAdapter.ViewHolder();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View v;

        public ViewHolder(View v) {
            super(v);
            this.v = v;
        }

    }

}
