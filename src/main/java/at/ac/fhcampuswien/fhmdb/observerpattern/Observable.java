package at.ac.fhcampuswien.fhmdb.observerpattern;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}
