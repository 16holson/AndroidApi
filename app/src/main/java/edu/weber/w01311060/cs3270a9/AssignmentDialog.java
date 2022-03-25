package edu.weber.w01311060.cs3270a9;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssignmentDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignmentDialog extends DialogFragment implements GetAssignmentTask.AsyncResponse
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private ListView list;
    private String[] assignments;
    private String courseId;
    private ArrayAdapter adapter;

    public AssignmentDialog()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssignmentDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static AssignmentDialog newInstance(String param1, String param2)
    {
        AssignmentDialog fragment = new AssignmentDialog();
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
        root = inflater.inflate(R.layout.fragment_assignment_dialog, container, false);

        if(savedInstanceState != null)
        {
            assignments = savedInstanceState.getStringArray("assignments");
            setAdapter();
            list = root.findViewById(R.id.listView);
            list.setAdapter(adapter);
        }

        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().setWindowAnimations(R.style.AppTheme_DialogAnimation);

        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        requireDialog().getWindow().setWindowAnimations(R.style.AppTheme_DialogAnimation);

        Toolbar toolbar = root.findViewById(R.id.assignmentToolbar);
        toolbar.setTitle("Assignments");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putStringArray("assignments", assignments);
    }

    @Override
    public void processFinish(String[] output)
    {
        assignments = output;
        if(assignments.length == 0)
        {
            assignments = new String[]{"No Assignments"};
        }
        list = root.findViewById(R.id.listView);
        Log.d("Assignment", "assignments: " + assignments);

        setAdapter();

        list.setAdapter(adapter);

    }

    public void setAdapter()
    {
        adapter = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_list_item_1,
                assignments
        );
    }

    public void setCourseId(String id)
    {
        courseId = id;
        GetAssignmentTask task = new GetAssignmentTask(this);
        task.execute(courseId);
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);

    }
}