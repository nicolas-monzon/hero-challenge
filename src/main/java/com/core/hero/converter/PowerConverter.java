package com.core.hero.converter;

import com.core.hero.enums.Power;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PowerConverter implements AttributeConverter<Power, String> {

    @Override
    public String convertToDatabaseColumn(Power category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }

    @Override
    public Power convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Power.values())
                .filter(p -> p.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}