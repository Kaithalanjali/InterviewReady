package com.conceptcoding.impdesignpattern.prototypepattern;

public class CharacterFactory {
    private Character prototypeCharacter;
    // Constructor to create a prototype character (default character)
    public CharacterFactory() {
        prototypeCharacter =
                new Character("DefaultName", 100, 50, 1); // Default prototype character
    }

    // Create a character by cloning the prototype and changing only the required
    // attributes
    public Character createCharacterWithNewName (String name)
            throws CloneNotSupportedException {
        Character clonedCharacter = prototypeCharacter.clone();
        clonedCharacter = new Character(name, clonedCharacter.getHealth(),
                clonedCharacter.getAttackPower(), clonedCharacter.getLevel());
        return clonedCharacter;
    }

    public Character createCharacterWithNewLevel(int level)
            throws CloneNotSupportedException {
        Character clonedCharacter = prototypeCharacter.clone();
        clonedCharacter = new Character(clonedCharacter.getName(),
                clonedCharacter.getHealth(), clonedCharacter.getAttackPower(), level);
        return clonedCharacter;
    }

    public Character createCharacterWithNewAttackPower(int attackPower)
            throws CloneNotSupportedException {
        Character clonedCharacter = prototypeCharacter.clone();
        clonedCharacter = new Character(clonedCharacter.getName(),
                clonedCharacter.getHealth(), attackPower, clonedCharacter.getLevel());
        return clonedCharacter;
    }
}
