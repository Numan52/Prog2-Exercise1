package at.ac.fhcampuswien.fhmdb.interfaces;
@FunctionalInterface
public interface ClickEventHandler<T> {
    void onClick(T item);
}
