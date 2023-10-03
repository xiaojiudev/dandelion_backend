package com.dandelion.backend.utils;

import com.dandelion.backend.entities.enumType.Gender;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class GenderDeserializer extends JsonDeserializer<Gender> {
    @Override
    public Gender deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String genderValue = node.asText();

        if (genderValue.equalsIgnoreCase(Gender.OTHER.toString())) {
            return Gender.OTHER;
        }

        if (genderValue.equalsIgnoreCase(Gender.MALE.toString())) {
            return Gender.MALE;
        }

        if (genderValue.equalsIgnoreCase(Gender.FEMALE.toString())) {
            return Gender.FEMALE;
        }

        return Gender.OTHER;
    }
}
