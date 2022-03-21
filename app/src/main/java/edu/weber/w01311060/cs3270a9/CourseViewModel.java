package edu.weber.w01311060.cs3270a9;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.weber.w01311060.cs3270a9.db.AppDatabase;
import edu.weber.w01311060.cs3270a9.models.Courses;

public class CourseViewModel extends ViewModel
{
    private LiveData<List<Courses>> courseList;

    public LiveData<List<Courses>> getAllCourses(Context context)
    {
        if(courseList == null)
        {
            courseList = AppDatabase.getInstance(context).getCourseDao().getAll();
        }
        return courseList;
    }
}
