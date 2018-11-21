package net.husnikamil.moviehouse;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NowPlayingDao {
    @Query("SELECT * FROM now_playing")
    List<NowPlaying> getAll();

    @Query("SELECT * FROM now_palying WHERE id = :id")
    NowPlaying getById(int id);

    @Insert
    void InsertAll(NowPlaying... nowPlayings);

    @Delete
    void delete(NowPlaying nowPlaying);


}
