package mobile.proj.spoilers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import mobile.proj.spoilers.R;
import mobile.proj.spoilers.fragment.HomeMovieFragment;
import mobile.proj.spoilers.fragment.HomeTVFragment;
import mobile.proj.spoilers.helper.SQLiteHandler;
import mobile.proj.spoilers.helper.SessionManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    private SQLiteHandler db;
    private SessionManager session;
    String pseudo;
    String email;

    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.rotate_reel) ImageView rotateReel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        pseudo = user.get("pseudo");
        email = user.get("email");
        Log.d("PSEUDO !  ", pseudo);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentMovie = fm.findFragmentById(R.id.fragment_movie_container);

        if (fragmentMovie == null) {
            fragmentMovie = new HomeMovieFragment();
            fm.beginTransaction().add(R.id.fragment_movie_container, fragmentMovie).commit();
        }

        Fragment fragmentTv = fm.findFragmentById(R.id.fragment_tv_container);
        if (fragmentTv == null)
        {
            fragmentTv = new HomeTVFragment();
            fm.beginTransaction().add(R.id.fragment_tv_container, fragmentTv).commit();
        }
    }

    @OnClick(R.id.home_in_theaters)
    void startMoviesActivity() {
        startActivity(new Intent(this, MoviesActivity.class));
    }

    @OnClick(R.id.home_on_tv)
    void startTvActivity() {
        startActivity(new Intent(this, TvActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
