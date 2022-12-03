package club.aborigen.groupstudents;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import club.aborigen.groupstudents.database.DatabaseClient;
import club.aborigen.groupstudents.database.StudentEntity;
import club.aborigen.groupstudents.databinding.FragmentStudentBinding;

public class StudentFragment extends Fragment {
    final static String GROUP_ID = "GROUP_ID";

    private List<StudentEntity> students = new ArrayList<>();
    private StudentAdapter adapter;

    public StudentFragment() {
        // Required empty public constructor
    }

    public static StudentFragment newInstance(long groupId) {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        args.putLong(GROUP_ID, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentStudentBinding binding = FragmentStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new StudentAdapter();

        binding.studentList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.studentList.setAdapter(adapter);

        Log.i("UWC", "Student setup done");
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        Reader reader = new Reader();
        reader.start();
        Log.i("UWC", "Student provider started"); //MVVM required
    }

    class Reader extends Thread { //Data provider
        @Override
        public void run() {
            super.run();

            long groupId = getArguments().getLong(GROUP_ID);
            students = DatabaseClient.getInstance(getContext()).getAppDatabase().studentDao().getAllFromGroup(groupId);
//            students = DatabaseClient.getInstance(getContext()).getAppDatabase().studentDao().getAll();
            Log.i("UWC", "Students received: " + students.size());

            getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
        }
    }

    public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

        @NonNull
        @Override
        public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            return new StudentViewHolder(inflater.inflate(R.layout.view_student_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
            holder.name.setText(students.get(position).name); ;
        }

        @Override
        public int getItemCount() {
            return students.size();
        }

        class StudentViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            public StudentViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.student_name);
            }
        }
    }
}
