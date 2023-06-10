package at.ac.fhcampuswien.fhmdb.builderpattern;

import at.ac.fhcampuswien.fhmdb.models.Genre;

import java.net.URL;
import java.net.URLEncoder;

public class URLBuilder {
    private String base;
    private String path;
    private String query;
    private Genre genre;
    private String releaseYear;
    private String ratingFrom;
    public URLBuilder(String base)
    {
        this.base = base;
    }
    public URLBuilder setBase(String base) {
        this.base = base;
        return this;
    }

    public URLBuilder setPath(String path) {
        this.path = path;
        return this;
    }
    public URLBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    public URLBuilder setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public URLBuilder setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public URLBuilder setRatingFrom(String ratingFrom) {
        this.ratingFrom = ratingFrom;
        return this;
    }

    public String build()
    {
        StringBuilder newURL = new StringBuilder();
        newURL.append(base);
        if(path != null)
        {
            newURL.append("/").append(path);
        }
        if((query != null && !query.isBlank()) || (genre != null && genre != Genre.ALL_GENRES) || releaseYear != null || ratingFrom != null)
        {
            newURL.append("?");
            if(query != null && !query.trim().equals(""))
            {
                query = URLEncoder.encode(query);
                newURL.append("query=").append(query).append("&");
            }
            if(genre != null && genre != Genre.ALL_GENRES)
            {
                newURL.append("genre=").append(genre).append("&");
            }
            if(releaseYear != null)
            {
                newURL.append("releaseYear=").append(releaseYear).append("&");
            }
            if(ratingFrom != null)
            {
                newURL.append("ratingFrom=").append(ratingFrom).append("&");
            }
        }
        return newURL.toString();
    }
}
