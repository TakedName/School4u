package fu.prm391.finalproject.school4u.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fu.prm391.finalproject.school4u.R;
import fu.prm391.finalproject.school4u.adapters.MyAdapter;
import fu.prm391.finalproject.school4u.data.School;
import fu.prm391.finalproject.school4u.ui.school.SchoolDetail;

public class HomeFragment extends Fragment {
    private ListView listView;
    private HomeViewModel homeViewModel;
    private ArrayList<School> schools;
    private SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        schools = new ArrayList<>();
        listView = (ListView) root.findViewById(R.id.school_list);
        searchView = root.findViewById(R.id.search);
//        searchView.onActionViewExpanded();
        initialize();
        getDataFromFireBase();
        return root;
    }

    private void initialize() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    ArrayList<School> result = new ArrayList<>();
                    if (s != null && !s.isEmpty()) {
                        for (School school : schools) {
                            if (school.isSatisfied(s)) {
                                result.add(school);
                            }
                        }
                        MyAdapter adapter = new MyAdapter(result, getContext());
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        MyAdapter adapter = new MyAdapter(schools, getContext());
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Log.w("Error", e.toString());
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), SchoolDetail.class);
                intent.putExtra("school", schools.get(i));
                startActivityForResult(intent, 100);
            }
        });
    }

    public void getDataFromFireBase() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("prm391").document("pe").collection("school")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            School school = new School();
                                            school.setName(doc.get("name").toString());
                                            school.setCode(doc.get("code").toString());
                                            school.setAvatar(doc.get("avatar").toString());
                                            school.setShortDescription(doc.get("shortDescription").toString());
                                            schools.add(school);
                                        }
                                        MyAdapter adapter = new MyAdapter(schools, getContext());
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Log.w("Error", "Error getting documents.", task.getException());
                                    }
                                }
                            });
                }
            }).start();
        } catch (Exception e) {
            Log.i("Error : ", e.toString());
        }
    }
}
