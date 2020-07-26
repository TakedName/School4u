package fu.prm391.finalproject.school4u.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fu.prm391.finalproject.school4u.R;
import fu.prm391.finalproject.school4u.adapters.ForumAdapter;
import fu.prm391.finalproject.school4u.data.Forum;
import fu.prm391.finalproject.school4u.ui.addForum.AddForum;
import fu.prm391.finalproject.school4u.ui.forumDetail.ForumDetail;

public class DashboardFragment extends Fragment {
    private ListView listView;
    private ArrayList<Forum> forums;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        listView = root.findViewById(R.id.forums_list);
        forums = new ArrayList<>();
        initialize();
        getDataFromFireBase();
        return root;
    }

    private void initialize() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ForumDetail.class);
                intent.putExtra("forum", forums.get(i));
                startActivityForResult(intent, 200);
            }
        });
    }

    public void getDataFromFireBase() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("prm391").document("pe").collection("forums")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            Forum forum = new Forum();
                                            forum.setAuthorId(doc.get("authorId").toString());
                                            forum.setAuthorName(doc.get("authorName").toString());
                                            forum.setContent(doc.get("content").toString());
                                            forum.setId(doc.getId());
                                            forums.add(forum);
                                        }
                                        ForumAdapter adapter = new ForumAdapter(forums, getContext());
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Log.w("Error", "Error getting documents.", task.getException());
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Error : ", e.toString());
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        } catch (Exception e) {
            Log.i("Error : ", e.toString());
        }
    }

    public void addNewForum(View v) {
        Intent intent = new Intent(getContext(), AddForum.class);
        startActivityForResult(intent, 300);
    }
}
