package com.conceptcoding.impdesignpattern.prototypepattern;

import lombok.Getter;

@Getter
public class Character implements Cloneable{
    private String name;
    private int health;
    private int attackPower;
    private int level;

    public Character(String name, int health, int attackPower, int level) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.level = level;
    }

    public Character clone(){
        try {
            return (Character) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }

    public void showCharacterInfo() {
        System.out.println("Name: " + name);
        System.out.println("Health: " + health);
        System.out.println("Attack Power: " + attackPower);
        System.out.println("Level: " + level);
    }

}
