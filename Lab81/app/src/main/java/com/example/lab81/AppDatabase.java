package com.example.lab81;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {TaskModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract TaskDao taskDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .build();

            // Заполнение базы данных начальными данными
            new Thread(() -> populateDatabase(instance)).start();
        }
        return instance;
    }

    private static void populateDatabase(AppDatabase db) {
        TaskDao dao = db.taskDao();
        if (dao.getCount() == 0) { // Проверяем, пуста ли таблица
            dao.insert(new TaskModel("Задача 1", "Описание задачи 1"));
            dao.insert(new TaskModel("Задача 2", "Описание задачи 2"));
            dao.insert(new TaskModel("Задача 3", "Описание задачи 3"));
        }
    }


}