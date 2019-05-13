package com.andrzejkarwoski.formater;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class OriginalFormatter implements TextFormatter{

    @Override
    public String format(String original) {
        return original;
    }
}
