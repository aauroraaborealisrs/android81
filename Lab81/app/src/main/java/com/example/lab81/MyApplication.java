package com.example.lab81;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class MyApplication extends Application {
    private TaskViewModel taskViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(this).create(TaskViewModel.class);
    }

    public TaskViewModel getTaskViewModel() {
        return taskViewModel;
    }
}