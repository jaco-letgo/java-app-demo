package com.letgo.book.infrastructure.persistence.mapping;

import com.letgo.book.domain.BookId;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.CharTypeDescriptor;

final public class BookIdType extends AbstractSingleColumnStandardBasicType<BookId> {
    public BookIdType() {
        super(new CharTypeDescriptor(), new BookIdDescriptor());
    }

    @Override
    public String getName() {
        return "BookId";
    }
}
