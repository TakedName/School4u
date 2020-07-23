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


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fu.prm391.finalproject.school4u.R;
import fu.prm391.finalproject.school4u.adapters.MyAdapter;
import fu.prm391.finalproject.school4u.data.School;
import fu.prm391.finalproject.school4u.services.FirebaseHelper;
import fu.prm391.finalproject.school4u.ui.dashboard.DashboardFragment;
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
        initialize();
        getDataFromFireBase();
        return root;
    }
    private void initialize(){
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
                    }}
                catch (Exception e){
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
                Intent intent= new Intent(getContext(), SchoolDetail.class);
                intent.putExtra("school",schools.get(i));
                startActivityForResult(intent,100);
            }
        });
    }
    public void getDataDemoData(){
        ArrayList<String> img= new ArrayList<>();
        img.add("https://s3.gaming-cdn.com/images/products/4361/orig/sword-art-online-alicization-lycoris-cover.jpg");
        img.add("https://salt.tikicdn.com/cache/w390/ts/product/fb/73/28/9bacd3e64b6bfbd2b4eb59798b5db965.jpg");
        img.add("https://znews-photo.zadn.vn/w660/Uploaded/lce_uxlcq/2020_06_08/kirito_lqm.jpg");
        img.add("https://image.thanhnien.vn/660/uploaded/vietthong/2020_07_01/thumb2_vntp.jpg");
        schools.add(new School("fpt1", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
               " String shortDescription", "String fullDescription",img, null));
        schools.add(new School("fpt2", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
                " String shortDescription", "String fullDescription",img, null));
        schools.add(new School("fpt3", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
                " String shortDescription", "String fullDescription",img, null));
        schools.add(new School("fpt3", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
                " String shortDescription", "String fullDescription",img, null));
        schools.add(new School("fpt4", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
                " String shortDescription", "String fullDescription",img, null));
        schools.add(new School("fpt5", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
                " String shortDescription", "String fullDescription",img, null));
        schools.add(new School("fpt6", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
                " String shortDescription", "String fullDescription",img, null));
        schools.add(new School("fpt7", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
                " String shortDescription", "String fullDescription",img, null));
        schools.add(new School("fpt8", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
                " String shortDescription", "String fullDescription",img, null));
        schools.add(new School("fpt9", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
                " String shortDescription", "String fullDescription",img, null));
        schools.add(new School("fpt10", "fpt","Đại học","700512", "Khu Giáo dục và Đào tạo - Khu Công nghệ cao Hòa Lạc - Km29 Đại lộ Thăng Long, Thạch Thất, TP.Hà Nội",
                " String shortDescription", "String fullDescription",img, null));
        MyAdapter adapter = new MyAdapter(schools, getContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
//                                    school.setFullDescription(doc.get("fullDescription").toString());
//                                    school.setLocation(doc.get("location").toString());
//                                    school.setPictures((ArrayList<String>) doc.get("picture"));
                                    schools.add(school);
                                }
                            } else {
                                Log.w("Error", "Error getting documents.", task.getException());
                            }
                        }
                    });
            MyAdapter adapter = new MyAdapter(schools, getContext());
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.i("Error : ", e.toString());
        }
    }
}
