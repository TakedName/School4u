package fu.prm391.finalproject.school4u.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fu.prm391.finalproject.school4u.R;
import fu.prm391.finalproject.school4u.data.School;
import fu.prm391.finalproject.school4u.services.FirebaseHelper;

public class MyAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<School> list = new ArrayList<School>();
    private Context context;
    public MyAdapter(ArrayList<School> list, Context context) {
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
            view = inflater.inflate(R.layout.fragment_school_layout, null);
        }
        //Handle TextView and display string from your list
        TextView name = (TextView)view.findViewById(R.id.school_name);
        TextView descrip = (TextView)view.findViewById(R.id.school_short_descrip);
        ImageView ava = (ImageView) view.findViewById(R.id.ava);
        name.setText(list.get(position).getName());
        descrip.setText(list.get(position).getShortDescription());
        try {
            FirebaseHelper.setImage(ava,list.get(position).getAvatar());
//            ava.getLayoutParams().height = 500;
//            ava.getLayoutParams().width = 100;
        }catch (Exception e){
            ava.setImageResource(R.drawable.error);

        }
        //Handle buttons and add onClickListenerss
        return view;
    }
}
