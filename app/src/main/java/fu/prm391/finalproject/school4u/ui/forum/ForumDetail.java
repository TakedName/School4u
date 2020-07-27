package fu.prm391.finalproject.school4u.ui.forum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fu.prm391.finalproject.school4u.R;
import fu.prm391.finalproject.school4u.adapters.CommentAdapter;
import fu.prm391.finalproject.school4u.data.Comment;
import fu.prm391.finalproject.school4u.data.Forum;
import fu.prm391.finalproject.school4u.dialog.EditDialog;
import fu.prm391.finalproject.school4u.dialog.EditDialogListener;

public class ForumDetail extends AppCompatActivity implements EditDialogListener {

    private TextView name;
    private TextView content;
    private ListView commentView;
    private EditText commentEdit;
    private Forum forum;
    private ArrayList<Comment> comments;
    private CollectionReference reference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_detail);
        getDataFromIntent();
        initialize();
        getDataFromFireBase();

    }

    private void initialize() {
        name = findViewById(R.id.forum_detail_userName);
        content = findViewById(R.id.forum_detail_content);
        commentView = findViewById(R.id.comment_list);
        commentEdit = findViewById(R.id.commnent);
        comments = new ArrayList<>();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        reference = db.collection("prm391").document("pe").collection("forums")
                .document(forum.getId()).collection("comments");
        registerForContextMenu(commentView);
        name.setText(forum.getAuthorName());
        content.setText(forum.getContent());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_forum, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        DocumentReference documentReference = reference.document(comments.get(position).getId());
        if (comments.get(info.position).getUserId().equals(currentUser.getUid())) {
            switch (item.getItemId()) {
                case R.id.menu_forum_delete:
                    documentReference.delete();
                    getDataFromFireBase();
                    break;
                case R.id.menu_forum_edit:
                    EditDialog editDialog = new EditDialog(documentReference,comments.get(position).getContent());
                    editDialog.show(getSupportFragmentManager(), "EditDialog");
                    break;
            }
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
        }
        return super.onContextItemSelected(item);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        forum = (Forum) intent.getSerializableExtra("forum");
    }

    public void getDataFromFireBase() {
        try {
            reference.orderBy("date", Query.Direction.DESCENDING)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        comments.clear();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Comment comment = new Comment();
                            comment.setUserId(doc.get("userId").toString());
                            comment.setUserName(doc.get("userName").toString());
                            comment.setContent(doc.get("content").toString());
                            comment.setDate(doc.getTimestamp("date"));
                            comment.setId(doc.getId());
                            comments.add(comment);
                        }
                        CommentAdapter adapter = new CommentAdapter(comments, ForumDetail.this);
                        commentView.setAdapter(adapter);
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

    public void addComment(View view) {
        String c = commentEdit.getText().toString();
        if (c.isEmpty() || c.length() == 0) {
            Toast.makeText(ForumDetail.this, "Please add comment before submit", Toast.LENGTH_LONG).show();
            return;
        }
        Map<String, Object> newComment = new HashMap<>();
        newComment.put("userId", currentUser.getUid());
        newComment.put("userName", currentUser.getEmail());
        newComment.put("content", c);
        newComment.put("date", Timestamp.now());

        reference.add(newComment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        getDataFromFireBase();
                        commentEdit.setText("");
                        Toast.makeText(ForumDetail.this, "Success", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForumDetail.this, "Something wrong. Try again", Toast.LENGTH_LONG).show();
                        Log.w("Error adding document", e);
                    }
                });
    }

    @Override
    public void notifyChange() {
        getDataFromFireBase();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getDataFromFireBase();
    }
}
