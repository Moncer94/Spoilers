package mobile.proj.spoilers.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobile.proj.spoilers.R;
import mobile.proj.spoilers.adapter.MoviesAdapter;
import mobile.proj.spoilers.model.ListUserMovie;

import java.util.ArrayList;
import java.util.List;

import static mobile.proj.spoilers.activity.MoviesActivity.favorites;

public class ProfileMyListsFragment extends Fragment {
    List<ListUserMovie> listUserMovies;

    // recycle view adapters and views
    private RecyclerView listRecyclerView;
    //private ListUserMovieAdapter listUserMovieAdapter;
    private MoviesAdapter mAdapter;

    private RecyclerView.LayoutManager listLayoutManager;
    private ProfileMyListsFragment.OnFragmentInteractionListener mListener;
  /*  private void initListMovies() {
        ListUserMovie l = new ListUserMovie();
        l.setListName("Top 10 fav Movies");
        l.setListDescription("This is my favourite drama/adventure/thriller movies , All have happy endings ");
        listUserMovies.add(l);
        l = new ListUserMovie();
        l.setListName("Top 25 TVSHOWS");
        l.setListDescription("All heroes in these tvshows have died , I really think you ll like these shows thought");
        listUserMovies.add(l);
        l = new ListUserMovie();
        l.setListName("Random Drama movies");
        l.setListDescription("These couples didnt end up together ");
        listUserMovies.add(l);
        l = new ListUserMovie();
        l.setListName("Random Thriller movies");
        l.setListDescription("efiizefhuzhgfuygzfiuyziuefuzegfyuezgofuizeyfugezufyg ");
        listUserMovies.add(l);
        l = new ListUserMovie();
        l.setListName("My Movie list 2017");
        l.setListDescription("zelfjzeihfuezghfyuegzifgzoeufguzefyg ");
        listUserMovies.add(l);
    }*/
    // newInstance constructor for creating fragment with arguments
    public static ProfileMyListsFragment newInstance(int page, String title) {
        ProfileMyListsFragment profileMyListsFragment = new ProfileMyListsFragment();
        return profileMyListsFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_my_lists, container, false);;
        listUserMovies = new ArrayList<>();
        //initListMovies();
        // before running request let me get the actual views
        listRecyclerView = (RecyclerView) view.findViewById(R.id.listmovies_recycler_view);

        listRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        listLayoutManager = new LinearLayoutManager(view.getContext());
        listRecyclerView.setLayoutManager(listLayoutManager);

        // specify an adapter (see also next example)
        //listUserMovieAdapter = new ListUserMovieAdapter(listRecyclerView,listUserMovies,view.getContext());
        mAdapter = new MoviesAdapter(favorites,-1);
        listRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        return view ;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileMyListsFragment.OnFragmentInteractionListener) {
            mListener = (ProfileMyListsFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
