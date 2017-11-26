package net.linuxutopia.studenteat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.linuxutopia.studenteat.models.RecentCardModel;

import java.util.ArrayList;
import java.util.Locale;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.ViewHolder> {

    private ArrayList<RecentCardModel> dataSet;

    public RecentsAdapter(ArrayList<RecentCardModel> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public RecentsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View inflatedLayout = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recents_card_layout, viewGroup, false
        );
        return new ViewHolder(inflatedLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.t.setText(dataSet.get(position).getTitle());
        holder.t2.setText(String.format(Locale.US, "%2.2f%%", dataSet.get(position).getRating()));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView t;
        private TextView t2;

        ViewHolder(View v) {
            super(v);
            t = v.findViewById(R.id.bulka);
            t2 = v.findViewById(R.id.bulka2);
        }

    }

}
