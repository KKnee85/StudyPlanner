package com.swe.studyplanner.model

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import kotlinx.datetime.LocalDate

class DateAdapter : TypeAdapter<LocalDate>() {
    override fun write(
        out: JsonWriter,
        value: LocalDate?,
    ) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(value.toString())
        }
    }

    override fun read(reader: JsonReader): LocalDate? =
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            null
        } else {
            LocalDate.parse(reader.nextString())
        }
}
