// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli.cache;

public interface ImmutableItem<T> {
    <S extends T> S mutableCopy();
}
