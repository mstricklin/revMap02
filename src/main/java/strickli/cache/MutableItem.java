// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.cache;


public interface MutableItem<T> {
    T mergeInto(T t);
    ImmutableItem<T> merge(T t);
}
