package com.example.adventuregame;
import java.util.Random;

public class Player {
    private int health = 20;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int gold = 0;
    Dice dice = new Dice();

    public Player() {
        this.strength = dice.rollMultiple(3,6);
        this.dexterity = dice.rollMultiple(3,6);
        this.intelligence = dice.rollMultiple(3,6);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void damageHealth(int damage){
        health -= damage;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getGold() {
        return gold;
    }

    public int getHealth() {
        return health;
    }

    public void addGold(int goldAdd) {
        this.gold = this.gold + goldAdd;
    }
}
