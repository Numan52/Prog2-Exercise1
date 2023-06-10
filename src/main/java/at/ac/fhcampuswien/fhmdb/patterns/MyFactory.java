package at.ac.fhcampuswien.fhmdb.patterns;


import javafx.util.Callback;

public class MyFactory implements Callback<Class<?>, Object> {
    private Object controllerInstance;

    @Override
    public Object call(Class<?> aClass) {
        if (controllerInstance == null) {
            try {
                controllerInstance = aClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return controllerInstance;
    }
}
