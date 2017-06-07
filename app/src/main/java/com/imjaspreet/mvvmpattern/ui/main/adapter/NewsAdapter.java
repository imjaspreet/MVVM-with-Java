package com.imjaspreet.mvvmpattern.ui.main.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.imjaspreet.mvvmpattern.R;
import com.imjaspreet.mvvmpattern.data.model.RedditChildren;
import com.imjaspreet.mvvmpattern.databinding.ItemNewsBinding;
import com.imjaspreet.mvvmpattern.ui.main.modelView.ItemNewsViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by jaspreet on 07/06/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<RedditChildren> childrens;

    public NewsAdapter() {
        this.childrens = Collections.emptyList();
    }

    public NewsAdapter(List<RedditChildren> childrens) {
        this.childrens = childrens;
    }

    public void setNews(List<RedditChildren> childrens) {
        this.childrens = childrens;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNewsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_news, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindRepository(childrens.get(position));
    }

    @Override
    public int getItemCount() {
        return childrens.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ItemNewsBinding binding;

        public ViewHolder(ItemNewsBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }

        void bindRepository(RedditChildren children) {
            if (binding.getItemViewModel() == null) {
                binding.setItemViewModel(new ItemNewsViewModel(itemView.getContext(), children));
            } else {
                binding.getItemViewModel().setChildren(children);
            }
        }
    }
}
