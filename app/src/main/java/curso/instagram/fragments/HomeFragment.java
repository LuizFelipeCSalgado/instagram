package curso.instagram.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import curso.instagram.R;
import curso.instagram.adapters.HomeAdapter;

public class HomeFragment extends Fragment {

    private ListView listView;
    private ArrayList<ParseObject> post;
    private ArrayAdapter<ParseObject> parseObjectArrayAdapter;
    private ParseQuery<ParseObject> query;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        post = new ArrayList<>();
        listView = ((ListView) view.findViewById(R.id.list_view));
        parseObjectArrayAdapter = new HomeAdapter(getActivity(), post);
        listView.setAdapter(parseObjectArrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Deseja apagar a imagem selecionada?");
                builder.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int ix) {
                                parseObjectArrayAdapter.getItem(i).deleteInBackground(
                                        new DeleteCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null) {
                                                    Toast.makeText(getActivity(), "Deletado com sucesso", Toast.LENGTH_LONG).show();
                                                    refreshPosts();

                                                } else {
                                                    Log.i("Erro ao deletar:", e.getMessage());

                                                }

                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();


                return true;
            }

        });

        getPosts();

        return view;
    }

    private void getPosts() {
        query = ParseQuery.getQuery("Images");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        post.clear();
                        for (ParseObject obj : objects) {
                            post.add(obj);
                        }
                        parseObjectArrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.i("ERROR", e.getMessage());
                }
            }
        });
    }

    public void refreshPosts() {
        getPosts();
    }


}
