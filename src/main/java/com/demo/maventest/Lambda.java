package com.demo.maventest;

import lombok.Data;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class Lambda {

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException {
        // es 对应的实体类
        Function<ElasticOrder, String> typeFunction = ElasticOrder::getOrderSn;
        String fieldName = LambdaUtils.getfieldName(ElasticOrder::getOrderSn);
        System.out.println("getfieldName = " + fieldName);
    }

}

@Data
class ElasticOrder {
    String orderSn;
}

class LambdaUtils {
    public static <T, R> String getfieldName(TypeFunction<T, R> typeFunction) throws NoSuchMethodException,
        SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = typeFunction.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        SerializedLambda serializedLambda = (SerializedLambda)method.invoke(typeFunction);
        String getter = serializedLambda.getImplMethodName();
        String fieldName = Introspector.decapitalize(getter.replace("get", ""));
        return fieldName;
    }

    @FunctionalInterface
    public interface TypeFunction<T, R> extends Function<T, R>, Serializable {}
}
