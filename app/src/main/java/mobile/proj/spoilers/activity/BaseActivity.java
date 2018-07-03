package mobile.proj.spoilers.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import mobile.proj.spoilers.InventumContextWrapper;
import mobile.proj.spoilers.R;
import mobile.proj.spoilers.helper.SQLiteHandler;
import mobile.proj.spoilers.helper.SessionManager;
import mobile.proj.spoilers.util.AppUtils;
import mobile.proj.spoilers.util.PrefUtils;

public class BaseActivity extends AppCompatActivity {

    private final static String TAG = BaseActivity.class.getSimpleName();
    private SQLiteHandler db;
    private SessionManager session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(InventumContextWrapper.wrap(base));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (!AppUtils.isNetworkAvailableAndConnected(this)) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.network_error), Snackbar.LENGTH_LONG).show();
        }
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        getToolbar();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    private void setupNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        configureNavView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void configureNavView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(navigationViewListener);
    }

    private NavigationView.OnNavigationItemSelectedListener navigationViewListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_menu_home:
                            startActivity(new Intent(BaseActivity.this, HomeActivity.class));
                            break;
                        case R.id.nav_menu_movies:
                            createBackStack(new Intent(BaseActivity.this, MoviesActivity.class));
                            break;
                        case R.id.nav_menu_tv:
                            createBackStack(new Intent(BaseActivity.this, TvActivity.class));
                            break;
                        case R.id.nav_menu_discover:
                            createBackStack(new Intent(BaseActivity.this, DiscoverActivity.class));
                            break;
                        case R.id.nav_menu_feedback:
                            logoutUser();
                            break;
                        case R.id.nav_menu_settings:
                            createBackStack(new Intent(BaseActivity.this, ProfileActivity.class));
                            break;
                    }

                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            };

    protected Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }

        return mToolbar;
    }

    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDraw();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDraw() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Movies lang: " + PrefUtils.getFormatLocale(this));
    }
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        // Launching the login activity
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
