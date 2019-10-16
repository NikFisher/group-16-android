package se.chalmers.cse.dit341.group00.model;

public class Player {
    public String _id;
    public int defense;
    public int health;
    public int damage;
    public String name;
    public int currency;
    public boolean dead;
    public String width;
    public void setHealth(int num) {
        this.health = num;
    }
    public int getHealth(){
        return this.health;
    }

    public Player(){


    }
}
