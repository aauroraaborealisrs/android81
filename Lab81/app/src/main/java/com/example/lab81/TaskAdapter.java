package com.example.lab81;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    static private LiveData<List<TaskModel>> taskList;
    private OnNoteClickListener listener;
    private OnNoteClickListener noteClickListener;
    private OnTaskChanged taskChangedListener;

    public void setTaskList(LiveData<List<TaskModel>> tasks){
        taskList = tasks;
    }
    public TaskAdapter(LiveData<List<TaskModel>> taskList, OnNoteClickListener noteClickListener, OnTaskChanged taskChangedListener) {
        this.taskList = taskList;
        this.noteClickListener = noteClickListener;
        this.taskChangedListener = taskChangedListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    /*@Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.getValue().get(position);
        holder.nameTextView.setText(task.getName());
        holder.completedCheckBox.setChecked(task.isCompleted());
        holder.taskImageView.setImageResource(R.drawable.note);

        holder.itemView.setOnClickListener(v -> listener.onNoteClick(task));
    }*/

//    @Override
//    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
//        TaskModel task = taskList.getValue().get(position);
//
//        // Установка значений для элементов списка
//        holder.nameTextView.setText(task.getName());
//        holder.completedCheckBox.setChecked(task.isCompleted());
//        holder.taskImageView.setImageResource(R.drawable.note);
//
//        // Обработчик кликов на задачу
//        holder.itemView.setOnClickListener(v -> listener.onNoteClick(task));
//
//        // Обработчик изменения состояния CheckBox
//        holder.completedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            task.setCompleted(isChecked); // Обновляем состояние
//            listener.onTaskCheckboxChanged(task); // Уведомляем через интерфейс
//        });
//    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.getValue().get(position);

        // Установка значений для элементов списка
        holder.nameTextView.setText(task.getName());
        holder.completedCheckBox.setChecked(task.isCompleted());
        holder.taskImageView.setImageResource(R.drawable.note);

        // Обработчик кликов на задачу
        holder.itemView.setOnClickListener(v -> noteClickListener.onNoteClick(task));

        // Устанавливаем слушатель для CheckBox
        holder.completedCheckBox.setOnCheckedChangeListener(null); // Сбрасываем предыдущий слушатель
        holder.completedCheckBox.setChecked(task.isCompleted()); // Устанавливаем текущее состояние
        holder.completedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked); // Обновляем состояние
            taskChangedListener.onTaskCheckboxChanged(task); // Уведомляем MainActivity
        });
    }



    @Override
    public int getItemCount() {
        try {
            return taskList.getValue().size();
        }
        catch (Exception e){
            return 0;
        }
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private CheckBox completedCheckBox;
        private ImageView taskImageView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.task_name);
            completedCheckBox = itemView.findViewById(R.id.task_checkbox);
            taskImageView = itemView.findViewById(R.id.task_image);
        }
    }
}

