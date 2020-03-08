package com.ulsa.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ulsa.newsapp.Activity.ListNewsActivity;
import com.ulsa.newsapp.Controller.Controller;
import com.ulsa.newsapp.Interface.IconSourceGet;
import com.ulsa.newsapp.Interface.ItemClickListener;
import com.ulsa.newsapp.Model.IconSourceModel;
import com.ulsa.newsapp.Model.SourceModel;
import com.ulsa.newsapp.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceAdapter.ListSourceViewHolder> {

    private Context context;
    private SourceModel list;
    private IconSourceGet iconSourceInterface;


    public ListSourceAdapter(Context context, SourceModel list) {
        this.context = context;
        this.list = list;
        iconSourceInterface = Controller.getIcon();
    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.source_layout, parent, false);
        return new ListSourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSourceViewHolder holder, int position) {

        StringBuilder stringApi = new StringBuilder("https://besticon-demo.herokuapp.com/allicons.json?url=");
        stringApi.append(list.getSources().get(position).getUrl());
        iconSourceInterface.getIconUrl(stringApi.toString()).enqueue(new Callback<IconSourceModel>() {
            @Override
            public void onResponse(Call<IconSourceModel> call, Response<IconSourceModel> response) {
                if (response.body().getIcons().size() > 0) {
                    Picasso.get().load(response.body().getIcons().get(0).getUrl())
                            .into(holder.img_sources);

                    holder.title_sources.setText(list.getSources().get(position).getName());

                    holder.setItemClickListener((view, pos, isLongClick) -> {
                        Intent intent = new Intent(context, ListNewsActivity.class);
                        intent.putExtra("id",list.getSources().get(pos).getId());
                        context.startActivity(intent);

                    });
                }
            }

            @Override
            public void onFailure(Call<IconSourceModel> call, Throwable t) {

            }
        });
    }

    @Override
        public int getItemCount() {
            return list.getSources().size();
    }

    class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemClickListener itemClickListener;
        TextView title_sources;
        CircleImageView img_sources;
        ProgressBar progress_bar_load_photo;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ListSourceViewHolder(@NonNull View itemView) {
            super(itemView);
            title_sources = itemView.findViewById(R.id.title_sources);
            progress_bar_load_photo = itemView.findViewById(R.id.progress_bar_load_photo);
            img_sources = itemView.findViewById(R.id.circle_source);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);

        }
    }
}
