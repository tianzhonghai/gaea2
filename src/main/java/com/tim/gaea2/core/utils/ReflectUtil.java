package com.tim.gaea2.core.utils;

import com.sun.tools.javac.code.Attribute;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

/**
 * Created by tianzhonghai on 2017/5/18.
 */
public class ReflectUtil {
    /**
     * logger.
     */
    private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 获得超类的参数类型，取第一个参数类型
     *
     * @param <T> 类型参数
     * @param clazz 超类类型
     */
    @SuppressWarnings("rawtypes")
    public static <T> Class<T> getClassGenericType(final Class clazz) {
        return findParameterizedType(clazz, 0);
    }


    /**
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> findInterfaceType(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            return (Class<T>) interfaces[0];
        } else {
            throw new RuntimeException("do not have interfaces");
        }
    }


    /**
     * 得到指定类型的指定位置的泛型实参
     *
     * @param clazz
     * @param index
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> findParameterizedType(Class<?> clazz, int index) {
        Type parameterizedType = clazz.getGenericSuperclass();
        // CGLUB subclass target object(泛型在父类上)
        if (!(parameterizedType instanceof ParameterizedType)) {
            parameterizedType = clazz.getSuperclass().getGenericSuperclass();
        }
        if (!(parameterizedType instanceof ParameterizedType)) {
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) parameterizedType).getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length == 0) {
            return null;
        }
        return (Class<T>) actualTypeArguments[0];
    }



    /**
     * protected constructor.
     */
    protected ReflectUtil() {}

    public static String getGetterMethodName(Object target, String fieldName)
            throws NoSuchMethodException {
        String methodName = "get" + StringUtils.capitalize(fieldName);

        try {
            target.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException ex) {
            logger.trace(ex.getMessage(), ex);
            methodName = "is" + StringUtils.capitalize(fieldName);

            target.getClass().getDeclaredMethod(methodName);
        }

        return methodName;
    }

    public static String getSetterMethodName(String fieldName) {
        return "set" + StringUtils.capitalize(fieldName);
    }


    /**
     * get method value by name.
     *
     * @param target Object
     * @param methodName method name
     * @return object
     * @throws Exception ex
     */
    public static Object getMethodValue(Object target, String methodName)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        Method method = target.getClass().getDeclaredMethod(methodName);

        return method.invoke(target);
    }

    /**
     * set method value by name.
     *
     * @param target Object
     * @param methodName method name
     * @param methodValue method value
     * @throws Exception ex
     */
    public static void setMethodValue(Object target, String methodName,
                                      Object methodValue) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Method method = target.getClass().getDeclaredMethod(methodName,
                methodValue.getClass());

        method.invoke(target, methodValue);
    }

    public static Object getFieldValue(Object target, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        return getFieldValue(target, fieldName, true);
    }

    public static Object getFieldValue(Object target, String fieldName,
                                       boolean isForce) throws NoSuchFieldException,
            IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(isForce);

        return field.get(target);
    }

    /**
     * convert reflection exception to unchecked.
     *
     * @param e Exception
     * @return RuntimeException;
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(
            Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else if (e instanceof IllegalAccessException
                || e instanceof NoSuchMethodException
                || e instanceof NoSuchFieldException) {
            return new IllegalArgumentException("Reflection Exception.", e);
        } else if (e instanceof InvocationTargetException) {
            Throwable targetException = ((InvocationTargetException) e)
                    .getTargetException();

            if (targetException instanceof RuntimeException) {
                return (RuntimeException) targetException;
            } else {
                return new RuntimeException("Reflection Exception.",
                        targetException);
            }
        }

        return new RuntimeException("Unexpected Checked Exception.", e);
    }

}
