package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@DatabaseTable(tableName = "movie")
public class WatchlistMovieEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String apiId;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField
    private String genres;
    @DatabaseField
    private int releaseYear;

    public String getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public double getRating() {
        return rating;
    }

    public String getApiId() {
        return apiId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @DatabaseField
    private String imgUrl;
    @DatabaseField
    private int lengthInMinutes;
    @DatabaseField
    private double rating;

    public WatchlistMovieEntity(String apiId, String title, String description, List<Genre> genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genresToString(genres);
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }
    public WatchlistMovieEntity()
    {

    }
    private String genresToString(List<Genre> genreList)
    {
        if(genreList != null)
        {
            return genreList.stream()
                    .map(Genre::name)
                    .collect(Collectors.joining(","));
        }
       return null;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof WatchlistMovieEntity)
        {
            WatchlistMovieEntity movie = (WatchlistMovieEntity) obj;
            return Objects.equals(movie.apiId, this.apiId);
        }
        return super.equals(obj);
    }
}
