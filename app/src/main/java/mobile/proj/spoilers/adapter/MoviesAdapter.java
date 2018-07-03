package mobile.proj.spoilers.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mobile.proj.spoilers.R;
import mobile.proj.spoilers.activity.MovieDetailsActivity;
import mobile.proj.spoilers.model.Movie;
import mobile.proj.spoilers.util.Constants;
import mobile.proj.spoilers.util.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder>{

    private List<Movie> mMovies;
    private int where;
    Context context;
    public MoviesAdapter(List<Movie> movies,int a) {
        mMovies = movies;where=a;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_movie, parent, false);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.bindMovie(movie, position);
    }

    @Override
    public int getItemCount() {
        return (mMovies != null) ? mMovies.size() : 0;
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private Movie mMovie;

        @BindView(R.id.movie_position)TextView positionView;
        @BindView(R.id.movie_poster) ImageView posterView;
        @BindView(R.id.movie_name)TextView nameView;
        @BindView(R.id.movie_original_name) TextView originalNameView;
        @BindView(R.id.movie_vote_average) TextView voteAverageView;
        @BindView(R.id.movie_vote_count) TextView voteCountView;
        @BindView(R.id.btnAddToFavorite) ImageView btnAddToFavorite;


        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        void bindMovie(final Movie movie, int position) {
            mMovie = movie;

            positionView.setText(String.valueOf(position + 1));
            Drawable placeholder = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.background_reel, null);
            Picasso.with(mContext)
                    .load(Constants.TMDB_IMAGE_URL + Constants.POSTER_SIZE_W342 + movie.getPosterPath())
                    .placeholder(placeholder)
                    .fit().centerCrop()
                    .noFade()
                    .into(posterView);
            nameView.setText(movie.getTitle());
            originalNameView.setText(mContext.getString(R.string.movie_original_name, movie.getOriginalTitle(), StringUtils.getYear(movie.getReleaseDate())));
            voteAverageView.setText(String.valueOf(movie.getVoteAverage()));
            voteCountView.setText(String.valueOf(movie.getVoteCount()));

            if(where>0){
                btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  AddToFavorite(movie,);
                    }
                });
            }
            else {
                btnAddToFavorite.setVisibility(View.GONE);
            }

        }

        @OnClick(R.id.movie_root)
        void startMovieDetailActivity() {
            Intent intent = MovieDetailsActivity.newIntent(mContext, mMovie.getId());
            mContext.startActivity(intent);
        }
    }



}
