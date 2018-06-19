package curso.instagram.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import curso.instagram.R;
import curso.instagram.adapters.TabsAdapter;
import curso.instagram.fragments.HomeFragment;
import curso.instagram.util.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTooblar;
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab);
        viewPager = ((ViewPager) findViewById(R.id.view_pager));

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabsAdapter);

        tabLayout.setCustomTabView(R.layout.tabs_view, R.id.text_item_tab);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tabLayout.setViewPager(viewPager);

        mTooblar = (Toolbar) findViewById(R.id.main_toolbar);
        mTooblar.setLogo(R.drawable.instagramlogo);
        tabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.grey1));
        setTitle("");
        setSupportActionBar(mTooblar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                break;
            case R.id.photo:
                Intent view = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(view, 1);
                break;
            case R.id.action_exit:
                Intent newActivity = new Intent(this, LoginActivity.class);
                startActivity(newActivity);
                ParseUser.logOut();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String FILE_NAME = "arquivo" + ".png";

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 70, stream);
                byte[] byteArray = stream.toByteArray();

                ParseFile parseFile = new ParseFile(FILE_NAME, byteArray);

                ParseObject parseObject = new ParseObject("Images");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.put("image", parseFile);
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "Imagem adicionada com sucesso!", Toast.LENGTH_SHORT).show();

                            TabsAdapter tabsAdapter = ((TabsAdapter) viewPager.getAdapter());
                            HomeFragment homeFragment = ((HomeFragment) tabsAdapter.getFragment(0));
                            homeFragment.refreshPosts();
                        } else {
                            Toast.makeText(MainActivity.this, "Erro ao salvar imagem no servidor!" +
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}

