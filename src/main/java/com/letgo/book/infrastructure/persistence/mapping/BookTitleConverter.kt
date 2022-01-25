package com.letgo.book.infrastructure.persistence.mapping;

import com.letgo.book.domain.BookTitle;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
final public class BookTitleConverter implements AttributeConverter<BookTitle, String> {
    @Override
    public String convertToDatabaseColumn(BookTitle attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public BookTitle convertToEntityAttribute(String dbData) {
        return dbData == null ? null : BookTitle.create(dbData);
    }
}
