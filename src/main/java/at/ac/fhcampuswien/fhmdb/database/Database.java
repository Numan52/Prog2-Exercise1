package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Arrays;

public class Database {
    public static final String DB_URL = "jdbc:h2:file: ./db/watchlistmoviesdb";
    public static final String username = "user";
    public static final String password = "pass";
    private static Database instance;
    private static ConnectionSource connectionSource;
    private static Dao<WatchlistMovieEntity, Long> watchlistMovieDao;

    private Database()
    {
        try{
            createConnectionSource();
            watchlistMovieDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTable();
        }catch (SQLException | DatabaseException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static Database getDatabase()
    {
        if(instance == null)
        {
            instance = new Database();
        }
        return instance;
    }
    private static void createTable() throws DatabaseException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException E){
            throw new DatabaseException();
        }

    }

    private static void createConnectionSource() throws DatabaseException {
        try {
            connectionSource = new JdbcConnectionSource(DB_URL,username,password);
        } catch (SQLException E){
            throw new DatabaseException();
        }

    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public Dao<WatchlistMovieEntity, Long> getWatchlistMovieDao() {
        return watchlistMovieDao;
    }

}
