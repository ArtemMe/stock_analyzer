package com.artem.mezh.stock_analyzer.service

import com.google.common.io.Files
import org.springframework.stereotype.Service
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.*

@Service
class FileService {
    fun fromClasspath(path: String?): String {

        val searchRoot = FileService::class.java
        val resourceUrl = Optional.ofNullable(searchRoot)
                .map { x -> x.getResource(path) }
                .orElseThrow { IllegalArgumentException(String.format("Resource %s not found", path)) }

        return Files.toString(File(resourceUrl.toURI()), StandardCharsets.UTF_8)
    }
}