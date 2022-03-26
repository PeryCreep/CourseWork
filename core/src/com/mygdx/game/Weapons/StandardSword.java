package com.mygdx.game.Weapons;

public class StandardSword extends Sword{
    private int damage, range;

    public StandardSword() {
        damage = 10;
        range = 20;
    }

    @Override
    public int getDamage() {
        return damage;
    }
}
