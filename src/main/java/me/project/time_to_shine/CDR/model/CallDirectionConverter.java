package me.project.time_to_shine.CDR.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CallDirectionConverter implements AttributeConverter<CallDirection, String> {


    @Override
    public String convertToDatabaseColumn(CallDirection attribute) {
        return attribute.getCode();
    }

    @Override
    public CallDirection convertToEntityAttribute(String dbData) {
        return CallDirection.codeToString(dbData);
    }
}
