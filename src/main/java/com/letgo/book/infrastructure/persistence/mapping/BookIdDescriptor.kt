package com.letgo.book.infrastructure.persistence.mapping

import com.letgo.book.domain.BookId
import org.hibernate.type.descriptor.WrapperOptions
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan

class BookIdDescriptor : AbstractTypeDescriptor<BookId>(BookId::class.java, ImmutableMutabilityPlan()) {
    override fun toString(value: BookId): String {
        return value.value()
    }

    override fun fromString(string: String): BookId {
        return BookId.create(string)
    }

    override fun <X> unwrap(value: BookId?, type: Class<X>, options: WrapperOptions): X? {
        if (value == null) {
            return null
        }
        if (String::class.java.isAssignableFrom(type)) {
            return toString(value) as X
        }
        throw unknownUnwrap(type)
    }

    override fun <X> wrap(value: X?, options: WrapperOptions): BookId? {
        if (value == null) {
            return null
        }
        if (value is String) {
            return fromString(value as String)
        }
        throw unknownWrap(value.javaClass)
    }
}
