package fu.prm391.finalproject.school4u.ui.school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import fu.prm391.finalproject.school4u.MainActivity;
import fu.prm391.finalproject.school4u.R;
import fu.prm391.finalproject.school4u.adapters.MyAdapter;
import fu.prm391.finalproject.school4u.data.School;
import fu.prm391.finalproject.school4u.services.FirebaseHelper;

public class SchoolDetail extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private School school ;
    private TextView name;
    private TextView desription;
    private TextView location;
    private Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);
        initialize();
        getSchoolInformation();
    }
    private void initialize(){
        viewFlipper = findViewById(R.id.school_detail_viewFlipper);
        toolbar = findViewById(R.id.toolbar_school_detail);
        desription = findViewById(R.id.school_detail_description);
        location = findViewById(R.id.school_detail_location);
        name = findViewById(R.id.school_detail_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
    public void getSchoolInformation(){
        Intent intent=getIntent();
        school= (School) intent.getSerializableExtra("school");
        toolbar.setTitle(school.getCode());
        getDataFromFireBase(school.getCode());
    }
    public void getDataFromFireBase(String code) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("prm391").document("pe").collection("school").whereEqualTo("code",code)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
//                                    school.setName(doc.get("name").toString());
//                                    school.setCode(doc.get("code").toString());
//                                    school.setAvatar(doc.get("avatar").toString());
//                                    school.setShortDescription(doc.get("shortDescription").toString());
//                                    school.setFullDescription(doc.get("fullDescription").toString());
//                                    school.setLocation(doc.get("location").toString());
//                                    school.setPictures((ArrayList<String>) doc.get("picture"));
//

                                    name.setText(doc.get("name").toString());
                                    FirebaseHelper.setViewFlipperResource(SchoolDetail.this, viewFlipper, (ArrayList<String>) doc.get("picture"));
                                    location.setText(doc.get("location").toString());
                                    desription.setText(doc.get("fullDescription").toString());
                                }
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

