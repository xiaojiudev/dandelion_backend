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
    public Gender deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String genderValue = node.asText();

        System.out.println(genderValue);
        System.out.println(Gender.MALE);

        if (genderValue.equals(Gender.OTHER.toString())) {
            System.out.println("Equal Other");
            return Gender.OTHER;
        }

        if (genderValue.equals(Gender.MALE.toString())) {
            System.out.println("Equal MALE");
            return Gender.MALE;
        }

        if (genderValue.equals(Gender.FEMALE.toString())) {
            System.out.println("Equal FEMALE");
            return Gender.FEMALE;
        }

        return Gender.OTHER;
    }
}
