package fu.prm391.finalproject.school4u.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fu.prm391.finalproject.school4u.R;
import fu.prm391.finalproject.school4u.data.Forum;
import fu.prm391.finalproject.school4u.data.School;
import fu.prm391.finalproject.school4u.services.FirebaseHelper;

public class ForumAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Forum> list = new ArrayList<Forum>();
    private Context context;
    public ForumAdapter(ArrayList<Forum> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_forum, null);
        }
        TextView name = (TextView)view.findViewById(R.id.userName);
        TextView content = (TextView)view.findViewById(R.id.content);
        name.setText(list.get(position).getAuthorName());
        content.setText(list.get(position).getContent());
        return view;
    }
}
