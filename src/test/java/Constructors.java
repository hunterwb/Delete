public class Constructors {

    private Constructors() {
        throw new UnsupportedOperationException();
    }

    public static class Default {}

    public static class Public {
        public Public() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Protected {
        protected Protected() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Package {
        Package() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Private {
        private Private() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Static {
        private Static() {
            throw new UnsupportedOperationException();
        }
    }

    public class NonStatic {
        private NonStatic() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Empty {
        private Empty() {}
    }

    public static class ThrowNull {
        private ThrowNull() {
            throw null;
        }
    }

    public static class Super {
        private Super() {
            super();
            throw new UnsupportedOperationException();
        }
    }

    public static class VarArgs {
        private VarArgs(String... args) {
            throw new UnsupportedOperationException();
        }
    }

    public static class Args {
        private Args(int n) {
            throw new UnsupportedOperationException();
        }
    }

    public static class Declared {
        private Declared() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

    public static class Generic <T> {
        private Generic() {
            throw new UnsupportedOperationException();
        }
    }

    public static class SemiColon {
        private SemiColon() {
            ;
            throw new UnsupportedOperationException();
        }
    }
}
