package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
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
            if (getAll().contains(movie))
            {
                watchlistMovieDao.delete(movie);
            }
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }

    public List<WatchlistMovieEntity> getAll() throws DatabaseException {
        try {
            return watchlistMovieDao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }

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
    }

}
