package se.chalmers.cse.dit341.group00.model;

public class Boss {
    public String _id;
    public String description;
    public int health;
    public int damage;
    public String name;
    public String difficulty;
    public void setHealth(int num) {
        this.health = num;
    }

    public Boss(){
    }
}
