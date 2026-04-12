package com.example.projectprincess010.crewMembers;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import com.example.projectprincess010.models.AbilityResult;
import com.example.projectprincess010.models.Threat;

public class Medic extends CrewMember {
    private int emergencyRevivalUses;
    private int contaminationStacks;

    public Medic(String name) {
        super(name, 700);
        this.role = "Medic";
        this.emergencyRevivalUses = 0;
        this.contaminationStacks = 0;
    }

    @Override
    protected void initializeAbilities() {}

    @Override
    protected int getBaseHP() {
        return 700;
    }

    @Override
    public AbilityResult useAbility(String abilityName, CrewMember target, Threat threat) {
        switch(abilityName) {
            case "Field Triage":
                return useFieldTriage(target);
            case "Antidote Protocol":
                return useAntidoteProtocol(target, threat);
            case "Adrenaline Shot":
                return useAdrenalineShot(target);
            case "Emergency Revival":
                return useEmergencyRevival(target);
            default:
                return new AbilityResult("Invalid ability", 0, 0, 0, false);
        }
    }

    private AbilityResult useFieldTriage(CrewMember target) {
        if (!isAbilityAvailable("Field Triage")) {
            return new AbilityResult("Field Triage is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        int damageReceived = target.getTotalDamageLastNTurns(2);
        int healing = (int)(damageReceived * 0.4);
        target.heal(healing);

        abilityLockoutTurns.put("Field Triage", 3);

        String message = name + " uses Field Triage, healing " + target.getName() + " for " + healing + " HP!";
        return new AbilityResult(message, 0, 0, healing, true);
    }

    private AbilityResult useAntidoteProtocol(CrewMember target, Threat threat) {
        if (!isAbilityAvailable("Antidote Protocol")) {
            return new AbilityResult("Antidote Protocol is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        target.getBuffs().put("damageReduction", 20);
        target.getBuffs().put("damageReductionDuration", 1);

        int reflectedDamage = 40;
        threat.takeDamage(reflectedDamage);

        abilityLockoutTurns.put("Antidote Protocol", 3);

        String message = name + " applies Antidote Protocol, protecting " + target.getName() + " and reflecting " + reflectedDamage + " damage!";
        return new AbilityResult(message, reflectedDamage, 0, 0, true);
    }

    private AbilityResult useAdrenalineShot(CrewMember target) {
        if (!isAbilityAvailable("Adrenaline Shot")) {
            return new AbilityResult("Adrenaline Shot is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        int sacrifice = (int)(currentHP * 0.15);
        currentHP -= sacrifice;

        buffs.put("attackBoost", 40);
        buffs.put("attackBoostDuration", 4);
        target.getBuffs().put("attackBoost", 40);
        target.getBuffs().put("attackBoostDuration", 4);

        abilityLockoutTurns.put("Adrenaline Shot", 3);

        String message = name + " administers Adrenaline Shot, sacrificing " + sacrifice + " HP to boost both crew members' attack by 40% for 4 turns!";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    private AbilityResult useEmergencyRevival(CrewMember target) {
        if (!isAbilityAvailable("Emergency Revival")) {
            return new AbilityResult("Emergency Revival is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        if (!target.isIncapacitated()) {
            return new AbilityResult("Target is not incapacitated!", 0, 0, 0, false);
        }

        emergencyRevivalUses++;
        int healPercent = 100 - (emergencyRevivalUses - 1) * 10;
        int healing = (int)(target.maxHP * (healPercent / 100.0));

        target.currentHP = healing;
        target.isIncapacitated = false;
        target.setRevived(true);

        abilityLockoutTurns.put("Emergency Revival", 3);

        String message = name + " performs Emergency Revival, bringing " + target.getName() + " back with " + healing + " HP!";
        return new AbilityResult(message, 0, 0, healing, true);
    }

    @Override
    public void updateTurnHistory() {
        super.updateTurnHistory();
        contaminationStacks++;
    }

    @Override
    protected int calculateDamageReduction(int damage, String source) {
        int reducedDamage = super.calculateDamageReduction(damage, source);
        reducedDamage = (int)(reducedDamage * (1 + medicDamageIncreaseCounter * 0.05));
        reducedDamage = (int)(reducedDamage * (1 + contaminationStacks * 0.1));
        return reducedDamage;
    }
}