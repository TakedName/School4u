package fu.prm391.finalproject.school4u.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.firestore.DocumentReference;

import fu.prm391.finalproject.school4u.R;

public class EditDialog extends AppCompatDialogFragment{
    private DocumentReference reference;
    private String oldContent;
    private EditText content;
    private EditDialogListener listener;

    public EditDialog(DocumentReference reference, String oldContent) {
        this.reference = reference;
        this.oldContent = oldContent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_layout, null);
        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newContent = content.getText().toString();
                        if (!newContent.equals(oldContent) || !newContent.isEmpty()) {
                            reference.update("content", newContent);
                            listener.notifyChange();
                        }
                    }
                });
        content= view.findViewById(R.id.edit_content);
        content.setText(oldContent);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener= (EditDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement EditDialogListener");
        }
    }
}
