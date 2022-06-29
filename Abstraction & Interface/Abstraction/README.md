## What is an Abstraction?

Выделение наиболее важных характеристик и информации об объекте

https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html

## Maintaining/ Achieving abstraction in java, best practice.

*Abstract*
- You want to share code among several closely related classes.
- You expect that classes that extend your abstract class have many common methods or fields, or require access modifiers other than public (such as protected and private).
- You want to declare non-static or non-final fields. This enables you to define methods that can access and modify the state of the object to which they belong.
*Interface*
- You expect that unrelated classes would implement your interface. For example, the interfaces Comparable and Cloneable are implemented by many unrelated classes.
- You want to specify the behavior of a particular data type, but not concerned about who implements its behavior.
- You want to take advantage of multiple inheritance of type.

An example of an abstract class in the JDK is *AbstractMap*, which is part of the Collections Framework. Its subclasses (which include HashMap, TreeMap, and ConcurrentHashMap) *share many methods* (including get, put, isEmpty, containsKey, and containsValue) that AbstractMap defines.




