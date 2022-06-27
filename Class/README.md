# Class

## Ways to create a new type/class in java (class, interface, …)
Dynamicaly compile source code of a class and defineClass with bytes of a .class file with ClassLoader ?

## Inner classes: classification, differences, best practice.
- https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html 
- https://docs.oracle.com/javase/tutorial/java/javaOO/localclasses.html 
- https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html 

## Inner classes and Memory leak
Each non-static inner class (anonymous) have implicit reference to parent, it means that outter class will never collected by GarbageCollector which leads to memory leak;
It doesn't happens with static inner class. 

## How anonymous classes stored and loaded by JVM?
if we create 2 interfaces A and B
```java
interface A{
}

interface B{
}
```
And in main method create its anonymous classes
```java
public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        A a = new A() {};
        B b = new B() {};
    }
```
JVM will create .class files for both A and B interfaces and also Main$1.class and Main$2.class for anonymous classes
1. A.class
2. B.class
3. Main$1.class
4. Main$2.class
5. Main.class
