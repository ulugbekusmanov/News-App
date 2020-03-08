package com.ulsa.newsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.google.gson.Gson;
import com.ulsa.newsapp.Adapter.ListSourceAdapter;
import com.ulsa.newsapp.Controller.Controller;
import com.ulsa.newsapp.Interface.SourcesGet;
import com.ulsa.newsapp.Model.SourceModel;
import com.ulsa.newsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.list_source)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    RecyclerRefreshLayout refreshLayout;
    private RecyclerView.LayoutManager manager;
    private ListSourceAdapter adapter;
    private SourcesGet sourcesGet;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        ButterKnife.bind(this);
        initDialog();
        alertDialog.show();
        sourcesGet = Controller.getSources();
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        loadSources(true);
        refreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                alertDialog.show();
                loadSources(false);
            }
        });




    }


    private void loadSources(boolean isRefreshing) {
        if (!isRefreshing) {
            String cache = Paper.book().read("cache");
            if (cache != null && !cache.isEmpty()) {
                SourceModel model = new Gson().fromJson(cache, SourceModel.class);
                adapter = new ListSourceAdapter(getBaseContext(), model);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            } else {
                sourcesGet.getSources().enqueue(new Callback<SourceModel>() {
                    @Override
                    public void onResponse(Call<SourceModel> call, Response<SourceModel> response) {
                        refreshLayout.setRefreshing(false);

                        adapter = new ListSourceAdapter(getBaseContext(), response.body());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);

                        Paper.book().write("cache", new Gson().toJson(response.body()));
                        alertDialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<SourceModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());

                    }
                });
            }

        } else {
            alertDialog.show();
            sourcesGet.getSources().enqueue(new Callback<SourceModel>() {
                @Override
                public void onResponse(Call<SourceModel> call, Response<SourceModel> response) {
                    alertDialog.dismiss();
                    adapter = new ListSourceAdapter(getBaseContext(), response.body());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                    Paper.book().write("cache", new Gson().toJson(response.body()));
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<SourceModel> call, Throwable t) {
                    refreshLayout.setRefreshing(false);
                    Log.d(TAG, "onFailure: " + t.getMessage());


                }
            });

        }


    }
    public void initDialog(){
        alertDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .setMessage("Loading...")
                .build();
    }

}



