package fu.prm391.finalproject.school4u.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import fu.prm391.finalproject.school4u.adapters.MyAdapter;
import fu.prm391.finalproject.school4u.data.School;

public class FirebaseHelper {

    public static void setImage(final ImageView imageView, String url){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference= storageReference.child(url);

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
               Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
               imageView.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
    public static void setViewFlipperResource (Context context, ViewFlipper viewFlipper, ArrayList<String> url) {
        for (int i = 0; i < url.size(); i++) {
            ImageView imageView = new ImageView(context);
            setImage(imageView,url.get(i));

            viewFlipper.addView(imageView);

            viewFlipper.setFlipInterval(2500);
            viewFlipper.setAutoStart(true);

            viewFlipper.startFlipping();
            viewFlipper.setInAnimation(context, android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(context, android.R.anim.slide_out_right);
        }
    }
    public static ArrayList<School> getSchoolList(){
        final ArrayList<School> schools=new ArrayList<>();
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
                                    school.setFullDescription(doc.get("fullDescription").toString());
                                    school.setLocation(doc.get("location").toString());
                                    school.setPictures((ArrayList<String>) doc.get("picture"));
                                    schools.add(school);
                                }
                            } else {
                                Log.w("Error", "Error getting documents.", task.getException());
                            }
                        }
                    });

        } catch (Exception e) {
            Log.i("Error : ", e.toString());
        }
        return schools;
    }
}
