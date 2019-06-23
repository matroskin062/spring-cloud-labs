package com.restservice.restservice.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;

@Entity
@JsonDeserialize(as = Player.class)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String playerName;

    @Column
    private int Age;

    @Column
    private String fieldPos;

    @Column
    private int goals;

    @Column
    private int cards;

    @ManyToOne
    @JoinColumn(name = "teamid")
    private Team team;

    @JsonIgnore
    @Column(name = "presenceFlag", nullable = false)
    private boolean presenceFlag = true;

    public Player(Team team, String playerName, int age, String fieldPos, int goals, int cards) {
        this.team = team;
        this.playerName = playerName;
        Age = age;
        this.fieldPos = fieldPos;
        this.goals = goals;
        this.cards = cards;
    }

    public Player() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getFieldPos() {
        return fieldPos;
    }

    public void setFieldPos(String fieldPos) {
        this.fieldPos = fieldPos;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getCards() {
        return cards;
    }

    public void setCards(int cards) {
        this.cards = cards;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isPresenceFlag() {
        return presenceFlag;
    }

    public void setPresenceFlag(boolean presenceFlag) {
        this.presenceFlag = presenceFlag;
    }
}
