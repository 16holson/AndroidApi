package edu.weber.w01311060.cs3270a9.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.weber.w01311060.cs3270a9.models.Courses;

@Dao
public interface CourseDAO
{
    @Query("SELECT * FROM Courses")
    LiveData<List<Courses>> getAll();

    @Query("SELECT * FROM Courses WHERE id = :key")
    Courses findCourseByKey(int key);

    @Query("SELECT * FROM Courses WHERE id = :id")
    Courses findCourseByCourseId(String id);

    @Delete
    void deleteCourse(Courses course);

    @Insert
    void insertCourses(Courses... courses);

    @Update
    void modifyCourse(Courses courses);
}
