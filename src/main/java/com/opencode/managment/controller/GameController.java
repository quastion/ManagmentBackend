package com.opencode.managment.controller;

import com.opencode.managment.app.Response;
import com.opencode.managment.GameSession;
import com.opencode.managment.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameController {

    private GameService service;

    @Autowired
    public void setService(GameService service){
        this.service = service;
    }

//    @GetMapping("/usersStats")
//    public @ResponseBody HTTPResponse register(@RequestParam(name="login", required=true, defaultValue="login") String login){
//        return service.register(entity);
//    }

    @GetMapping("/usersStats")
    public @ResponseBody List<GameSession> getUsersStats(){
        return service.findAll();
    }

    @GetMapping("/userStats")
    public @ResponseBody List<GameSession> getUserStats(){
        return service.getLastStats(3);
    }

    @GetMapping("/start")
    public @ResponseBody Response startGame(){
        return service.startGame();
    }

    @PostMapping("/step")
    public @ResponseBody Response makeStep(@RequestBody Response response){
        return service.makeStep(response);
    }
}
