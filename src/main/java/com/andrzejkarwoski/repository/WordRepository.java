package com.andrzejkarwoski.repository;

import com.andrzejkarwoski.model.Word;
import com.andrzejkarwoski.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;


@Repository
public class WordRepository {
    private List<Word> words;

    @Autowired
    public WordRepository(FileService fileService){
        try{
            words = fileService.readAllFile();
        } catch (IOException e) {
            words = new ArrayList<>();
        }
    }

    public Set<Word> getRandomWords(int howMuchWords){
        Set<Word> randomWords = new HashSet<>();
        Random random = new Random();
        while (randomWords.size() < howMuchWords && randomWords.size() < words.size()) {
            randomWords.add(words.get(random.nextInt(words.size())));
        }
        return randomWords;
    }

    public List<Word> getAll() {
        return words;
    }

    public int size() {
        return words.size();
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    public void add(Word word) {
        words.add(word);
    }


}
