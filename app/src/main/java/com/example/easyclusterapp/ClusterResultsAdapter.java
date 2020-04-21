package com.example.easyclusterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ClusterResultsAdapter extends ArrayAdapter<ClusterResult> {

    private int layoutX;
    private LayoutInflater layoutInflater;
    private List<ClusterResult> clusterResults;

    public ClusterResultsAdapter(Context context, int resource, List<ClusterResult> clusterResults) {
        super(context, resource, clusterResults);
        this.layoutX = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.clusterResults = clusterResults;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup vg) {
        View view = layoutInflater.inflate(this.layoutX, vg, false);

        TextView idxView = view.findViewById(R.id.ml_idx);
        TextView dataView = view.findViewById(R.id.ml_data);
        TextView clusterView = view.findViewById(R.id.ml_cluster);
        ImageView imgRes = view.findViewById(R.id.ml_iv);

        final ClusterResult clusterResult = clusterResults.get(pos);

        idxView.setText(clusterResult.getIdx());
        dataView.setText(clusterResult.getD());
        clusterView.setText(clusterResult.getCentr());
        imgRes.setImageResource(clusterResult.getImgRes());

        final ImageView imgLike = view.findViewById(R.id.ml_iv2);
        final ImageView imgDislike = view.findViewById(R.id.ml_iv3);

        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clusterResult.getImgLike() == R.drawable.ic_before_like) {
                    clusterResult.setImgLike(R.drawable.ic_like);
                    imgLike.setImageResource(R.drawable.ic_like);
                    imgDislike.setImageResource(R.drawable.ic_before_dislike);
                } else {
                    clusterResult.setImgLike(R.drawable.ic_before_like);
                    imgLike.setImageResource(R.drawable.ic_before_like);
                }
            }
        });

        imgDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clusterResult.getImgDislike() == R.drawable.ic_before_dislike) {
                    clusterResult.setImgDislike(R.drawable.ic_dislike);
                    imgDislike.setImageResource(R.drawable.ic_dislike);
                    imgLike.setImageResource(R.drawable.ic_before_like);
                } else {
                    clusterResult.setImgDislike(R.drawable.ic_before_dislike);
                    imgDislike.setImageResource(R.drawable.ic_before_dislike);
                }
            }
        });

        return view;
    }
}
