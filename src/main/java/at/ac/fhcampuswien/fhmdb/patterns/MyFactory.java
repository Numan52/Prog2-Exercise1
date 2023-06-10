package at.ac.fhcampuswien.fhmdb.patterns;


import javafx.util.Callback;

import java.sql.SQLOutput;

public class MyFactory implements Callback<Class<?>, Object> {
    private Object controllerInstance;
    private static MyFactory instance;


    public static MyFactory getInstance() {
        if(instance == null)
        {
            instance = new MyFactory();
        }
        return instance;
    }
    private MyFactory()
    {

    }

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
