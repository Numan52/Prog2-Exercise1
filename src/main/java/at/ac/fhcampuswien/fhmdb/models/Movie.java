package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Movie {
    private String title;
    private String description;
    private List<Genre> genres; //List of Genres of a movie

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }

    public String getGenres() {
        StringJoiner joiner = new StringJoiner(", "); //will add ", " at every new add after the first add
        for (Genre g : genres) {
            joiner.add(g.toString());
        }
        return joiner.toString();

    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        //Add some dummy data here

        MovieAPI.getAllMovies(null,Genre.ADVENTURE,null,null);
        movies.add(new Movie(
                "The Maze Runner",
                "Thomas is deposited in a community of boys after his memory is erased, " +
                        "soon learning they're all trapped in a maze that will require him to join " +
                        "forces with fellow runners for a shot at escape.",
                Arrays.asList(Genre.ACTION,Genre.MYSTERY,Genre.SCIENCE_FICTION)));

        movies.add(new Movie(
                "Avengers: Infinity War",
                "The Avengers and their allies must be willing to sacrifice all in an attempt " +
                        "to defeat the powerful Thanos before his blitz of devastation and ruin puts " +
                        "an end to the universe.",
                Arrays.asList(Genre.ADVENTURE,Genre.ACTION)));

        movies.add(new Movie(
                "Divergent",
                "In a world divided by factions based on virtues, Tris learns she's Divergent " +
                        "and won't fit in. When she discovers a plot to destroy Divergents, Tris and the " +
                        "mysterious Four must find out what makes Divergents dangerous before it's too late.",
                Arrays.asList(Genre.ADVENTURE,Genre.ACTION,Genre.MYSTERY)));

        movies.add(new Movie(
                "Titanic",
                "A seventeen-year-old aristocrat falls in love with a kind but poor " +
                        "artist aboard the luxurious, ill-fated R.M.S. Titanic.",
                Arrays.asList(Genre.DRAMA,Genre.ROMANCE)));
        movies.add(new Movie(
                "Blair Witch",
                "After discovering a video showing what he believes to be his vanished sister " +
                        "Heather, James and a group of friends head to the forest believed to be " +
                        "inhabited by the Blair Witch.",
                Arrays.asList(Genre.HORROR,Genre.MYSTERY,Genre.THRILLER)));
        movies.add(new Movie(
                "Free Guy",
                "A bank teller discovers that he's actually an NPC inside a brutal, open world video game.",
                Arrays.asList(Genre.ADVENTURE,Genre.COMEDY,Genre.FAMILY)));
        movies.add(new Movie(
                "The Super Mario Bros. Movie",
                "The story of The Super Mario Bros. on their journey through the Mushroom Kingdom",
                Arrays.asList(Genre.ADVENTURE,Genre.ANIMATION,Genre.COMEDY)));

        return movies;
    }
}
