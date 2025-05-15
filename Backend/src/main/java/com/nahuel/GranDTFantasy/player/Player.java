package com.nahuel.GranDTFantasy.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @Column(name = "Player") // Coincide con la columna "Player"
    private String name;

    @Column(name = "Nation") // Coincide con la columna "Nation"
    private String nation;

    @Column(name = "Pos") // Coincide con la columna "Pos"
    private String position;

    @Column(name = "Age") // Coincide con la columna "Age"
    private Integer age;

    @Column(name = "MP") // Coincide con la columna "MP"
    private Integer mp;

    @Column(name = "Starts") // Coincide con la columna "Starts"
    private Integer starts;

    @Column(name = "Min") // Coincide con la columna "Min"
    private Double min;

    @Column(name = "Gls") // Coincide con la columna "Gls"
    private Double goals;

    @Column(name = "Ast") // Coincide con la columna "Ast"
    private Double assists;

    @Column(name = "PK") // Coincide con la columna "PK"
    private Double penalties;

    @Column(name = "CrdY") // Coincide con la columna "CrdY"
    private Double yellow_cards;

    @Column(name = "CrdR") // Coincide con la columna "CrdR"
    private Double red_cards;

    @Column(name = "xG") // Coincide con la columna "xG"
    private Double expected_goals;

    @Column(name = "xAG") // Coincide con la columna "xAG"
    private Double expected_assists;

    @Column(name = "Team") // Coincide con la columna "Team"
    private String teamName;

    public Player() {
    }

    public Player(String name, String nation, String position, Integer age, Integer mp, Integer starts, Double min, Double goals, Double assists, Double penalties, Double yellow_cards, Double red_cards, Double expected_goals, Double expected_assists, String teamName) {
        this.name = name;
        this.nation = nation;
        this.position = position;
        this.age = age;
        this.mp = mp;
        this.starts = starts;
        this.min = min;
        this.goals = goals;
        this.assists = assists;
        this.penalties = penalties;
        this.yellow_cards = yellow_cards;
        this.red_cards = red_cards;
        this.expected_goals = expected_goals;
        this.expected_assists = expected_assists;
        this.teamName = teamName;
    }

    // Getters y setters (sin cambios en los nombres de los métodos)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getMp() {
        return mp;
    }

    public void setMp(Integer mp) {
        this.mp = mp;
    }

    public Integer getStarts() {
        return starts;
    }

    public void setStarts(Integer starts) {
        this.starts = starts;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getGoals() {
        return goals;
    }

    public void setGoals(Double goals) {
        this.goals = goals;
    }

    public Double getAssists() {
        return assists;
    }

    public void setAssists(Double assists) {
        this.assists = assists;
    }

    public Double getPenalties() {
        return penalties;
    }

    public void setPenalties(Double penalties) {
        this.penalties = penalties;
    }

    public Double getYellow_cards() {
        return yellow_cards;
    }

    public void setYellow_cards(Double yellow_cards) {
        this.yellow_cards = yellow_cards;
    }

    public Double getRed_cards() {
        return red_cards;
    }

    public void setRed_cards(Double red_cards) {
        this.red_cards = red_cards;
    }

    public Double getExpected_goals() {
        return expected_goals;
    }

    public void setExpected_goals(Double expected_goals) {
        this.expected_goals = expected_goals;
    }

    public Double getExpected_assists() {
        return expected_assists;
    }

    public void setExpected_assists(Double expected_assists) {
        this.expected_assists = expected_assists;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
