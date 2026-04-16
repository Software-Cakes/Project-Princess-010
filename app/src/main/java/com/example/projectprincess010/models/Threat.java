package com.example.projectprincess010.models;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Threat {
    private String name;
    private int maxHP;
    private int currentHP;
    private int baseDamage;
    private int level;
    private Map<String, Integer> debuffs;
    private Random random;

    public Threat(String name, int level) {
        this.name = name;
        this.level = level;
        this.random = new Random();
        this.debuffs = new HashMap<>();

        this.maxHP = 500 + (level * 200);
        this.currentHP = this.maxHP;
        this.baseDamage = 100 + (level * 20);
    }

    public int calculateDamage() {
        int damage = baseDamage + random.nextInt(50) - 25;
        damage = Math.max(10, damage);

        if (debuffs.containsKey("damageReduction")) {
            damage = (int)(damage * (1 - debuffs.get("damageReduction") / 100.0));
        }
        return damage;
    }

    public void takeDamage(int damage) {
        this.currentHP = Math.max(0, this.currentHP - damage);
    }

    public void addDamageReductionDebuff(int percent, int duration) {
        debuffs.put("damageReduction", percent);
        debuffs.put("damageReductionDuration", duration);
    }

    public void updateDebuffs() {
        if (debuffs.containsKey("damageReductionDuration")) {
            int duration = debuffs.get("damageReductionDuration") - 1;
            if (duration <= 0) {
                debuffs.remove("damageReduction");
                debuffs.remove("damageReductionDuration");
            } else {
                debuffs.put("damageReductionDuration", duration);
            }
        }
    }

    public boolean isDefeated() {
        return currentHP <= 0;
    }

    public String getName() { return name; }
    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }
    public int getLevel() { return level; }
}