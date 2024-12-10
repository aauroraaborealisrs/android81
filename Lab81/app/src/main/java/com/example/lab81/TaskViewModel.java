package com.example.lab81;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskViewModel extends AndroidViewModel {
    private TaskDao taskDao;
    private LiveData<List<TaskModel>> allTasks;
    private ExecutorService executorService;

    public TaskViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = (LiveData<List<TaskModel>>) taskDao.getAllTasks();
        executorService = Executors.newSingleThreadExecutor(); // Создание ExecutorService
    }

    public LiveData<List<TaskModel>> getAllTasks() {
        return allTasks;
    }

    public void insert(TaskModel task) {
        executorService.execute(() -> taskDao.insert(task));
    }

    public void update(TaskModel task) {
        executorService.execute(() -> taskDao.update(task));
    }

    public void delete(TaskModel task) {
        executorService.execute(() -> taskDao.delete(task));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}