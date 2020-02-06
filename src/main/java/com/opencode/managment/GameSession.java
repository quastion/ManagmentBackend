package com.opencode.managment;

import com.opencode.bullcow.entity.User;

import javax.persistence.*;

@Entity
@Table(name = "gamesession", schema = "public")
public class GameSession {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="login")
    private User user;

    @Column(name = "attemptsCount")
    private int attemptsCount;

    public GameSession() {
    }

    public GameSession(User user, int attemptsCount) {
        this.user = user;
        this.attemptsCount = attemptsCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAttemptsCount() {
        return attemptsCount;
    }

    public void setAttemptsCount(int attemptsCount) {
        this.attemptsCount = attemptsCount;
    }
}
