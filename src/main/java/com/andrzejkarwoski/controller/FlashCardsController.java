package com.andrzejkarwoski.controller;

import com.andrzejkarwoski.formater.TextFormatter;
import com.andrzejkarwoski.model.Word;
import com.andrzejkarwoski.repository.WordRepository;
import com.andrzejkarwoski.service.FileService;
import com.andrzejkarwoski.writer.ConsoleTextWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;


@Controller
public class FlashCardsController {
    private static final int UNDEFINED = -1;
    private static final int ADD_WORD = 0;
    private static final int TEST = 1;
    private static final int CLOSE_APP = 2;

    private WordRepository wordRepository;
    private FileService fileService;
    private Scanner scanner;
    private ConsoleTextWriter consoleTextWriter;

    @Autowired
    public FlashCardsController(WordRepository wordRepository, FileService fileService, Scanner scanner, ConsoleTextWriter consoleTextWriter) {
        this.wordRepository = wordRepository;
        this.fileService = fileService;
        this.scanner = scanner;
        this.consoleTextWriter = consoleTextWriter;
    }

    public void mainLoop() {
        consoleTextWriter.println("Flash Cards Application");
        int option = UNDEFINED;
        while(option != CLOSE_APP) {
            printMenu();
            option = chooseOption();
            executeOption(option);
        }
    }


    private void printMenu() {
        consoleTextWriter.println("Wybierz opcję:");
        consoleTextWriter.println("0 - Dodaj fiszkę");
        consoleTextWriter.println("1 - Przetestuj się");
        consoleTextWriter.println("2 - Zamknij program");
    }

    private int chooseOption() {
        int option;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            option = UNDEFINED;
        } finally {
            scanner.nextLine();
        }
        if (option > UNDEFINED && option <= CLOSE_APP)
            return option;
        else
            return UNDEFINED;
    }

    private void close() {
        try {
            fileService.saveWords(wordRepository.getAll());
            consoleTextWriter.println("Zapisano stan aplikacji");
        } catch (IOException e) {
            consoleTextWriter.println("Nie udało się zapisać zmian");
        }
        consoleTextWriter.println("Bye Bye");
    }

    private void addWord() {
        consoleTextWriter.println("Podaj polską frazę");
        String polish = scanner.nextLine();
        consoleTextWriter.println("Podaj frazę po angielsku");
        String english = scanner.nextLine();
        Word word = new Word(polish, english);
        wordRepository.add(word);
    }

    private void test() {
        if(wordRepository.isEmpty()) {
            consoleTextWriter.println("Dodaj przynajmniej jedną fiszkę do bazy.");
            return;
        }
        final int testSize = wordRepository.size() > 10 ? 10 : wordRepository.size();
        Set<Word> randomWords = wordRepository.getRandomWords(testSize);
        int score = 0;
        for (Word word : randomWords) {
            consoleTextWriter.println(String.format("Podaj tłumaczenie dla :\"%s\"", word.getPolish()));
            String translation = scanner.nextLine();
            if(word.getEnglish().equalsIgnoreCase(translation)) {
                consoleTextWriter.println("Odpowiedź poprawna");
                score++;
            } else {
                consoleTextWriter.println(String.format("Twoja odpowiedź: " + translation + " jest niepoprawna -  poprawna - " + word.getEnglish()));
            }
        }
        consoleTextWriter.println(String.format("Twój wynik: " +  score + "/" + testSize));
    }

    private void executeOption(int option) {
        switch (option) {
            case ADD_WORD:
                addWord();
                break;
            case TEST:
                test();
                break;
            case CLOSE_APP:
                close();
                break;
            default:
                consoleTextWriter.println("something goes wrong...");
        }
    }
}
