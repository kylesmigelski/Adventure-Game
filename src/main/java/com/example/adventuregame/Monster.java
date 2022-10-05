package com.example.adventuregame;

public class Monster {
    private int health;
    private int strength;
    private int dexterity;
    private int intelligence;
    Dice dice = new Dice();

    public Monster() {
        this.health = dice.rollMultiple(2,6);
        this.strength = dice.rollMultiple(2,6);
        this.dexterity = dice.rollMultiple(2,6);
        this.intelligence = dice.rollMultiple(2,6);
    }

    public int getHealth() {
        return health;
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

    public void setHealth(int damage){
        health -= damage;
    }
}
