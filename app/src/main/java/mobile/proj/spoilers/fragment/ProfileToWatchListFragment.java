package mobile.proj.spoilers.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobile.proj.spoilers.R;

public class ProfileToWatchListFragment extends Fragment {
    // newInstance constructor for creating fragment with arguments
    public static ProfileToWatchListFragment newInstance(int page, String title) {
        ProfileToWatchListFragment profileToWatchListFragment = new ProfileToWatchListFragment();
        return profileToWatchListFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_to_watch_list, container, false);
        return view;
    }
}
