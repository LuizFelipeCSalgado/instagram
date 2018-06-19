package curso.instagram.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import curso.instagram.R;

public class HomeAdapter extends ArrayAdapter {

    private  Context context;
    private ArrayList<ParseObject> posts;

    public HomeAdapter(@NonNull Context c, @NonNull ArrayList<ParseObject> objects) {
        super(c,0, objects);
        this.context = c;
        this.posts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE));

            view = layoutInflater.inflate(R.layout.post_list, parent, false);
        }

        if (posts.size()>0) {
            ImageView imageView = ((ImageView) view.findViewById(R.id.image_view_post));

            ParseObject parseObject = posts.get(position);

            Picasso.with(context).load(parseObject.getParseFile("image").getUrl())
                    .fit()
                    .into(imageView);
        }

        return view;
    }
}
