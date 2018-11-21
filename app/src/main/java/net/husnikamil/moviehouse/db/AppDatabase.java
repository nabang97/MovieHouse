package net.husnikamil.moviehouse.db;

import net.husnikamil.moviehouse.NowPlaying;
import net.husnikamil.moviehouse.NowPlayingDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = (NowPlaying.class),version = 1)
public abstract class AppDatabase extends RoomDatabase{

    public abstract NowPlayingDao nowPlayingDao();

}
