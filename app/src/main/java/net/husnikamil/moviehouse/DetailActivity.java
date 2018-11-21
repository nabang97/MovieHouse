package net.husnikamil.moviehouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import net.husnikamil.moviehouse.Adapter.Movie;

public class DetailActivity extends AppCompatActivity {

    ImageView imgPoster;
    TextView textReleaseDate, textJudul, textOverview, textRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPoster = findViewById(R.id.imageView);
        textReleaseDate = findViewById(R.id.textTanggalRelease);
        textJudul = findViewById(R.id.textIsiJudul);
        textOverview = findViewById(R.id.textIsiOverview);
        textRate = findViewById(R.id.textIsiRating);

        Intent intent = getIntent();
        if (intent != null){
            Movie movie = intent.getParcelableExtra("movie_extra_key");
            textJudul.setText(movie.title);
            textReleaseDate.setText(movie.release_date);
            textRate.setText(Double.toString(movie.vote_average));
            textOverview.setText(movie.overview);
            String poster_url = "http://image.tmdb.org/t/p/w154" + movie.poster_path;
            Glide.with(this).load(poster_url).into(imgPoster);
        }

    }
}
