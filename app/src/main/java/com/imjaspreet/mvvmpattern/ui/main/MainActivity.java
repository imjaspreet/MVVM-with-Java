package com.imjaspreet.mvvmpattern.ui.main;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imjaspreet.mvvmpattern.R;
import com.imjaspreet.mvvmpattern.databinding.ActivityMainBinding;
import com.imjaspreet.mvvmpattern.ui.main.modelView.MainViewModel;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        binding.setMainViewModel(mainViewModel);
    }
}
