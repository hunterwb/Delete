# Delete

```java
public final class Example {

    private static final int ONE = 1;

    private Example() {
        throw new UnsupportedOperationException();
    }

    public static int one() {
        return ONE;
    }
}
```

```console
$ javac Example.java
$ javap -p -c -constants Example.class
Compiled from "Example.java"
public final class Example {
  private static final int ONE = 1;

  private Example();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: new           #2                  // class java/lang/UnsupportedOperationException
       7: dup
       8: invokespecial #3                  // Method java/lang/UnsupportedOperationException."<init>":()V
      11: athrow

  public static int one();
    Code:
       0: iconst_1
       1: ireturn
}
```
```console
$ javac Example.java -processsorpath delete.jar -Xplugin:Delete
$ javap -p -c -constants Example.class
Compiled from "Example.java"
public final class Example {
  public static int one();
    Code:
       0: iconst_1
       1: ireturn
}
```
