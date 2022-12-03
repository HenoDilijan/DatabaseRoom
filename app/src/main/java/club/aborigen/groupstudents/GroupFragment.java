package club.aborigen.groupstudents;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import club.aborigen.groupstudents.database.DatabaseClient;
import club.aborigen.groupstudents.database.GroupEntity;
import club.aborigen.groupstudents.database.GroupWithStudents;
import club.aborigen.groupstudents.databinding.FragmentGroupBinding;

public class GroupFragment extends Fragment {
    private List<GroupWithStudents> groups = new ArrayList<>();
    private GroupAdapter adapter;

    public GroupFragment() {
        // Required empty public constructor
    }

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        //argument stuff here
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentGroupBinding binding = FragmentGroupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new GroupAdapter();

        binding.groupList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.groupList.setAdapter(adapter);

        Log.i("UWC", "Group setup done");
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        Reader reader = new Reader();
        reader.start();
        Log.i("UWC", "Group thread started");
    }

    class Reader extends Thread { //Data provider
        @Override
        public void run() {
            super.run();

            groups = DatabaseClient.getInstance(getContext()).getAppDatabase().groupDao().getAll();
            Log.i("UWC", "Groups received: " + groups.size());

            getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
        }
    }

    public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

        @NonNull
        @Override
        public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            return new GroupViewHolder(inflater.inflate(R.layout.view_group_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
            holder.name.setText(groups.get(position).group.name); ;
            holder.groupId = groups.get(position).group.id;
        }

        @Override
        public int getItemCount() {
            return groups.size();
        }

        class GroupViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            long groupId;

            public GroupViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.group_name);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.students_container, StudentFragment.newInstance(groupId))
                                .commit();
                    }
                });
            }
        }
    }
}
