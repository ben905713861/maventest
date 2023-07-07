package com.demo.maventest.cglib;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassWriterTest {

    public static void main2(String[] args) throws Exception {
        // 生成二进制字节码
        byte[] bytes = generate();
        // 使用自定义的ClassLoader
        MyClassLoader cl = new MyClassLoader();
        // 加载我们生成的 HelloWorld 类
        Class<?> clazz = cl.defineClass("com.dadiyang.asm.HelloWorld", bytes);
    }

    private static byte[] generate() {
        ClassWriter cw = new ClassWriter(0);
        // 定义对象头：版本号、修饰符、全类名、签名、父类、实现的接口
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "com/dadiyang/asm/HelloWorld", null, "java/lang/Object", null);
        // 添加方法：修饰符、方法名、描述符、签名、抛出的异常
        // 生成字节数组
        return cw.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        ClassWriter classWriter = new ClassWriter(0);
        // 定义对象头：版本号、修饰符、全类名、签名、父类、实现的接口
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "com/dadiyang/asm/HelloWorld", null, "java/lang/Object",
            null);

        // 构造函数
        MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        /**
         * access- 字段的访问标志（参见Opcodes）。此参数还指示该字段是否是合成的和/或不推荐使用的。 name- 字段的名称。 desc- 字段的描述符（参见Type）。 signature-
         * 字段的签名。null如果字段的类型不使用泛型类型，则可能是这样。 value- 字段的初始值。null如果字段没有初始值，则此参数必须是 an Integer、 a Float、 a 、LongaDouble或 a
         * String（分别为int、float或字段）。此参数仅用于静态字段。对于非静态字段，它的值被忽略，必须通过构造函数或方法中的字节码指令进行初始化。longString
         */
        classWriter.visitField(Opcodes.ACC_PUBLIC, "age", "I", null, 18);

        // 类完成
        classWriter.visitEnd();
        // 生成字节数组
        byte[] bytes = classWriter.toByteArray();

        // 使用自定义的ClassLoader
        MyClassLoader cl = new MyClassLoader();
        // 加载我们生成的 HelloWorld 类
        Class<?> clazz = cl.defineClass("com.dadiyang.asm.HelloWorld", bytes);

        Object object = clazz.getConstructor().newInstance();
        System.out.println(object);
        Object res = clazz.getField("age").get(object);
        System.out.println(res);
    }
}

class MyClassLoader extends ClassLoader {
    public Class<?> defineClass(String name, byte[] b) {
        // ClassLoader是个抽象类，而ClassLoader.defineClass 方法是protected的
        // 所以我们需要定义一个子类将这个方法暴露出来
        return super.defineClass(name, b, 0, b.length);
    }
}
