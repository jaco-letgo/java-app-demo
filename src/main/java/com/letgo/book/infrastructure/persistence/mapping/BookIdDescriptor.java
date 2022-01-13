package com.letgo.book.infrastructure.persistence.mapping;

import com.letgo.book.domain.BookId;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

final public class BookIdDescriptor extends AbstractTypeDescriptor<BookId> {
    public BookIdDescriptor() {
        super(BookId.class, new ImmutableMutabilityPlan<>());
    }

    @Override
    public String toString(BookId value) {
        return value.value();
    }

    @Override
    public BookId fromString(String string) {
        return BookId.create(string);
    }

    @Override
    public <X> X unwrap(BookId value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }

        throw unknownUnwrap(type);
    }

    @Override
    public <X> BookId wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return fromString((String) value);
        }

        throw unknownWrap(value.getClass());
    }
}
