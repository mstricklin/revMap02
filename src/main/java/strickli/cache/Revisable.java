// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.cache;

public interface Revisable<T> {
    T mutableCopy();
    T mergeInto(T target);
}
