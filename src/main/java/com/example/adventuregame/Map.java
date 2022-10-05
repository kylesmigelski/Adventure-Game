package com.example.adventuregame;
import java.util.Random;

public class Map {
    Room [][] map = new Room[10][10];
    Random rand = new Random();

    public Room getRoom(int x, int y) {
        return map[x][y];
    }

    //Populate matrix with room objects
    public Map() {
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                int spawnChance = rand.nextInt(2)+1;
                boolean spawn;
                spawn = spawnChance == 1;
                map[row][column] = new Room(row, column, false, spawn);
            }
        }
    }



}
