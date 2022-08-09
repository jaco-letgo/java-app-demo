package com.letgo.book.infrastructure.persistence.mapping

import com.letgo.book.domain.BookId
import org.hibernate.type.AbstractSingleColumnStandardBasicType
import org.hibernate.type.descriptor.sql.CharTypeDescriptor

class BookIdType : AbstractSingleColumnStandardBasicType<BookId>(CharTypeDescriptor(), BookIdDescriptor()) {
    override fun getName(): String = "BookId"
}
