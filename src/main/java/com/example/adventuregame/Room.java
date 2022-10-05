package com.example.adventuregame;

public class Room {
    private int positionX;
    private int positionY;
    private boolean isBlocked;
    public boolean isMonster;
    Monster m;
    private int gold;
    Dice dice = new Dice();

    public Room(int positionX, int positionY, boolean isBlocked, boolean isMonster) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.isBlocked = isBlocked;
        this.isMonster = isMonster;
        spawnMonster();
        this.gold = dice.roll(20);
    }

    public void spawnMonster(){
        m = new Monster();
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public boolean getMonster() {
        return isMonster;
    }

    public void setMonster(boolean isMonster) {
        this.isMonster = isMonster;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
