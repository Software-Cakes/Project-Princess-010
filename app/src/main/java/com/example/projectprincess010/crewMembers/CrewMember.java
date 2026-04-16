package com.example.projectprincess010.crewMembers;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import com.example.projectprincess010.models.AbilityResult;
import com.example.projectprincess010.models.Threat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CrewMember {
    protected String name;
    protected String role;
    protected int maxHP;
    protected int currentHP;
    protected int level;
    protected int xp;
    protected int totalXP;
    protected int completedMissions;
    protected boolean isIncapacitated;
    protected boolean isRevived;
    protected Map<String, Integer> abilityCooldowns;
    protected Map<String, Integer> abilityLockoutTurns;
    protected Map<String, Boolean> conditionalLocks;
    protected Map<String, Integer> buffs;
    protected Map<String, Integer> debuffs;
    protected int damageDealtLastTurn;
    protected int damageReceivedLastTurn;
    protected List<Integer> damageHistory;
    protected int turnsInMission;
    protected int medicDamageIncreaseCounter;

    public CrewMember(String name, int baseHP) {
        this.name = name;
        this.maxHP = baseHP;
        this.currentHP = baseHP;
        this.level = 1;
        this.xp = 0;
        this.totalXP = 0;
        this.completedMissions = 0;
        this.isIncapacitated = false;
        this.isRevived = false;
        this.abilityCooldowns = new HashMap<>();
        this.abilityLockoutTurns = new HashMap<>();
        this.conditionalLocks = new HashMap<>();
        this.buffs = new HashMap<>();
        this.debuffs = new HashMap<>();
        this.damageHistory = new ArrayList<>();
        this.damageDealtLastTurn = 0;
        this.damageReceivedLastTurn = 0;
        this.turnsInMission = 0;
        this.medicDamageIncreaseCounter = 0;
        initializeAbilities();
    }

    protected abstract void initializeAbilities();

    public void setName(String name) {
        this.name = name;
    }

    public void resetStatus() {
        this.currentHP = this.maxHP;
        this.isIncapacitated = false;
        this.isRevived = false;
        this.buffs.clear();
        this.debuffs.clear();
        this.abilityCooldowns.clear();
        this.abilityLockoutTurns.clear();
        this.conditionalLocks.clear();
        this.damageHistory.clear();
        this.damageDealtLastTurn = 0;
        this.damageReceivedLastTurn = 0;
        this.turnsInMission = 0;
        this.medicDamageIncreaseCounter = 0;
    }

    public void takeDamage(int damage, String source) {
        int actualDamage = calculateDamageReduction(damage, source);
        this.currentHP = Math.max(0, this.currentHP - actualDamage);
        updateDamageHistory(actualDamage);

        if (this.currentHP <= 0) {
            this.isIncapacitated = true;
        }
    }

    protected int calculateDamageReduction(int damage, String source) {
        int reducedDamage = damage;
        if (buffs.containsKey("dodgeBuff")) {
            reducedDamage = (int)(reducedDamage * 0.85);
        }
        if (buffs.containsKey("damageReduction")) {
            reducedDamage = (int)(reducedDamage * (1 - buffs.get("damageReduction") / 100.0));
        }
        if (role != null && role.equals("Pilot") && source.equals("direct")) {
            reducedDamage = (int)(reducedDamage * 1.15);
        }
        return Math.max(0, reducedDamage);
    }

    public void heal(int amount) {
        if (role != null && role.equals("Medic") && !isRevived) {
            amount = Math.min(amount, maxHP / 2);
        }
        int actualHeal = Math.min(amount, maxHP - currentHP);
        this.currentHP = Math.min(maxHP, currentHP + actualHeal);
    }

    public void updateTurnHistory() {
        turnsInMission++;
        for (String ability : new ArrayList<>(abilityCooldowns.keySet())) {
            int remaining = abilityCooldowns.get(ability) - 1;
            if (remaining <= 0) {
                abilityCooldowns.remove(ability);
            } else {
                abilityCooldowns.put(ability, remaining);
            }
        }
        for (String ability : new ArrayList<>(abilityLockoutTurns.keySet())) {
            int remaining = abilityLockoutTurns.get(ability) - 1;
            if (remaining <= 0) {
                abilityLockoutTurns.remove(ability);
            } else {
                abilityLockoutTurns.put(ability, remaining);
            }
        }
        updateStatusEffects();
        if (role != null && role.equals("Medic")) {
            medicDamageIncreaseCounter++;
        }
    }

    protected void updateStatusEffects() {
        updateMapDurations(buffs);
        updateMapDurations(debuffs);
    }

    private void updateMapDurations(Map<String, Integer> map) {
        if (map == null || map.isEmpty()) return;
        List<String> keysToRemove = new ArrayList<>();
        Map<String, Integer> updates = new HashMap<>();
        for (String key : new ArrayList<>(map.keySet())) {
            if (key.endsWith("Duration")) continue;

            String durationKey = key + "Duration";
            if (map.containsKey(durationKey)) {
                int duration = map.get(durationKey) - 1;
                if (duration <= 0) {
                    keysToRemove.add(key);
                    keysToRemove.add(durationKey);
                } else {
                    updates.put(durationKey, duration);
                }
            }
        }
        for (String key : keysToRemove) {
            map.remove(key);
        }
        map.putAll(updates);
    }

    protected void updateDamageHistory(int damage) {
        damageHistory.add(damage);
        damageReceivedLastTurn = damage;
        if (damageHistory.size() > 10) {
            damageHistory.remove(0);
        }
    }

    public int getTotalDamageLastNTurns(int n) {
        int total = 0;
        int start = Math.max(0, damageHistory.size() - n);
        for (int i = start; i < damageHistory.size(); i++) {
            total += damageHistory.get(i);
        }
        return total;
    }

    public abstract AbilityResult useAbility(String abilityName, CrewMember target, Threat threat);

    public void addXP(int amount) {
        this.xp += amount;
        this.totalXP += amount;
        checkLevelUp();
    }

    public void incrementCompletedMissions() {
        this.completedMissions++;
    }

    protected void checkLevelUp() {
        int requiredXP = calculateRequiredXPForNextLevel();
        while (totalXP >= requiredXP) {
            level++;
            maxHP = calculateNewMaxHP();
            currentHP = maxHP;
            requiredXP = calculateRequiredXPForNextLevel();
        }
    }

    protected int calculateRequiredXPForNextLevel() {
        int cumulativeXP = 0;
        for (int i = 1; i <= level; i++) {
            cumulativeXP += i * 10;
        }
        return cumulativeXP;
    }

    protected int calculateNewMaxHP() {
        return (int)(getBaseHP() * (1 + (level - 1) * 0.2));
    }

    protected abstract int getBaseHP();

    public boolean isAbilityAvailable(String abilityName) {
        return !abilityCooldowns.containsKey(abilityName) &&
                !abilityLockoutTurns.containsKey(abilityName) &&
                !conditionalLocks.getOrDefault(abilityName, false);
    }

    // Getters and setters
    public String getName() { return name; }
    public String getRole() { return role; }
    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }
    public int getLevel() { return level; }
    public int getXP() { return xp; }
    public int getTotalXP() { return totalXP; }
    public int getCompletedMissions() { return completedMissions; }
    public boolean isIncapacitated() { return isIncapacitated; }
    public void setIncapacitated(boolean incapacitated) { isIncapacitated = incapacitated; }
    public boolean isRevived() { return isRevived; }
    public void setRevived(boolean revived) { isRevived = revived; }
    public int getDamageDealtLastTurn() { return damageDealtLastTurn; }
    public void setDamageDealtLastTurn(int damage) { this.damageDealtLastTurn = damage; }
    public int getDamageReceivedLastTurn() { return damageReceivedLastTurn; }
    public Map<String, Integer> getBuffs() { return buffs; }
    public Map<String, Integer> getDebuffs() { return debuffs; }
    public void setLevel(int level) { this.level = level; }
    public void setTotalXP(int totalXP) { this.totalXP = totalXP; }
    public void setCompletedMissions(int completedMissions) { this.completedMissions = completedMissions; }
}
