package com.andrzejkarwoski.service;

import com.andrzejkarwoski.model.Word;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FileService {

    private String fileName;

    public FileService(@Value("${filename}") String fileName) {
        this.fileName = fileName;
    }

    public List<Word> readAllFile() throws IOException{
        return Files.readAllLines(Paths.get(fileName))
                .stream()
                .map(CSVWordConverter::parse)
                .collect(Collectors.toList());
    }

    public void saveWords(List<Word> words) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Word word : words) {
            writer.write(word.toString());
            writer.newLine();
        }
        writer.close();
    }

    private static class CSVWordConverter{
        static Word parse(String csv){
            String[] split = csv.split(";");
            return new Word(split[0],split[1]);
        }
    }
}
