package com.example.lab81;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class NoteListFragment extends Fragment {
    TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    TaskAdapter adapter;

    private OnNoteClickListener noteClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteClickListener) {
            this.noteClickListener = (OnNoteClickListener) context;
        } else {
            throw new ClassCastException(context + " must implement OnNoteClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        adapter = new TaskAdapter(taskViewModel.getAllTasks(), noteClickListener);
        recyclerView.setAdapter(adapter);

        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<TaskModel>>() {
            @Override
            public void onChanged(List<TaskModel> tasks) {
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}