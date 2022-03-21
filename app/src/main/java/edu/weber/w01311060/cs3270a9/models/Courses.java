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
        this.course_code = courseCode;
        this.start_at = startAt;
        this.end_at = endAt;
    }
    public Courses()
    {

    }

    @PrimaryKey(autoGenerate = true)
    private int _id;
    @ColumnInfo
    private String id;
    @ColumnInfo()
    private String name;
    @ColumnInfo
    private String course_code;
    @ColumnInfo
    private String start_at;
    @ColumnInfo
    private String end_at;

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
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

    public String getCourse_code()
    {
        return course_code;
    }

    public void setCourse_code(String course_code)
    {
        this.course_code = course_code;
    }

    public String getStart_at()
    {
        return start_at;
    }

    public void setStart_at(String start_at)
    {
        this.start_at = start_at;
    }

    public String getEnd_at()
    {
        return end_at;
    }

    public void setEnd_at(String end_at)
    {
        this.end_at = end_at;
    }

    @Override
    public String toString()
    {
        return "Courses{" +
                "_id=" + _id +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", course_code='" + course_code + '\'' +
                ", start_at='" + start_at + '\'' +
                ", end_at='" + end_at + '\'' +
                '}';
    }
}
