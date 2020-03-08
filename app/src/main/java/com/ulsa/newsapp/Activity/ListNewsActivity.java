package com.ulsa.newsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;
import com.ulsa.newsapp.Adapter.ListNewsAdapter;
import com.ulsa.newsapp.Controller.Base;
import com.ulsa.newsapp.Controller.Controller;
import com.ulsa.newsapp.Interface.ItemClickListener;
import com.ulsa.newsapp.Interface.NewsClick;
import com.ulsa.newsapp.Interface.SourcesGet;
import com.ulsa.newsapp.Model.Articles;
import com.ulsa.newsapp.Model.News;
import com.ulsa.newsapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNewsActivity extends AppCompatActivity implements ItemClickListener, NewsClick {
    @BindView(R.id.kenburns_view)
    KenBurnsView kbv;
    AlertDialog dialog;
    SourcesGet sourcesGet;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private String sourceID = "", weHotUrl = "";
    ListNewsAdapter listNewsAdapter;
    @BindView(R.id.rv_news)
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @BindView(R.id.diagonal_layout)
    DiagonalLayout diagonalLayout;
    @BindView(R.id.tv_author)
    TextView tv_author;
    @BindView(R.id.tv_top_title)
    TextView tv_title;
    List<Articles> removeArticle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        ButterKnife.bind(this);
        sourcesGet = Controller.getSources();

        initDialog();
        dialog.show();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(getBaseContext(), DetailActivity.class);
                detail.putExtra("weburl", weHotUrl);
                startActivity(detail);

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(sourceID, true);
            }


        });

        if (getIntent() != null) {
            sourceID = getIntent().getStringExtra("id");
            if (!sourceID.isEmpty()) {
                loadNews(sourceID, false);

            }

        }
    }

    public void initDialog() {
        dialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .setMessage("Loading...")
                .build();

    }

    private void loadNews(String sourceID, boolean b) {

        if (!b) {
            sourcesGet.getNews(Controller.getApiUrl(sourceID, Base.APP_ID)).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    dialog.dismiss();
                    Picasso.get().load(response.body().getArticles().get(0).getUrlToImage()).into(kbv);
                    tv_author.setText(response.body().getArticles().get(0).getAuthor());
                    tv_title.setText(response.body().getArticles().get(0).getTitle());
                    weHotUrl = response.body().getArticles().get(0).getUrl();
                    removeArticle = response.body().getArticles();
                    removeArticle.remove(0);
                    initAdapter();


                    listNewsAdapter.notifyDataSetChanged();


                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    dialog.dismiss();

                }
            });
        }else {

            sourcesGet.getNews(Controller.getApiUrl(sourceID, Base.APP_ID)).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {

                    swipeRefreshLayout.setRefreshing(false);
                    dialog.dismiss();
                    Picasso.get().load(response.body().getArticles().get(0).getUrlToImage()).into(kbv);
                    tv_author.setText(response.body().getArticles().get(0).getAuthor());
                    tv_title.setText(response.body().getArticles().get(0).getTitle());
                    weHotUrl = response.body().getArticles().get(0).getUrl();
                    removeArticle = response.body().getArticles();
                    removeArticle.remove(0);
                    initAdapter();


                    listNewsAdapter.notifyDataSetChanged();


                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    dialog.dismiss();

                }
            });

        }

    }

    @Override
    public void onClick(View view, int pos, boolean isLongClick) {

    }

    public void initAdapter() {
        listNewsAdapter = new ListNewsAdapter(removeArticle, getApplicationContext(), (NewsClick) this);
        recyclerView.setAdapter(listNewsAdapter);
    }


    @Override
    public void newsClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("idweb",removeArticle.get(position).getUrl());
        startActivity(intent);
    }
}
