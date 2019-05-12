package com.andrzejkarwoski.controller;

import com.andrzejkarwoski.model.Word;
import com.andrzejkarwoski.repository.WordRepository;
import com.andrzejkarwoski.service.FileService;
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

    private WordRepository wordRepository = new WordRepository();
    private FileService fileService = new FileService();
    private Scanner scanner = new Scanner(System.in);

    @Autowired
    public FlashCardsController(WordRepository wordRepository, FileService fileService, Scanner scanner) {
        this.wordRepository = wordRepository;
        this.fileService = fileService;
        this.scanner = scanner;
    }

    public void mainLoop() {
        System.out.println("Flash Cards Application");
        int option = UNDEFINED;
        while(option != CLOSE_APP) {
            printMenu();
            option = chooseOption();
            executeOption(option);
        }
    }


    private void printMenu() {
        System.out.println("Wybierz opcję:");
        System.out.println("0 - Dodaj fiszkę");
        System.out.println("1 - Przetestuj się");
        System.out.println("2 - Zamknij program");
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
            System.out.println("Zapisano stan aplikacji");
        } catch (IOException e) {
            System.out.println("Nie udało się zapisać zmian");
        }
        System.out.println("Bye Bye");
    }

    private void addWord() {
        System.out.println("Podaj polską frazę");
        String polish = scanner.nextLine();
        System.out.println("Podaj frazę po angielsku");
        String english = scanner.nextLine();
        Word word = new Word(polish, english);
        wordRepository.add(word);
    }

    private void test() {
        if(wordRepository.isEmpty()) {
            System.out.println("Dodaj przynajmniej jedną fiszkę do bazy.");
            return;
        }
        final int testSize = wordRepository.size() > 10 ? 10 : wordRepository.size();
        Set<Word> randomWords = wordRepository.getRandomWords(testSize);
        int score = 0;
        for (Word word : randomWords) {
            System.out.printf("Podaj tłumaczenie dla :\"%s\"\n", word.getPolish());
            String translation = scanner.nextLine();
            if(word.getEnglish().equalsIgnoreCase(translation)) {
                System.out.println("Odpowiedź poprawna");
                score++;
            } else {
                System.out.println("Twoja odpowiedź: " + translation + " jest niepoprawna -  poprawna - " + word.getEnglish());
            }
        }
        System.out.println("Twój wynik: " +  score + "/" + testSize);
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
                System.out.println("something goes wrong...");
        }
    }
}
