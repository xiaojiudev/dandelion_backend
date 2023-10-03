package com.dandelion.backend.utils;

import com.dandelion.backend.entities.enumType.RoleBase;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoleDeserializer extends JsonDeserializer<List<RoleBase>> {
    @Override
    public List<RoleBase> deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JacksonException {

        JsonNode node = p.getCodec().readTree(p);
        String roleValue = node.asText();

        List<RoleBase> roles = new ArrayList<>();


        if (roleValue.equalsIgnoreCase(RoleBase.CUSTOMER.toString())) {
            roles.add(RoleBase.CUSTOMER);
        }

        if (roleValue.equalsIgnoreCase(RoleBase.MANAGER.toString())) {
            roles.add(RoleBase.CUSTOMER);
            roles.add(RoleBase.MANAGER);
        }

        if (roleValue.equalsIgnoreCase(RoleBase.ADMIN.toString())) {
            roles.add(RoleBase.CUSTOMER);
            roles.add(RoleBase.MANAGER);
            roles.add(RoleBase.ADMIN);
        }

        if (roles.isEmpty()) {
            roles.add(RoleBase.CUSTOMER);
        }

        return roles;
    }
}
