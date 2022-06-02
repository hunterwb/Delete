public final class Fields {

    public static final int PUBLIC = 0;
    protected static final int PROTECTED = 0;
    static final int PACKAGE = 0;
    private static final int PRIVATE = 0;

    public final int INSTANCE_PUBLIC = 0;
    protected final int INSTANCE_PROTECTED = 0;
    final int INSTANCE_PACKAGE = 0;
    private final int INSTANCE_PRIVATE = 0;

    private static int NOT_FINAL = 0;
    private static final int FINAL = 0;

    private static final int UNBOXED = 0;
    private static final Integer BOXED = 0;

    private static final int NOT_CONSTANT = Integer.hashCode(0);
    private static final int CONSTANT = Integer.SIZE;

    private static final String NOT_CONSTANT_STRING = Integer.toString(0);
    private static final String CONSTANT_STRING = "" + 0;

    private static final String NOT_NULL = "";
    private static final String NULL = null;

    private static final Object OBJECT = "";
    private static final CharSequence CHAR_SEQUENCE = "";
    private static final String STRING = "";
}
