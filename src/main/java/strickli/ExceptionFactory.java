// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli;

public class ExceptionFactory {
    public static UnsupportedOperationException immutableInstanceException() {
        return new UnsupportedOperationException("Immutable instance");
    }
}
