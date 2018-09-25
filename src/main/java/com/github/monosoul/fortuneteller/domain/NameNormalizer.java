package com.github.monosoul.fortuneteller.domain;

import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class NameNormalizer implements Function<String, String> {

    @Override
    public String apply(final String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
