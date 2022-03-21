package edu.weber.w01311060.cs3270a9;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.weber.w01311060.cs3270a9.models.Courses;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder>
{

    private final List<Courses> values;
    private onCourseListener mOnCourseListener;

    public CourseRecyclerAdapter(List<Courses> values, onCourseListener onCourseListener)
    {
        this.values = values;
        this.mOnCourseListener = onCourseListener;
    }
    public CourseRecyclerAdapter(List<Courses> values)
    {
        this.values = values;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public View view;
        public TextView courseInfo;
        public Courses courseItem;
        public onCourseListener onCourseListener;
        public ViewHolder(@NonNull View itemView, onCourseListener onCourseListener)
        {
            super(itemView);
            this.view = itemView;
            courseInfo = view.findViewById(R.id.courseInfo);
            this.onCourseListener = onCourseListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            onCourseListener.onCourseClick(values.get(getAdapterPosition()));
        }
    }
    public interface onCourseListener
    {
        void onCourseClick(Courses course);
    }

    public void addItems(List<Courses> courses)
    {
        values.clear();
        values.addAll(courses);

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.course_view, parent, false);
        return new ViewHolder(view, mOnCourseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Courses course = values.get(position);
        if(course != null)
        {
            holder.courseItem = course;
            holder.courseInfo.setText(course.getName());
            holder.courseInfo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mOnCourseListener.onCourseClick(course);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return values.size();
    }
}
