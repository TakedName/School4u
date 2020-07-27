package fu.prm391.finalproject.school4u.ui.school;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fu.prm391.finalproject.school4u.R;
import fu.prm391.finalproject.school4u.adapters.SchoolAdapter;
import fu.prm391.finalproject.school4u.data.School;

public class SchoolFragment extends Fragment {
    private ListView listView;
    private ArrayList<School> schools;
    private EditText search;
    private View root;
    private AppCompatImageView imageView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_school, container, false);
        schools = new ArrayList<>();
        listView = (ListView) root.findViewById(R.id.school_list);
        initialize();
        getDataFromFireBase();
        return root;
    }
    private void initialize() {
        search=root.findViewById(R.id.search_bar);
        imageView=root.findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=search.getText().toString();
                try {
                    ArrayList<School> result = new ArrayList<>();
                    if (s != null && !s.isEmpty()) {
                        for (School school : schools) {
                            if (school.isSatisfied(s)) {
                                result.add(school);
                            }
                        }
                        SchoolAdapter adapter = new SchoolAdapter(result, getContext());
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        SchoolAdapter adapter = new SchoolAdapter(schools, getContext());
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Log.w("Error", e.toString());
                }
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
                                    school.setLocation(doc.get("location").toString());
                                    schools.add(school);
                                }
                                SchoolAdapter adapter = new SchoolAdapter(schools, getContext());
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                Log.w("Error", "Error getting documents.", task.getException());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.i("Error : ", e.toString());
        }
    }
}
