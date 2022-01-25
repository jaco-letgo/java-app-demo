package com.letgo.book.infrastructure.persistence.mapping

import com.letgo.book.domain.BookTitle
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class BookTitleConverter : AttributeConverter<BookTitle, String> {
    override fun convertToDatabaseColumn(attribute: BookTitle): String {
        return attribute.value()
    }

    override fun convertToEntityAttribute(dbData: String?): BookTitle? {
        return if (dbData == null) null else BookTitle.create(dbData)
    }
}
