package fu.prm391.finalproject.school4u.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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

public class NotificationsFragment extends Fragment {
    private FirebaseUser currentUser;
    private EditText content;
    private NotificationsViewModel notificationsViewModel;
    private View root;
    private Button btn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        initialize();

        return root;
    }

    private void initialize() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        content = root.findViewById(R.id.new_forum_content);
        btn = root.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c = content.getText().toString();
                try {
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
                                        Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Something wrong. Try again", Toast.LENGTH_LONG).show();
                                        Log.w("Error adding document", e);
                                    }
                                });
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something wrong. Try again", Toast.LENGTH_LONG).show();
                    Log.w("Error adding document", e.toString());
                }
            }
        });
    }
}
