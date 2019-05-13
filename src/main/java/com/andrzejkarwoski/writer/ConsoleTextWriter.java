package com.andrzejkarwoski.writer;

import com.andrzejkarwoski.formater.TextFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsoleTextWriter {

    private TextFormatter textFormatter;

    @Autowired
    ConsoleTextWriter(TextFormatter textFormatter){
        this.textFormatter=textFormatter;
    }


    public void println(String text){
        String outputText = textFormatter.format(text);
        System.out.println(outputText);
    }
}
