package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import com.j256.ormlite.dao.Dao;
import javafx.scene.chart.PieChart;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    public Dao<WatchlistMovieEntity, Long> watchlistMovieDao;
    public WatchlistRepository()
    {
        watchlistMovieDao = Database.getDatabase().getWatchlistMovieDao();
    }

    public void removeFromWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
        try {
            watchlistMovieDao.delete(movie);
        } catch (SQLException e) {
            throw new DatabaseException();
        }
        //watchlistMovieDao.delete(movie);
    }
    public List<WatchlistMovieEntity> getAll() throws DatabaseException {
        try {
            return watchlistMovieDao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }
        //return watchlistMovieDao.queryForAll();


    public void addToWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
        try {
            if (!getAll().contains(movie))
            {
                watchlistMovieDao.create(movie);
                return;
            }
            System.out.println("Already in Watchlist");
        } catch (SQLException e) {
            throw new DatabaseException();
        }

        //watchlistMovieDao.create(movie);
    }

}
