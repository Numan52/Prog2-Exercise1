package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.observerpattern.Observable;
import at.ac.fhcampuswien.fhmdb.observerpattern.Observer;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository implements Observable {
    private List<Observer> observers;
    private static WatchlistRepository instance;
    public Dao<WatchlistMovieEntity, Long> watchlistMovieDao;
    private WatchlistRepository() {
        watchlistMovieDao = Database.getDatabase().getWatchlistMovieDao();
        observers = new ArrayList<>();
    }

    public static WatchlistRepository getInstance() {
        if (instance == null) {
            instance = new WatchlistRepository();
        }
        return instance;
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
                notifyObservers("Added '" + movie.getTitle() + "' to Watchlist!");
                return;
            }
            notifyObservers("'" + movie.getTitle() + "' is already in Watchlist!");
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer: observers)
        {
            observer.update(message);
        }
    }
}
