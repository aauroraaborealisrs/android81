package com.example.lab81;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class DetailFragment extends Fragment {
    private TaskModel task;

    TaskViewModel taskViewModel;
    private static final String task_key = "TASK";

    public DetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = (TaskModel) getArguments().getSerializable(task_key);
        }
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        EditText nameInput = view.findViewById(R.id.nameInput);
        EditText descriptionInput = view.findViewById(R.id.descriptionInput);
        CheckBox completedCheckBox = view.findViewById(R.id.completedCheckBox);
        Button saveBtn = view.findViewById(R.id.saveBtn);
        Button deleteBtn = view.findViewById(R.id.deleteBtn);

        if (task != null) {
            nameInput.setText(task.getName());
            descriptionInput.setText(task.getDescription());
            completedCheckBox.setChecked(task.isCompleted());
        }


        saveBtn.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String description = descriptionInput.getText().toString();
            if (!name.isEmpty() && !description.isEmpty()) {
                task.setName(name);
                task.setDescription(description);
                task.setCompleted(completedCheckBox.isChecked());
                taskViewModel.update(task);
                Toast.makeText(requireContext(), "Заметка сохранена", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(requireContext(), "Введите название и описание заметки", Toast.LENGTH_SHORT).show();
            }
        });

        deleteBtn.setOnClickListener(v -> {
            taskViewModel.delete(task);
            FragmentActivity activity = requireActivity();
            Toast.makeText(requireContext(), "Заметка удалена", Toast.LENGTH_SHORT).show();
            activity.getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}