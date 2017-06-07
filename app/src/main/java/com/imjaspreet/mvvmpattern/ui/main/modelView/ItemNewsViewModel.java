package com.imjaspreet.mvvmpattern.ui.main.modelView;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.imjaspreet.mvvmpattern.data.model.RedditChildren;
import com.squareup.picasso.Picasso;

/**
 * Created by jaspreet on 06/06/17.
 */

public class ItemNewsViewModel extends BaseObservable implements ViewModel{

    private Context context;
    private RedditChildren children;

    public ItemNewsViewModel(Context context, RedditChildren children){

        this.context = context;
        this.children = children;
    }

    public void onItemClick(View view) {

    }

    public String getTitle() {
        return children.data.title;
    }

    public String getThumbnail() {
        return children.data.thumbnail;
    }

    @BindingAdapter({"bind:thumbnail"})
    public static void loadImage(ImageView view, String thumbnail) {
        Picasso.with(view.getContext())
                .load(thumbnail)
                .into(view);
    }

    // Allows recycling ItemNewsViewModels within the recyclerview adapter
    public void setChildren(RedditChildren children) {
        this.children = children;
        notifyChange();
    }

    @Override
    public void destroy() {

    }
}
