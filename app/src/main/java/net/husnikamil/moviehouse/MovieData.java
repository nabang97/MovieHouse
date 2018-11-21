package net.husnikamil.moviehouse;

import net.husnikamil.moviehouse.Adapter.Movie;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieData {

    @SerializedName("results")
    @Expose

    public List<Movie> results = null;

}
