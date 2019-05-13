package com.andrzejkarwoski.formater;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class ToUpperCaseFormatter implements TextFormatter{

    @Override
    public String format(String original) {
        return original.toUpperCase();
    }
}
