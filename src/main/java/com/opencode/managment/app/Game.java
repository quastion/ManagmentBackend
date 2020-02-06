package com.opencode.managment.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Game {
    private String puzzledNumber;
    private int dimension = 4;
    private int attemptsCount;

    public Game() {
    }

    /**
     * Сгенерировать случайное число из dimension количества цифр
     * @return
     */
    public Response generatePuzzledNumber(){
        List digits = new ArrayList();
        for(int i = 0; i < 10; i++) digits.add(i);

        puzzledNumber = "";
        Random random = new Random();
        for(int i = 0; i < dimension; i++){
            int randIndex = random.nextInt(digits.size());
            puzzledNumber += digits.get(randIndex);
            digits.remove(randIndex);
        }

        return new Response("0Б0К", "GAME");
    }

    /**
     * Проверить предположение пользователя относительно загаданного слова
     * и вернуть ответ
     * @param number Число - предположение пользователя
     * @return
     */
    public Response checkPuzzledNumberAndGetResponse(String number){
        if(number.length() != puzzledNumber.length()){
            throw new Error("Несоответствие размерности загаданное слова и полученного ответа!");
        }

        int preciseDigitsCount = 0;
        int noPreciseDigitsCount = 0;
        String gameStatus = "GAME";

        for(int i = 0; i < number.length(); i++){
            if(puzzledNumber.charAt(i) == number.charAt(i)){
                preciseDigitsCount++;
            }
            if(puzzledNumber.indexOf(number.toCharArray()[i])!=-1){
                noPreciseDigitsCount++;
            }
        }
        if(preciseDigitsCount == puzzledNumber.length()) {
            gameStatus = "GAME_OVER";
        }
        attemptsCount++;

        return new Response(preciseDigitsCount+"Б"+noPreciseDigitsCount+"К", gameStatus);
    }

    public int getAttemptsCount() {
        return attemptsCount;
    }
}
