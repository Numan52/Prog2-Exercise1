package at.ac.fhcampuswien.fhmdb.exceptions;

public class MovieApiException extends RuntimeException{
    public MovieApiException(){

    }
    public MovieApiException(String message){
        super(message);
    }
}
