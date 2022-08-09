package com.letgo.book.infrastructure.persistence.mapping

import com.letgo.book.domain.BookId
import org.hibernate.type.descriptor.WrapperOptions
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan

class BookIdDescriptor : AbstractTypeDescriptor<BookId>(BookId::class.java, ImmutableMutabilityPlan()) {
    override fun toString(value: BookId): String = value.value()

    override fun fromString(string: String): BookId = BookId(string)

    override fun <X> unwrap(value: BookId?, type: Class<X>, options: WrapperOptions): X? =
        value?.let {
            if (String::class.java.isAssignableFrom(type)) toString(value) as X
            else throw unknownUnwrap(type)
        }

    override fun <X> wrap(value: X?, options: WrapperOptions): BookId? =
        value?.let {
            when (it) {
                is String -> fromString(value as String)
                else -> throw unknownWrap(value.javaClass)
            }
        }
}
