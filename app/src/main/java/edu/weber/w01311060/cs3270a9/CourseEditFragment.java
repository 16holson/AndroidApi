package edu.weber.w01311060.cs3270a9;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.weber.w01311060.cs3270a9.db.AppDatabase;
import edu.weber.w01311060.cs3270a9.models.Courses;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseEditFragment extends DialogFragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private EditText idText;
    private EditText nameText;
    private EditText courseCodeText;
    private EditText startAtText;
    private EditText endAtText;
    private Courses course;
    private FloatingActionButton saveBtn;
    private onUpdateListener mCallBack;


    public CourseEditFragment()
    {
        // Required empty public constructor
    }

    public interface onUpdateListener
    {
        void onUpdate(Courses course);
    }

    public void setOnUpdateListener(CourseEditFragment.onUpdateListener mCallBack)
    {
        this.mCallBack = mCallBack;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseEditFragment newInstance(String param1, String param2)
    {
        CourseEditFragment fragment = new CourseEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog_FullScreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_course_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        requireDialog().getWindow().setWindowAnimations(R.style.AppTheme_DialogAnimation);
        requireDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        idText = root.findViewById(R.id.idText);
        nameText = root.findViewById(R.id.nameText);
        courseCodeText = root.findViewById(R.id.courseCodeText);
        startAtText = root.findViewById(R.id.startAtText);
        endAtText = root.findViewById(R.id.endAtText);
        saveBtn = root.findViewById(R.id.saveBtn);

        idText.setText(course.getId());
        nameText.setText(course.getName());
        courseCodeText.setText(course.getCourse_code());
        startAtText.setText(course.getStart_at());
        endAtText.setText(course.getEnd_at());


        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Course");
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24);

        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //update database
                course.setId(idText.getText().toString());
                course.setName(nameText.getText().toString());
                course.setCourse_code(courseCodeText.getText().toString());
                course.setStart_at(startAtText.getText().toString());
                course.setEnd_at(endAtText.getText().toString());
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        AppDatabase.getInstance(getContext())
                                .getCourseDao()
                                .modifyCourse(course);
                    }
                }).start();
                mCallBack.onUpdate(course);
                dismiss();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().setWindowAnimations(R.style.AppTheme_DialogAnimation);

        return dialog;
    }

    public void saveCourse(Courses course)
    {
        this.course = course;
    }
}