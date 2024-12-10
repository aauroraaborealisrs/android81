package com.example.lab81;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements OnNoteClickListener {

    private static final String task_key = "TASK";
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView btnAdd = findViewById(R.id.btn_add);

        if (findViewById(R.id.fragment_detail_container) == null) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController);
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (navController.getCurrentDestination().getId() == R.id.noteListFragment) {
                    btnAdd.setVisibility(View.VISIBLE);
                } else {
                    btnAdd.setVisibility(View.INVISIBLE);
                }
            });
        }

        btnAdd.setOnClickListener(view -> {
            TaskModel task = new TaskModel("Новая заметка", "Описание заметки");
            taskViewModel.insert(task);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public void onNoteClick(TaskModel task) {
        View detail_container = findViewById(R.id.fragment_detail_container);

        if(detail_container != null) {
            DetailFragment details = new DetailFragment();
            Bundle args = new Bundle();
            args.putSerializable(task_key, task);
            details.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail_container, details)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            Bundle args = new Bundle();
            args.putSerializable(task_key, task);
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(R.id.action_noteListFragment_to_detailFragment, args);
        }
    }
}