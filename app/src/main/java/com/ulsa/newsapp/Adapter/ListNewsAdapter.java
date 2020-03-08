package com.ulsa.newsapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;
import com.ulsa.newsapp.Activity.DetailActivity;
import com.ulsa.newsapp.Controller.ISO8601Parser;
import com.ulsa.newsapp.Interface.ItemClickListener;
import com.ulsa.newsapp.Interface.NewsClick;
import com.ulsa.newsapp.Model.Articles;
import com.ulsa.newsapp.Model.News;
import com.ulsa.newsapp.R;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ListNewsViewHolder> {
    private List<Articles> articlesList;
    private Context context;
    private NewsClick onClick;

    public ListNewsAdapter(List<Articles> articlesList, Context context, NewsClick click) {
        this.articlesList = articlesList;
        this.context = context;
        this.onClick=click;
    }

    @NonNull
    @Override
    public ListNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_layout, parent, false);
        return new ListNewsViewHolder(view,onClick);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListNewsViewHolder holder, int position) {
        Picasso.get().load(articlesList.get(position).getUrlToImage()).into(holder.article_image);
        if (articlesList.get(position).getTitle().length() > 65) {
            holder.article_title.setText(articlesList.get(position).getTitle().substring(0, 65) + "...");
        } else {
            holder.article_title.setText(articlesList.get(position).getTitle());
            Date date = null;
            try {
                date = ISO8601Parser.parse(articlesList.get(position).getPublishedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.article_time.setReferenceTime(date.getTime());


        }

    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }


    class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView article_title;
        ItemClickListener itemClickListener;
        RelativeTimeTextView article_time;
        CircleImageView article_image;
        NewsClick newsClick;

        public ListNewsViewHolder(@NonNull View itemView, NewsClick click) {
            super(itemView);
            article_title = itemView.findViewById(R.id.article_title);
            article_image = itemView.findViewById(R.id.img_news);
            article_time = itemView.findViewById(R.id.article_time);
            itemView.setOnClickListener(this);
            this.newsClick = click;


        }


        @Override
        public void onClick(View v) {
            newsClick.newsClick(getAdapterPosition());

        }
    }


}

