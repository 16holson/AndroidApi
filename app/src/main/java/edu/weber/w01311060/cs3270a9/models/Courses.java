package edu.weber.w01311060.cs3270a9.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Courses
{
    public Courses(String id, String name, String courseCode, String startAt, String endAt)
    {
        this.id = id;
        this.name = name;
        this.courseCode = courseCode;
        this.startAt = startAt;
        this.endAt = endAt;
    }
    public Courses()
    {

    }

    @PrimaryKey(autoGenerate = true)
    private int course_key;
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "course_code")
    private String courseCode;
    @ColumnInfo(name = "start_at")
    private String startAt;
    @ColumnInfo(name = "end_at")
    private String endAt;



    public int getCourse_key()
    {
        return course_key;
    }

    public void setCourse_key(int course_key)
    {
        this.course_key = course_key;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCourseCode()
    {
        return courseCode;
    }

    public void setCourseCode(String courseCode)
    {
        this.courseCode = courseCode;
    }

    public String getStartAt()
    {
        return startAt;
    }

    public void setStartAt(String startAt)
    {
        this.startAt = startAt;
    }

    public String getEndAt()
    {
        return endAt;
    }

    public void setEndAt(String endAt)
    {
        this.endAt = endAt;
    }

    @Override
    public String toString()
    {
        return "Courses{" +
                "course_key=" + course_key +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                '}';
    }
}
