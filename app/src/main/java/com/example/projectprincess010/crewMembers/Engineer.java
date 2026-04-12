package com.example.projectprincess010.crewMembers;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import com.example.projectprincess010.models.AbilityResult;
import com.example.projectprincess010.models.Threat;

public class Engineer extends CrewMember {
    private boolean usedEMPulse;

    public Engineer(String name) {
        super(name, 800);
        this.role = "Engineer";
        this.usedEMPulse = false;
    }

    @Override
    protected void initializeAbilities() {}

    @Override
    protected int getBaseHP() {
        return 800;
    }

    @Override
    public AbilityResult useAbility(String abilityName, CrewMember target, Threat threat) {
        switch(abilityName) {
            case "Overload Circuit":
                return useOverloadCircuit(target);
            case "EMP Pulse":
                return useEMPulse(threat);
            case "Reinforce Armor":
                return useReinforceArmor(target);
            case "System Reroute":
                return useSystemReroute(threat);
            default:
                return new AbilityResult("Invalid ability", 0, 0, 0, false);
        }
    }

    private AbilityResult useOverloadCircuit(CrewMember target) {
        if (!isAbilityAvailable("Overload Circuit")) {
            return new AbilityResult("Overload Circuit is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        int cost = (int)(currentHP * 0.1);
        currentHP -= cost;

        target.getBuffs().put("attackEffectiveness", 200);
        target.getBuffs().put("attackEffectivenessDuration", 2);

        String message = name + " uses Overload Circuit on " + target.getName() + ", increasing their attack effectiveness by 200% for 2 turns! (Cost: " + cost + " HP)";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    private AbilityResult useEMPulse(Threat threat) {
        if (!isAbilityAvailable("EMP Pulse")) {
            return new AbilityResult("EMP Pulse is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        threat.addDamageReductionDebuff(15, 2);
        usedEMPulse = true;

        String message = name + " fires EMP Pulse, stunning the threat and reducing its damage by 15% for 2 turns!";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    private AbilityResult useReinforceArmor(CrewMember target) {
        if (!isAbilityAvailable("Reinforce Armor")) {
            return new AbilityResult("Reinforce Armor is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        buffs.put("barrier", 10);
        buffs.put("barrierDuration", 3);
        target.getBuffs().put("barrier", 10);
        target.getBuffs().put("barrierDuration", 3);

        abilityLockoutTurns.put("Reinforce Armor", 3);

        String message = name + " deploys Reinforce Armor, granting barriers to both crew members!";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    private AbilityResult useSystemReroute(Threat threat) {
        if (!isAbilityAvailable("System Reroute")) {
            return new AbilityResult("System Reroute is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        if (!debuffs.isEmpty()) {
            threat.addDamageReductionDebuff(20, 1);
            debuffs.clear();
            String message = name + " reroutes system, redirecting debuffs and reducing threat damage by 20%!";
            return new AbilityResult(message, 0, 0, 0, true);
        } else {
            conditionalLocks.put("System Reroute", true);
            debuffs.put("damageIncrease", 10);
            debuffs.put("damageIncreaseDuration", 2);
            String message = name + "'s System Reroute fails! Turn lost and taking 10% more damage for 2 turns.";
            return new AbilityResult(message, 0, 0, 0, false);
        }
    }

    @Override
    protected void updateStatusEffects() {
        super.updateStatusEffects();

        if (usedEMPulse) {
            debuffs.put("damageIncrease", 10);
            debuffs.put("damageIncreaseDuration", 1);
            usedEMPulse = false;
        }
    }

    @Override
    protected int calculateDamageReduction(int damage, String source) {
        int reducedDamage = super.calculateDamageReduction(damage, source);

        if (debuffs.containsKey("damageIncrease")) {
            reducedDamage = (int)(reducedDamage * (1 + debuffs.get("damageIncrease") / 100.0));
        }

        if (buffs.containsKey("barrier")) {
            reducedDamage = (int)(reducedDamage * (1 - buffs.get("barrier") / 100.0));
        }

        return reducedDamage;
    }
}