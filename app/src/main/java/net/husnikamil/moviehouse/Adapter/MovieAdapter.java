package net.husnikamil.moviehouse.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.husnikamil.moviehouse.DetailActivity;
import net.husnikamil.moviehouse.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolders>{

    ArrayList<Movie> dataFilm;
    OnItemClick handler;


    public void setDatafile(ArrayList<Movie> films) {
        this.dataFilm= films;
        notifyDataSetChanged();
    }

    public void setHandler(OnItemClick clickHandler){
        handler=clickHandler;
    }

    @NonNull
    @Override
    public MovieHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_roll, viewGroup,false);
        MovieHolders holders = new MovieHolders(v);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieHolders holder, int i) {
        Movie movie = dataFilm.get(i);
        //Poster belum kita utak-atik dulu...
        holder.textJudul.setText(movie.title);
        holder.textRate.setText(String.valueOf(movie.vote_average));

        String poster_url = "http://image.tmdb.org/t/p/w342" + movie.poster_path;

        Glide.with(holder.itemView)
                .load(poster_url)
                .into(holder.imagePoster);
    }

    @Override
    public int getItemCount() {
        if (dataFilm != null){
            return dataFilm.size();
        }

        return 0;
    }

    public interface OnItemClick {

        void click (Movie m);
    }


    class MovieHolders extends RecyclerView.ViewHolder{

        ImageView imagePoster;
        TextView textJudul, textRate;

        public MovieHolders(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.moviePoster);
            textJudul = itemView.findViewById(R.id.textJudul);
            textRate = itemView.findViewById(R.id.textRate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Movie m = dataFilm.get(position);
                    handler.click(m);
                }
            });
        }


    }
}
