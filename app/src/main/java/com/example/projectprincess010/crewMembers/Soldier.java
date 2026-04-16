package com.example.projectprincess010.crewMembers;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import com.example.projectprincess010.models.AbilityResult;
import com.example.projectprincess010.models.Threat;

public class Soldier extends CrewMember {
    private int protectionShieldUses;
    private boolean lastStandActive;
    private int lastStandTurns;

    public Soldier(String name) {
        super(name, 950);
        this.role = "Soldier";
        this.protectionShieldUses = 0;
        this.lastStandActive = false;
        this.lastStandTurns = 0;
    }

    @Override
    protected void initializeAbilities() {}

    @Override
    protected int getBaseHP() {
        return 950;
    }

    @Override
    public AbilityResult useAbility(String abilityName, CrewMember target, Threat threat) {
        switch(abilityName) {
            case "Protection Shield":
                return useProtectionShield(target, threat);
            case "Iron Wall":
                return useIronWall(threat);
            case "Suppressive Fire":
                return useSuppressiveFire(threat);
            case "Last Stand":
                return useLastStand();
            default:
                return new AbilityResult("Invalid ability", 0, 0, 0, false);
        }
    }

    private AbilityResult useProtectionShield(CrewMember target, Threat threat) {
        if (!isAbilityAvailable("Protection Shield")) {
            return new AbilityResult("Protection Shield is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        int damageAbsorbed = (int)(target.getTotalDamageLastNTurns(2) * 0.1);
        int healing = (int)(target.maxHP * 0.2);
        target.heal(healing);

        protectionShieldUses++;
        int reflectedDamage = damageAbsorbed * Math.max(1, 4 - (protectionShieldUses - 1));
        threat.takeDamage(reflectedDamage);

        String message = name + " uses Protection Shield, healing " + target.getName() + " for " + healing + " HP and reflecting " + reflectedDamage + " damage!";
        return new AbilityResult(message, reflectedDamage, 0, healing, true);
    }

    private AbilityResult useIronWall(Threat threat) {
        if (!isAbilityAvailable("Iron Wall")) {
            return new AbilityResult("Iron Wall is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        int damageThisTurn = getTotalDamageLastNTurns(1);
        if (damageThisTurn >= maxHP * 0.5) {
            conditionalLocks.put("Iron Wall", true);
            return new AbilityResult("Security Breach: Cannot activate Iron Wall!", 0, 0, 0, false);
        }

        buffs.put("damageNullification", 30);
        buffs.put("damageNullificationDuration", 4);
        buffs.put("damageReflection", 25);
        buffs.put("damageReflectionDuration", 4);

        String message = name + " raises Iron Wall, nullifying 30% damage and reflecting 25% for 4 turns!";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    private AbilityResult useSuppressiveFire(Threat threat) {
        if (!isAbilityAvailable("Suppressive Fire")) {
            return new AbilityResult("Suppressive Fire is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        threat.addDamageReductionDebuff(15, 2);

        String message = name + " lays down Suppressive Fire, reducing threat damage by 15% for 2 turns!";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    private AbilityResult useLastStand() {
        if (!isAbilityAvailable("Last Stand")) {
            return new AbilityResult("Last Stand is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        int damageTaken = maxHP - currentHP;
        if (damageTaken < maxHP * 0.5) {
            return new AbilityResult("Stand-alone: Last Stand only available when below 50% HP!", 0, 0, 0, false);
        }

        lastStandActive = true;
        lastStandTurns = 4;
        buffs.put("unkillable", 1);
        buffs.put("unkillableDuration", 4);
        buffs.put("damageReflection", 200);
        buffs.put("damageReflectionDuration", 4);

        String message = name + " enters Last Stand! Cannot be knocked out and reflecting 200% damage for 4 turns!";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    @Override
    protected void updateStatusEffects() {
        super.updateStatusEffects();

        if (lastStandActive) {
            lastStandTurns--;
            if (lastStandTurns <= 0) {
                lastStandActive = false;
                currentHP = (int)(currentHP * 0.7);
            }
        }

        if (!buffs.containsKey("damageNullification") && conditionalLocks.containsKey("Iron Wall")) {
            abilityLockoutTurns.put("Iron Wall", 3);
            conditionalLocks.put("Iron Wall", false);
        }
    }

    @Override
    public void takeDamage(int damage, String source) {
        if (lastStandActive) {
            int newHP = currentHP - damage;
            currentHP = Math.max(1, newHP);
        } else {
            super.takeDamage(damage, source);
        }
    }
}