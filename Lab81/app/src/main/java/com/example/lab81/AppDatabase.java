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
                    .fallbackToDestructiveMigration() // Удалить данные при обновлении схемы
                    .build();
        }
        return instance;
    }
}