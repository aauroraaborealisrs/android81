package com.example.lab81;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private static final String task_key = "TASK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            TaskModel task = (TaskModel) getIntent().getSerializableExtra(task_key);
            DetailFragment detailFragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putSerializable(task_key, task);
            detailFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail_container, detailFragment).commit();
        }
    }
}