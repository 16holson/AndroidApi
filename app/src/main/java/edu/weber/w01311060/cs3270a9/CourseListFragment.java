package edu.weber.w01311060.cs3270a9;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.weber.w01311060.cs3270a9.models.Courses;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseListFragment extends Fragment implements CourseRecyclerAdapter.onCourseListener
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private FloatingActionButton addBtn;

    public CourseListFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseListFragment newInstance(String param1, String param2)
    {
        CourseListFragment fragment = new CourseListFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_course_list, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Toolbar toolbar = root.findViewById(R.id.listToolbar);
        toolbar.setTitle("Courses");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        addBtn = root.findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AddCourseDialog dialog = new AddCourseDialog();
                dialog.show(getParentFragmentManager(), "addDialog");
            }
        });

        RecyclerView rv = root.findViewById(R.id.recycler);
        CourseRecyclerAdapter adapter = new CourseRecyclerAdapter(new ArrayList<Courses>(), this);

        if(rv instanceof RecyclerView)
        {
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setAdapter(adapter);
            rv.setHasFixedSize(false);
        }
        CourseViewModel vm = new ViewModelProvider(this)
                .get(CourseViewModel.class);
        vm.getAllCourses(getContext())
                .observe(this, new Observer<List<Courses>>()
        {
            @Override
            public void onChanged(List<Courses> courses)
            {
                if(courses != null)
                {
                    adapter.addItems(courses);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.courselist, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.loadCourses:
                loadCourses();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadCourses()
    {
        GetCourseTask task = new GetCourseTask();
        task.execute(getContext());
    }

    @Override
    public void onCourseClick(Courses course)
    {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        CourseViewFragment dialog = new CourseViewFragment();
        dialog.show(ft, "CourseViewFragment");
        dialog.showCourse(course);
    }

    @Override
    public void onLongCourseClick(Courses course)
    {
        AssignmentDialog assignmentDialog = new AssignmentDialog();
        assignmentDialog.show(getParentFragmentManager(), "AssignmentDialog");
        assignmentDialog.setCourseId(course.getId());
    }
}