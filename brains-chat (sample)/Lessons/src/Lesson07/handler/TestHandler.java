package Lesson07.handler;

import Lesson07.annotation.AfterSuite;
import Lesson07.annotation.BeforeSuite;
import Lesson07.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class TestHandler {

    public static void start(Class<?> testClass) {
        Method[] declaredMethods = testClass.getDeclaredMethods ( );
        Method beforeMethod = getSuitMethod (BeforeSuite.class, declaredMethods);
        Method afterMethod = getSuitMethod (AfterSuite.class, declaredMethods);

        runTests (declaredMethods, beforeMethod, afterMethod);
    }

    private static Method getSuitMethod(Class<? extends Annotation> suitAnnotationClass, Method[] declaredMethods) {
        Method suitMethod = null;

        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent (suitAnnotationClass)) {
                if (suitMethod != null) {
                    throw new RuntimeException (suitAnnotationClass.getName ( ) + " more than one");
                } else {
                    suitMethod = declaredMethod;
                }
            }
        }
        return suitMethod;
    }

    private static void runTests(Method[] declaredMethods, Method beforeMethod, Method afterMethod) {
        invokeMethod (beforeMethod);
        callTsetMethods (declaredMethods);
        invokeMethod (afterMethod);
    }

    private static void callTsetMethods(Method[] declaredMethods) {
        Arrays.stream (declaredMethods)
                .filter (method -> method.isAnnotationPresent (Test.class))
                .sorted (Comparator.comparingInt (method -> method.getAnnotation (Test.class).priority ( )))
                .forEach (method1 -> invokeMethod (method1));
    }

    private static void invokeMethod(Method method) {
        try {
            method.invoke (null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace ( );
        }
    }
}