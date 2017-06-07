package com.imjaspreet.mvvmpattern.ui.main.modelView;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import com.imjaspreet.mvvmpattern.App;
import com.imjaspreet.mvvmpattern.data.Injector;
import com.imjaspreet.mvvmpattern.data.InterfaceApi;
import com.imjaspreet.mvvmpattern.data.model.RedditData;
import com.imjaspreet.mvvmpattern.data.model.RedditNewsResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;


/**
 * Created by jaspreet on 06/06/17.
 */

public class MainViewModel implements ViewModel{

    public ObservableInt progressVisibility;
    public ObservableInt recyclerViewVisibility;
    public ObservableInt getNewsButtonVisibility;

    private Context context;
    private DataListener dataListener;
    private DisposableObserver disposable;

    public MainViewModel(Context context, DataListener dataListener) {

        this.context = context;
        this.dataListener = dataListener;
        progressVisibility = new ObservableInt(View.INVISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        getNewsButtonVisibility = new ObservableInt(View.VISIBLE);
    }


    @Override
    public void destroy() {
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
        disposable = null;
        context = null;
        dataListener = null;
    }

    public void onClickGetNews(View view) {

        loadNews();
    }

    private void loadNews(){
        progressVisibility.set(View.VISIBLE);
        getNewsButtonVisibility.set(View.INVISIBLE);
        recyclerViewVisibility.set(View.INVISIBLE);

        InterfaceApi api = Injector.provideApi();
        disposable = api.getTop("", "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(App.getInstance().defaultSubscribeScheduler())
                .subscribeWith(new DisposableObserver<RedditNewsResponse>() {
                    @Override
                    public void onNext(RedditNewsResponse value) {
                        if (dataListener != null) dataListener.onRepositoriesChanged(value.data);
                        if (!value.data.children.isEmpty()) {
                            recyclerViewVisibility.set(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressVisibility.set(View.INVISIBLE);

                    }

                    @Override
                    public void onComplete() {
                        progressVisibility.set(View.INVISIBLE);
                    }
                });
    }

    public interface DataListener {
        void onRepositoriesChanged(RedditData response);
    }

}
