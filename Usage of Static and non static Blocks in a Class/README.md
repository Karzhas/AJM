# Usage of Static and non static Blocks in a Class

- #### Why static and non static blocks were introduced in Java?

**Static**

- Initialize any static fields which are too complicated
- Set up static resources (such as JDBC driver)
- When initialization code could throw an exception, and you want to handle it
- Logging

**Non Static**
- Can be used in anonymous classes


- #### Life cycle of static and non static blocks
**Static**
1. A static block executes at class loading time.
2. All static variables can be accessed freely
3. Any non-static fields can only be accessed through object reference,thus only after object construction.
4. Multiple static blocks would execute in the order they are defined in the class.
5. All static blocks executes only once per classloader

**Non-Static**
1. A non-static block executes when the object is created, before the constructor


- #### Static and non static blocks and inheritance: rules of initialization static and non static blocks between parent and children.

**Order**
1. static initialization blocks of super classes

2. static initialization blocks of the class

3. instance initialization blocks of super classes

4. constructors of super classes

5. instance initialization blocks of the class

6. constructor of the class.

