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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import edu.weber.w01311060.cs3270a9.db.AppDatabase;
import edu.weber.w01311060.cs3270a9.models.Courses;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCourseDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCourseDialog extends DialogFragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private TextInputLayout id, name, courseCode, startAt, endAt;
    private FloatingActionButton saveBtn;

    public AddCourseDialog()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCourseDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCourseDialog newInstance(String param1, String param2)
    {
        AddCourseDialog fragment = new AddCourseDialog();
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
        return root = inflater.inflate(R.layout.fragment_add_course_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        requireDialog().getWindow().setWindowAnimations(R.style.AppTheme_DialogAnimation);
        requireDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        id = root.findViewById(R.id.idInput);
        name = root.findViewById(R.id.nameInput);
        courseCode = root.findViewById(R.id.courseCodeInput);
        startAt = root.findViewById(R.id.startAtInput);
        endAt = root.findViewById(R.id.endAtInput);
        saveBtn = root.findViewById(R.id.saveBtn);

        Toolbar toolbar = root.findViewById(R.id.toolbarAdd);

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });

        toolbar.setTitle("Add Course");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24);

        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String idText = id.getEditText().getText().toString();
                String nameText = name.getEditText().getText().toString();
                String courseCodeText = courseCode.getEditText().getText().toString();
                String startAtText = startAt.getEditText().getText().toString();
                String endAtText = endAt.getEditText().getText().toString();

                final Courses course = new Courses(idText, nameText, courseCodeText, startAtText, endAtText);

                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        AppDatabase.getInstance(getContext())
                                .getCourseDao()
                                .insertCourses(course);
                    }
                }).start();
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
}