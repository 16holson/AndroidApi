package edu.weber.w01311060.cs3270a9.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.weber.w01311060.cs3270a9.models.Courses;

@Database(entities = {Courses.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context)
    {
        if(instance != null)
        {
            return instance;
        }
        instance = Room.databaseBuilder(context, AppDatabase.class, "course-database").build();
        return instance;
    }

    public abstract CourseDAO getCourseDao();
}
