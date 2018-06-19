package curso.instagram.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import curso.instagram.R;

/**
 * Created by luizf on 26/04/2018.
 */

public class UserAdapter extends ArrayAdapter{
    private ArrayList<ParseUser> parseUsers;
    private Context context;

    public UserAdapter(@NonNull Context c, @NonNull ArrayList<ParseUser> objects) {
        super(c, 0, objects);
        this.context = c;
        this.parseUsers = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE));

            view = layoutInflater.inflate(R.layout.list_users, parent, false);
        }
        TextView textView_user = ((TextView) view.findViewById(R.id.user_name));

        ParseUser user = parseUsers.get(position);
        textView_user.setText(user.getUsername());

        return view;
    }
}
