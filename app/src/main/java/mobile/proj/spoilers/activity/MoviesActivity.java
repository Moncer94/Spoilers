package mobile.proj.spoilers.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import mobile.proj.spoilers.R;
import mobile.proj.spoilers.fragment.MoviesFragment;
import mobile.proj.spoilers.model.Movie;

import java.util.ArrayList;
import java.util.List;

import static mobile.proj.spoilers.activity.LoginActivity.idUser;

public class MoviesActivity extends BaseActivity {
    private static final String TAG = MoviesActivity.class.getSimpleName();
    public static List<Movie> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);
        favorites=new ArrayList<>();
        //favorites=getMyFavoriteMovies();
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new MoviesFragment().newInstance(MoviesFragment.POPULAR), getString(R.string.popular));
        adapter.addFragment(new MoviesFragment().newInstance(MoviesFragment.TOP_RATED), getString(R.string.top_rated));
        adapter.addFragment(new MoviesFragment().newInstance(MoviesFragment.UPCOMING), getString(R.string.upcoming));
        adapter.addFragment(new MoviesFragment().newInstance(MoviesFragment.NOW_PLAYING), getString(R.string.now_playing));

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent search = new Intent(new Intent(MoviesActivity.this, SearchActivity.class));
                startActivity(search);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public  static void AddToFavorite(Movie movie,Context context) {
        if(favorites.contains(movie)){
            favorites.remove(movie);

        }else
            favorites.add(movie);
        //AddToFavorites(movie.getId().toString(),id);
        AddToFavorites(movie.getId().toString(),idUser,movie.getTitle().toString(),movie.getOriginalTitle().toString(),movie.getVoteAverage(),movie.getVoteCount(),context);

    }

    private static void AddToFavorites(final String idMovie, final String idUser,final String name,final String original_name,final float movie_average,final int movie_count, Context context) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://41.226.11.243:10080/mobileAndroid/addFavorites.php?idUser=" + idUser + "&idMovie=" + idMovie +"&name"+ name+ "&original_name"+original_name+"&movie_average"+movie_average+"&movie_count"+movie_count;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        Log.d( "Done" , "Added to favorites!");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
