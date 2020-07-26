package fu.prm391.finalproject.school4u.ui.addForum;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import fu.prm391.finalproject.school4u.R;
import fu.prm391.finalproject.school4u.ui.forumDetail.ForumDetail;

public class AddForum extends AppCompatActivity {
    private FirebaseUser currentUser;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);
        initialize();
    }

    private void initialize() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        content = findViewById(R.id.new_forum_content);
    }

    public void postForum(View v) {
        String c = content.getText().toString();
        if (!c.isEmpty()) {
            Map<String, Object> forum = new HashMap<>();
            forum.put("authorId", currentUser.getUid());
            forum.put("authorName", currentUser.getEmail());
            forum.put("content", c);
            forum.put("date", Timestamp.now());

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("prm391").document("pe").collection("forums")
                    .add(forum)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            documentReference.set("documentReference").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AddForum.this, "Success", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddForum.this, "Something wrong. Try again", Toast.LENGTH_LONG).show();
                    Log.w("Error adding document", e);
                }
            });
        }
    }
}
