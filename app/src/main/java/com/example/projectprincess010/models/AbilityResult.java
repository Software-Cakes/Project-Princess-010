package com.example.projectprincess010.models;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

public class AbilityResult {
    public String message;
    public int damageToThreat;
    public int healingToSelf;
    public int healingToAlly;
    public boolean success;

    public AbilityResult(String message, int damageToThreat, int healingToSelf, int healingToAlly, boolean success) {
        this.message = message;
        this.damageToThreat = damageToThreat;
        this.healingToSelf = healingToSelf;
        this.healingToAlly = healingToAlly;
        this.success = success;
    }
}