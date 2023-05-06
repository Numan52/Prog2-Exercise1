package at.ac.fhcampuswien.fhmdb.database;

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

    public void removeFromWatchlist(WatchlistMovieEntity movie) throws SQLException {
        watchlistMovieDao.delete(movie);
    }
    public List<WatchlistMovieEntity> getAll() throws SQLException {
        return watchlistMovieDao.queryForAll();
    }

    public void addToWatchlist(WatchlistMovieEntity movie) throws SQLException {
        watchlistMovieDao.create(movie);
    }

}
