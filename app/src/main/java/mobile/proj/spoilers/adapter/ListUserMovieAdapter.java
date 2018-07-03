package mobile.proj.spoilers.adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobile.proj.spoilers.R;
import mobile.proj.spoilers.model.ListUserMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jihen on 28/11/2017.
 */

public class ListUserMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    int item_id;
    List<ListUserMovie> listUserMovies;
    Context context ;
    // Provide a suitable constructor (depends on the kind of dataset)
    public ListUserMovieAdapter(RecyclerView newsRecyclerView, List<ListUserMovie> list, Context context) {

        /**
         * add listeners
         */

        listUserMovies =new ArrayList<>();
        listUserMovies =list;
        this.context= context;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) newsRecyclerView.getLayoutManager();



    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_movies, parent, false);
        ListMovieViewHolder uvh = new ListMovieViewHolder(v);
        /**
         * Appending a listener whenever user taps an item
         */
        //v.setOnClickListener(new HomeListingActivity.RecycleViewItemOnClickListener(positionitem));
        return uvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ListUserMovieAdapter.ListMovieViewHolder listViewHolder = (ListUserMovieAdapter.ListMovieViewHolder) holder;
        int i = position +1;
        ListUserMovie itemAtPosition = listUserMovies.get(position);
        listViewHolder.txttitle.setText(itemAtPosition.getListName());
        listViewHolder.txtdescription.setText(itemAtPosition.getListDescription());
        listViewHolder.txtnumber.setText(position+".");
    }

    @Override
    public int getItemCount() {
        if (listUserMovies != null)
            return listUserMovies.size();
        else
            return 0;
    }
    static class ListMovieViewHolder extends RecyclerView.ViewHolder {

        private TextView txttitle, txtdescription, txtnumber;
        //  public ImageView avatar;


        public ListMovieViewHolder(View v) {
            super(v);


            txttitle = (TextView) v.findViewById(R.id.listTitle);
            txtdescription = (TextView) v.findViewById(R.id.listDescription);
            txtnumber = (TextView) v.findViewById(R.id.number);

        }
    }
}
