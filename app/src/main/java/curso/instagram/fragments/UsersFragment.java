package curso.instagram.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import curso.instagram.R;

public class UsersFragment extends Fragment {

    private ListView list_users;
    private ArrayAdapter<ParseUser> parseUserArrayAdapter;
    private ArrayList<ParseUser> users;
    private ParseQuery<ParseUser> query;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);


        users = new ArrayList<>();
        getUsers();
        list_users = ((ListView) view.findViewById(R.id.list_users));
        parseUserArrayAdapter = new UserAdapter(getActivity(), users);
        list_users.setAdapter(parseUserArrayAdapter);

        return view;
    }

    private void getUsers() {
        query = ParseUser.getQuery();
        // TODO: 26/04/2018 sistema que adiciona apenas usuários selecionados
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByAscending("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size()>0) {
                        for (ParseUser parseUser :
                                objects) {
                            users.add(parseUser);
                        }
                        parseUserArrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
