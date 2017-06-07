package com.imjaspreet.mvvmpattern.ui.main;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.imjaspreet.mvvmpattern.R;
import com.imjaspreet.mvvmpattern.data.model.RedditData;
import com.imjaspreet.mvvmpattern.databinding.ActivityMainBinding;
import com.imjaspreet.mvvmpattern.ui.main.adapter.NewsAdapter;
import com.imjaspreet.mvvmpattern.ui.main.modelView.MainViewModel;


public class MainActivity extends AppCompatActivity implements MainViewModel.DataListener{

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this, this);
        binding.setMainViewModel(mainViewModel);
        setupRecyclerView(binding.reposRecyclerView);

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        NewsAdapter adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRepositoriesChanged(RedditData response) {
        NewsAdapter adapter =
                (NewsAdapter) binding.reposRecyclerView.getAdapter();
        adapter.setNews(response.children);
        adapter.notifyDataSetChanged();
    }
}
