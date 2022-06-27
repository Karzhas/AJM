# Usage of Static and non static Blocks in a Class

 ## Why static and non static blocks were introduced in Java?

**Static**

- Initialize any static fields which are too complicated
- Set up static resources (such as JDBC driver)
- When initialization code could throw an exception, and you want to handle it
- Logging

**Non Static**
- Can be used in anonymous classes


 ## Life cycle of static and non static blocks
**Static**
1. A static block executes at class loading time.
2. All static variables can be accessed freely
3. Any non-static fields can only be accessed through object reference,thus only after object construction.
4. Multiple static blocks would execute in the order they are defined in the class.
5. All static blocks executes only once per classloader

**Non-Static**
1. A non-static block executes when the object is created, before the constructor


 ## Static and non static blocks and inheritance: rules of initialization static and non static blocks between parent and children.

**Order**
1. static initialization blocks of super classes

2. static initialization blocks of the class

3. instance initialization blocks of super classes

4. constructors of super classes

5. instance initialization blocks of the class

6. constructor of the class.

 ## Usage of static blocks and best practice.
 
 Best practice is not to use initialization blocks, there are not so many use cases when it will give boost in developing, moreover it leads to bugs and confusing errors.

- Better to use "telescoping" constructors to avoid  confusion:
```java
public class Test {
    private String something;

    // Default constructor does some things
    public Test() { doStuff(); }

    // Other constructors call the default constructor
    public Test(String s) {
        this(); // Call default constructor
        something = s;
    }
}
```
- If you need to doStuff() at the end of each constructor or other sophisticated initialization, perhaps a builder pattern would be best. 
```java
public class Test {

    private final String something;
    private Test(String s) { something = s; }
    public static Builder builder() { return new Builder(); }

    private static class Builder {
        private String tempSomething;
        public Builder something(String s) {
            tempSomething = s;
            return this;
        }
        public Test build() {
            Test t = new Test(tempSomething);

            doStuff();
            return t;
        }
    }
}
```
```java
Test t = Test.builder()
             .setString("Utini!")
             .build();
```
- Use case for initializer blocks is for building small helper data structures.

```java
private Map<String, String> days = new HashMap<String, String>();
{
    days.put("mon", "monday");
    days.put("tue", "tuesday");
    days.put("wed", "wednesday");
    days.put("thu", "thursday");
    days.put("fri", "friday");
    days.put("sat", "saturday");
    days.put("sun", "sunday");
}
```

