package curso.instagram.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.ViewGroup;

import java.util.HashMap;

import curso.instagram.R;
import curso.instagram.fragments.HomeFragment;
import curso.instagram.fragments.UsersFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {


    private Context context;
    private int[] icons = new int[]{R.drawable.ic_home_black_24dp, R.drawable.ic_people_black_24dp};
    int icon_size;
    private HashMap<Integer, Fragment> fragmentHashMap;

    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.context = c;
        double scale = this.context.getResources().getDisplayMetrics().density;
        this.icon_size = (int) (36*scale);
        Log.i("Values", String.format("TabsAdapter: icon_size: %d", icon_size));
        this.fragmentHashMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                fragmentHashMap.put(position, fragment);
                break;
            case 1:
                fragment = new UsersFragment();
                fragmentHashMap.put(position, fragment);
                break;
        }
        return fragment;
    }

    public Fragment getFragment(Integer index) {
        return fragmentHashMap.get(index);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable drawable = ContextCompat.getDrawable(this.context, icons[position]);
        drawable.setBounds(0,0,icon_size,icon_size);
        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragmentHashMap.remove(position);
    }

    @Override
    public int getCount() {
        return icons.length;
    }
}
