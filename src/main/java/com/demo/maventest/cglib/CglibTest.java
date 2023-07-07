package com.demo.maventest.cglib;

import lombok.Data;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibTest {
    public static void main(String[] args) {
        // 创建Enhancer类,用作实现Student类的代理
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserMapper.class);
        enhancer.setCallback(new CglibProxy());

        // 创建Enhancer代理,来代理Student类
        UserMapper userMapper = (UserMapper)enhancer.create();
        User user = userMapper.getOne(10);
        System.out.println(user);
    }
}

interface UserMapper {

    User getOne(int userId);

}

@Data
class User {
    private String name;
    private Integer age;
}

class CglibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
        System.out.println("【增强方法】代理对象正在执行的方法：" + method.getName());
        User user = new User();
        user.setAge(10);
        user.setName("ben");
        return user;
    }
}
