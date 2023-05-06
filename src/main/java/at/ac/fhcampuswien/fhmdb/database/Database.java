package at.ac.fhcampuswien.fhmdb.database;

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
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void testDB() throws SQLException {
        WatchlistMovieEntity movie = new WatchlistMovieEntity("a","b","c", Arrays.asList(Genre.THRILLER, Genre.ACTION),2015,"url",10,2.2);
        watchlistMovieDao.create(movie);
    }


    public static Database getDatabase()
    {
        if(instance == null)
        {
            instance = new Database();
            System.out.println("created");
        }
        return instance;
    }
private static void createTable() throws SQLException {
    TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
}


    private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL,username,password);
    }
}
