package com.example.adventuregame;
import java.util.Random;

public class Dice {
    private int value;

    public void setValue(int diceValue) {
        value = diceValue;
    }

    public int getValue() {
        return value;
    }

    public int roll(int sides) {
        Random rand = new Random();
        return value = rand.nextInt(sides) + 1;
    }

    public int rollMultiple(int times, int sides){
        int value = 0;
        for(int i=0; i<times; i++){
            value+=roll(sides);
        }
        return value;
    }

}
