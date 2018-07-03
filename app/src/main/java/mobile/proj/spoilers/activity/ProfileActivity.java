package mobile.proj.spoilers.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import mobile.proj.spoilers.R;
import mobile.proj.spoilers.fragment.ProfileMyListsFragment;
import mobile.proj.spoilers.fragment.ProfileToWatchListFragment;
import mobile.proj.spoilers.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static mobile.proj.spoilers.activity.LoginActivity.idUser;
import static mobile.proj.spoilers.app.AppConfig.URL_GETIMG_PREFIX;


public class ProfileActivity extends BaseActivity implements ProfileMyListsFragment.OnFragmentInteractionListener{
    FragmentPagerAdapter profileAdapterViewPager;
    TextView pseudo, nom, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ViewPager vpPager = (ViewPager) findViewById(R.id.ProfilePager);
        profileAdapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(profileAdapterViewPager);
        final SharedPreferences sharedPreferences = ProfileActivity.this.getSharedPreferences("USER", 0);
        pseudo = (TextView) findViewById(R.id.pseudo);
        nom =(TextView) findViewById(R.id.nom);
        email =(TextView) findViewById(R.id.email);
        pseudo.setText(sharedPreferences.getString("pseudo",null));
        email.setText(sharedPreferences.getString("email",null));
        nom.setText(sharedPreferences.getString("firstname",null) + " " + sharedPreferences.getString("lastname",null));
        CircleImageView imgPi = (CircleImageView)findViewById(R.id.profilePic);
        Picasso.with(ProfileActivity.this).load(URL_GETIMG_PREFIX+sharedPreferences.getString("img",null)).into(imgPi);
        ImageView img = (ImageView) findViewById(R.id.edit);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment different title
                    return ProfileMyListsFragment.newInstance(0, "My lists");
                case 1: // Fragment # 1 - This will show SecondFragment
                    return ProfileToWatchListFragment.newInstance(1, "To Watch");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment different title
                    return "My Lists";
                case 1: // Fragment # 1 - This will show SecondFragment
                    return "To Watch";
                default:
                    return null;
            }
        }

    }
    public List<Movie> getMyFavoriteMovies(){
        final List<Movie> myMovieList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        Log.d("User Id " ,idUser);
        String url = "http://41.226.11.243:10022/spoilers/mobileAndroid/selectMyLists.php?idUser=" + idUser;
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("rs", response);

                        JSONArray movies_array = null;
                        Movie movie = null;
                        try {

                            movies_array = new JSONArray(response);

                            for (int i = 0; i < movies_array.length(); i++) {

                                JSONObject obj = movies_array.getJSONObject(i);
                                movie = new Movie();
                                movie.setId(obj.getString("idMovie"));
                                Log.d("movie ", movie.toString());
                                myMovieList.add(movie);

                            }

                            RecyclerView favorites = (RecyclerView) findViewById(R.id.listmovies_recycler_view);
                            //MoviesAdapter = new MoviesAdapter(getApplicationContext(), myMovieList);
                            //   favorites.setAdapter(MoviesAdapter);
                        } catch (JSONException ex) {
                            ex.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

        return myMovieList;
    }
}
