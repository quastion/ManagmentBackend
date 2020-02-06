package com.opencode.managment.service;

import com.opencode.managment.app.Game;
import com.opencode.managment.app.Response;
import com.opencode.managment.GameSession;
import com.opencode.bullcow.entity.User;
import com.opencode.managment.repository.GameSessionRepository;
import com.opencode.managment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {
    private GameSessionRepository gameSessionRepository;
    private UserRepository userRepository;
    private Game game;

    @Autowired
    public void setProductRepository(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    @Autowired
    public void setRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Сформировать и возвратить игровую статистику по всем пользователям.
     * Результатом является таблица из логинов пользователей и среднего количества попыток у каждого
     * @return
     */
    public List<GameSession> findAll() {
        List<GameSession> gameSessions = gameSessionRepository.findAll();
        Set<String> set = new HashSet<>(gameSessions.stream().map((g) -> g.getUser().getLogin()).collect(Collectors.toList()));
        List<GameSession> resultList = new ArrayList<>();

        for(String login : set){
            int attemptsCount = 0;
            int i = 0;

            for(GameSession gs : gameSessions){
                if(gs.getUser().getLogin().equals(login)){
                    attemptsCount += gs.getAttemptsCount();
                    i++;
                }
            }
            resultList.add(new GameSession(new User(login, ""), attemptsCount/i));
        }

        return resultList;
    }

    /**
     * Сформировать игровую статистику конкретного (авторизованного в данный момент пользователя)
     * @param count Количество последних партий, сыгранных текущим (авторизованным) пользователем
     * @return
     */
    public List<GameSession> getLastStats(int count) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String currentUserName = authentication.getName();

        List<GameSession> gameSessions = gameSessionRepository.findAll();
        gameSessions = gameSessions.stream().filter(gameSession ->
                gameSession.getUser().getLogin().equals(currentUserName)).collect(Collectors.toList());
        int startIndex = gameSessions.size()-count;
        if(startIndex < 0){
            startIndex = 0;
        }
        gameSessions = gameSessions.subList(startIndex, gameSessions.size());
        gameSessions.sort((o1, o2) -> o2.getId() - o1.getId());
        return gameSessions;
    }

    /**
     * Сгенерировать и начать игровую сессию
     * @return
     */
    public Response startGame(){
        game = new Game();
        return game.generatePuzzledNumber();
    }

    /**
     * Проверить очередное предположение пользователя относительно загаданного слова.
     * В случае угадывания - занести игровую сессию в базу данных
     * @param response Контейнер, содержащий предположение пользователя относительно
     *                 загаданного слова
     * @return
     */
    public Response makeStep(Response response){
        if(game == null)
            return null;

        String currentUserName = "unknownUser";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }

        Response gameResponse = game.checkPuzzledNumberAndGetResponse(response.getAnswer());
        if(gameResponse.getStatus() == "GAME_OVER"){
            gameSessionRepository.save(new GameSession(
                    userRepository.findById(currentUserName).get(),
                    game.getAttemptsCount()
            ));
            game = null;
        }

        return gameResponse;
    }
}
