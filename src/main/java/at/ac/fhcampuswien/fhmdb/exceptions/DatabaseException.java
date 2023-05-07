package at.ac.fhcampuswien.fhmdb.exceptions;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(){

    }
    public DatabaseException(String message){
         super(message);
    }
}
