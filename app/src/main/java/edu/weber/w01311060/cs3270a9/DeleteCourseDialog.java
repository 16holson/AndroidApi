package edu.weber.w01311060.cs3270a9;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import edu.weber.w01311060.cs3270a9.db.AppDatabase;
import edu.weber.w01311060.cs3270a9.models.Courses;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteCourseDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteCourseDialog extends DialogFragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Courses course;
    private CourseViewFragment cv;
    private onDeleteListener mCallBack;
    public DeleteCourseDialog()
    {
        // Required empty public constructor
    }

    public interface onDeleteListener
    {
        void onDelete();
    }

    public void setOnDeleteListener(onDeleteListener mCallBack)
    {
        this.mCallBack = mCallBack;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteCourseDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteCourseDialog newInstance(String param1, String param2)
    {
        DeleteCourseDialog fragment = new DeleteCourseDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("This will permanently delete the course.")
                .setTitle("Are You Sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                AppDatabase.getInstance(getContext())
                                        .getCourseDao()
                                        .deleteCourse(course);
                            }
                        }).start();
                        mCallBack.onDelete();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dismiss();
                    }
                });
        return builder.create();
    }
    public void setCourse(Courses course)
    {
        this.course = course;
    }
}