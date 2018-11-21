package net.husnikamil.moviehouse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.husnikamil.moviehouse.Adapter.Movie;
import net.husnikamil.moviehouse.Adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClick{

    RecyclerView rvMovies;
    ProgressBar pbMovie;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbMovie = findViewById(R.id.progress_movie);
        pbMovie.setVisibility(View.INVISIBLE);

        adapter= new MovieAdapter();
        adapter.setHandler(this);


        rvMovies = findViewById(R.id.movieList);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);
        rvMovies.setVisibility(View.VISIBLE);

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        int mode = preferences.getInt("last_seen_key",1);
        if (mode==1){
            ambilDataNowPlaying();
        }else{
            ambilDataUpComing();
        }

    }
    //menampilkan menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sync_menu:
                SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
                int mode = preferences.getInt("last_seen_key",1);
                if (mode==1){
                    ambilDataNowPlaying();
                }else{
                    ambilDataUpComing();
                }
            break;

            case R.id.menu_clear:
                adapter.setDatafile(null);
                break;

            case R.id.menu_nowPlaying:ambilDataNowPlaying();
            break;

            case  R.id.menu_upComing:ambilDataUpComing();
            break;
        }
//        if (item.getItemId() == R.id.sync_menu){
//           Toast.makeText(this,"Aw...!", Toast.LENGTH_SHORT).show();
//            ambilDataNowPlaying();
//        }
//        if(item.getItemId() == R.id.menu_clear){
//
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void click(Movie m) {
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra("movie_extra_key", m);
        startActivity(detailActivityIntent);
    }


    private void ambilDataNowPlaying() {

//        AmbilDataTrash task = new AmbilDataTrash();
//        task.execute();
//        ArrayList<Movie> files = new ArrayList<>();
//        files.add(new Movie(
//                1,
//                "Nutcracker and The Four Realsm",
//                "",
//                "Clara need to help the Four Realsm",
//                "2018-10-11",
//                6.5
//        ));
//        files.add(new Movie(
//                2,
//                "Nutcracker and The Four Realsm",
//                "",
//                "Clara need to help the Four Realsm",
//                "2018-10-11",
//                6.5
//        ));
//        files.add(new Movie(
//                3,
//                "Nutcracker and The Four Realsm",
//                "",
//                "Clara need to help the Four Realsm",
//                "2018-10-11",
//                6.5
//        ));
//        files.add(new Movie(
//                4,
//                "Nutcracker and The Four Realsm",
//                "",
//                "Clara need to help the Four Realsm",
//                "2018-10-11",
//                6.5
//        ));
//        files.add(new Movie(
//                5,
//                "Nutcracker and The Four Realsm",
//                "",
//                "Clara need to help the Four Realsm",
//                "2018-10-11",
//                6.5
//        ));
//        files.add(new Movie(
//                6,
//                "Nutcracker and The Four Realsm",
//                "",
//                "Clara need to help the Four Realsm",
//                "2018-10-11",
//                6.5
//        ));
//
//        adapter.setDatafile(files);

        pbMovie.setVisibility(View.VISIBLE);
        rvMovies.setVisibility(View.INVISIBLE);

        String TMDB_BASE_URL="https://api.themoviedb.org";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbClient client =  retrofit.create(TmdbClient.class);

        Call<MovieData> call = client.getNowPlaying("2105eeb16c2ac67a002123c95e40a86b");
        call.enqueue(new Callback<MovieData>() {
            //jika proses pemintaan data berhasil
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                MovieData movieData = response.body();
                List<Movie> results = movieData.results;
                adapter.setDatafile(new ArrayList<Movie>(results));
                pbMovie.setVisibility(View.INVISIBLE);
                rvMovies.setVisibility(View.VISIBLE);
            }
            //jika gagal
            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                Toast.makeText(MainActivity.this,"gagal coy",Toast.LENGTH_SHORT).show();
                pbMovie.setVisibility(View.VISIBLE);
                rvMovies.setVisibility(View.INVISIBLE);
            }
        });

        getSupportActionBar().setTitle("Now playing movies");
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("last_seen_key",1);
        editor.commit();

    }

    private void ambilDataUpComing() {

        pbMovie.setVisibility(View.VISIBLE);
        rvMovies.setVisibility(View.INVISIBLE);

        String TMDB_BASE_URL="https://api.themoviedb.org";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbClient client =  retrofit.create(TmdbClient.class);

        Call<MovieData> call = client.getUpComing("2105eeb16c2ac67a002123c95e40a86b");
        call.enqueue(new Callback<MovieData>() {
            //jika proses pemintaan data berhasil
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                MovieData movieData = response.body();
                List<Movie> results = movieData.results;
                adapter.setDatafile(new ArrayList<Movie>(results));
                pbMovie.setVisibility(View.INVISIBLE);
                rvMovies.setVisibility(View.VISIBLE);
            }
            //jika gagal
            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                Toast.makeText(MainActivity.this,"gagal coy",Toast.LENGTH_SHORT).show();
                pbMovie.setVisibility(View.VISIBLE);
                rvMovies.setVisibility(View.INVISIBLE);
            }
        });

        getSupportActionBar().setTitle("Up coming movies");
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("last_seen_key",2);
        editor.commit();

    }
}
