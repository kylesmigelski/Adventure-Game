package com.example.adventuregame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Random;

public class GameController {
    Player p = new Player();
    Map map = new Map();
    Room room;
    Random rand = new Random();
    Dice dice = new Dice();
    int locationX;
    int locationY;

    int damage = p.getStrength()/3;

    @FXML
    private Label PlayerHealth;
    @FXML
    private Label PlayerInt;
    @FXML
    private Label PlayerGold;
    @FXML
    private Label PlayerDex;
    @FXML
    private Button StartGame;
    @FXML
    private Button RunAwayButton;
    @FXML
    private Button LeftButton;
    @FXML
    private Button FightButton;
    @FXML
    private Label LocationLabel;
    @FXML
    private Label GoldLabel;
    @FXML
    private Button SleepButton;
    @FXML
    private Button UpBotton;
    @FXML
    private Button SearchButton;
    @FXML
    private Button DownButton;
    @FXML
    private Label MonsterStrength;
    @FXML
    private Label PlayerStrength;
    @FXML
    private Button RightButton;
    @FXML
    private Label MonsterDex;
    @FXML
    private Label MonsterIntelligence;
    @FXML
    private Label MonsterHealth;
    @FXML
    private Label GameMessage;
    @FXML
    private Label MonsterMessage;

    @FXML
    public void startGame(ActionEvent actionEvent) {

        //Display player stats on button press
        PlayerHealth.setText(String.valueOf(p.getHealth()));
        PlayerInt.setText(String.valueOf(p.getIntelligence()));
        PlayerGold.setText(String.valueOf(p.getGold()));
        PlayerDex.setText(String.valueOf(p.getDexterity()));
        PlayerStrength.setText(String.valueOf(p.getStrength()));
        StartGame.setDisable(true);

        //Set starting location to random index
        locationX = rand.nextInt(9);
        locationY = rand.nextInt(9);
        gameLoop();
    }

    private void gameLoop(){
        //Set current room to room stored on map
        room = map.getRoom(locationX, locationY);

        //Display room location and gold amount
        LocationLabel.setText(locationX + " - " + locationY);
        GoldLabel.setText(String.valueOf(room.getGold()));

        if(room.getMonster()){
            GameMessage.setText("A monster approaches!");
            monsterUIVisibility();
        }
        else {
            GameMessage.setText("There is no one in this room");
            defaultUIVisibility();
        }
    }

    private void monsterUIVisibility(){

        //Set UI elements for monster encounter, display monster stats
        FightButton.setDisable(false);
        UpBotton.setDisable(true);
        LeftButton.setDisable(true);
        RightButton.setDisable(true);
        DownButton.setDisable(true);
        RunAwayButton.setDisable(false);
        SleepButton.setDisable(true);
        SearchButton.setDisable(true);
        MonsterHealth.setText(String.valueOf(room.m.getHealth()));
        MonsterIntelligence.setText(String.valueOf(room.m.getIntelligence()));
        MonsterDex.setText(String.valueOf(room.m.getDexterity()));
        MonsterStrength.setText(String.valueOf(room.m.getStrength()));
    }

    private void defaultUIVisibility(){

        //Set UI elements for when there is not a monster
        FightButton.setDisable(true);
        UpBotton.setDisable(false);
        LeftButton.setDisable(false);
        RightButton.setDisable(false);
        DownButton.setDisable(false);
        SleepButton.setDisable(false);
        RunAwayButton.setDisable(true);
        SearchButton.setDisable(false);
        MonsterHealth.setText("");
        MonsterIntelligence.setText("");
        MonsterDex.setText("");
        MonsterStrength.setText("");
    }

    private void updateStatLabels(){
        //Updates monster labels
        if(room.getMonster()) {
            MonsterHealth.setText(String.valueOf(room.m.getHealth()));
            MonsterIntelligence.setText(String.valueOf(room.m.getIntelligence()));
            MonsterDex.setText(String.valueOf(room.m.getDexterity()));
            MonsterStrength.setText(String.valueOf(room.m.getStrength()));
        } else {
            MonsterHealth.setText("");
            MonsterIntelligence.setText("");
            MonsterDex.setText("");
            MonsterStrength.setText("");
            MonsterMessage.setText("");
        }

        //Updates player labels
        PlayerHealth.setText(String.valueOf(p.getHealth()));
        PlayerGold.setText(String.valueOf(p.getGold()));
    }

    @FXML
    public void sleepAction(ActionEvent actionEvent) {
        int roll = dice.roll(6);

        //Create a monster encounter if a 1 is rolled, else regain health
        if(roll==1){
            room.setMonster(true);
            room.m.setHealth(roll);
            monsterUIVisibility();
            updateStatLabels();
            GameMessage.setText("A monster ambushes you in your sleep!");
        } else {
            GameMessage.setText("You slept and regained your health");
            p.setHealth(20);
            PlayerHealth.setText(String.valueOf(p.getHealth()));
        }
    }

    @FXML
    public void runAwayAction(ActionEvent actionEvent) {

        //removes monster from the room, allowing the user to select a new location
        GameMessage.setText("You ran away");
        room.setMonster(false);
        defaultUIVisibility();
        updateStatLabels();
        gameLoop();
    }

    @FXML
    public void searchAction(ActionEvent actionEvent) {
        int roll = dice.roll(20);

        //If roll is less than int, add gold to player count
        if(roll < p.getIntelligence()) {
            p.addGold(room.getGold());
            room.setGold(0);
            updateStatLabels();
            GoldLabel.setText(String.valueOf(room.getGold()));
        } else {
            SearchButton.setDisable(true);
        }
    }

    @FXML
    public void fightAction(ActionEvent actionEvent) {
        int roll = dice.roll(20);
        MonsterMessage.setText("");

        //if roll is >= monster dexterity, do damage to monster
        if(roll >= room.m.getDexterity()){
            room.m.setHealth(damage);
            GameMessage.setText("You did " + damage + " damage");
        } else {
            GameMessage.setText("You missed");
        }

        //If monster is alive, it attacks player
        if(room.m.getHealth() > 0){
            monsterAttack();

            //If the player dies, exit the game
            if(p.getHealth()<0) {
                Platform.exit();
                System.exit(0);
            }

        //Check if monster died
        } else if (room.m.getHealth()<=0){
            MonsterMessage.setText("Monster Died");
            room.setMonster(false);
            gameLoop();
        }
    }

    public void monsterAttack(){
        int monsterDamage = (room.m.getStrength()/3)+1;
        int roll = dice.roll(20);

        if(roll >= p.getDexterity()){
            p.damageHealth(monsterDamage);
            MonsterMessage.setText("Monster did " + monsterDamage + " damage");
        } else {
            MonsterMessage.setText("Monster Missed");
        }
        updateStatLabels();
    }

    @FXML
    public void moveLeft(ActionEvent actionEvent) {

        //Check for valid move
        if((locationX-1)>=0) {
            locationX -= 1;

            //Loop and initialize new room
            gameLoop();
        } else {
            GameMessage.setText("Out of bounds");
        }
    }

    @FXML
    public void moveDown(ActionEvent actionEvent) {
        if((locationY+1)<10) {
            locationY += 1;
            gameLoop();
        } else {
            GameMessage.setText("Out of bounds");
        }
    }

    @FXML
    public void moveUp(ActionEvent actionEvent) {
        if((locationY-1)>=0) {
            locationY -= 1;
            gameLoop();
        } else {
            GameMessage.setText("Out of bounds");
        }
    }

    @FXML
    public void moveRight(ActionEvent actionEvent) {
        if((locationX+1)<10) {
            locationX += 1;
            gameLoop();
        } else {
            GameMessage.setText("Out of bounds");
        }
    }
}

