public final class DeleteTest {

    public void testExample() {
        assert !hasEmptyConstructor(Example.class);
        assert !hasField(Example.class, "ONE");
        assert Example.one() == 1;
    }

    public void testConstructors() {
        assert !hasEmptyConstructor(Constructors.class);
        assert hasEmptyConstructor(Constructors.Default.class);
        assert hasEmptyConstructor(Constructors.Public.class);
        assert hasEmptyConstructor(Constructors.Protected.class);
        assert hasEmptyConstructor(Constructors.Package.class);
        assert !hasEmptyConstructor(Constructors.Private.class);
        assert !hasEmptyConstructor(Constructors.Static.class);
        assert !hasEmptyConstructor(Constructors.NonStatic.class);
        assert hasEmptyConstructor(Constructors.Empty.class);
        assert !hasEmptyConstructor(Constructors.ThrowNull.class);
        assert !hasEmptyConstructor(Constructors.Super.class);
        assert !hasEmptyConstructor(Constructors.VarArgs.class);
        assert !hasEmptyConstructor(Constructors.Args.class);
        assert !hasEmptyConstructor(Constructors.Declared.class);
        assert !hasEmptyConstructor(Constructors.Generic.class);
        assert hasEmptyConstructor(Constructors.SemiColon.class);
    }

    public void testFields() {
        assert hasEmptyConstructor(Fields.class);
        assert hasField(Fields.class, "PUBLIC");
        assert hasField(Fields.class, "PROTECTED");
        assert hasField(Fields.class, "PACKAGE");
        assert !hasField(Fields.class, "PRIVATE");
        assert hasField(Fields.class, "INSTANCE_PUBLIC");
        assert hasField(Fields.class, "INSTANCE_PROTECTED");
        assert hasField(Fields.class, "INSTANCE_PACKAGE");
        assert hasField(Fields.class, "INSTANCE_PRIVATE");
        assert hasField(Fields.class, "NOT_FINAL");
        assert !hasField(Fields.class, "FINAL");
        assert !hasField(Fields.class, "UNBOXED");
        assert hasField(Fields.class, "BOXED");
        assert hasField(Fields.class, "NOT_CONSTANT");
        assert !hasField(Fields.class, "CONSTANT");
        assert hasField(Fields.class, "NOT_CONSTANT_STRING");
        assert !hasField(Fields.class, "CONSTANT_STRING");
        assert !hasField(Fields.class, "NOT_NULL");
        assert hasField(Fields.class, "NULL");
        assert hasField(Fields.class, "OBJECT");
        assert hasField(Fields.class, "CHAR_SEQUENCE");
        assert !hasField(Fields.class, "STRING");
    }

    private static boolean hasEmptyConstructor(Class<?> c) {
        try {
            c.getDeclaredConstructor();
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private static boolean hasField(Class<?> c, String f) {
        try {
            c.getDeclaredField(f);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
