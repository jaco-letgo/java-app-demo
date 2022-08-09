package com.letgo.book.infrastructure.configuration

import org.springframework.core.type.classreading.MetadataReader
import org.springframework.core.type.classreading.MetadataReaderFactory
import org.springframework.core.type.filter.TypeFilter

class TestClassesExcluder : TypeFilter {
    override fun match(metadataReader: MetadataReader, metadataReaderFactory: MetadataReaderFactory): Boolean =
        metadataReader.resource.uri.path.contains("/test/")
}
