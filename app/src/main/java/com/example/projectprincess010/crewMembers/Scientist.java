package com.example.projectprincess010.crewMembers;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import com.example.projectprincess010.models.AbilityResult;
import com.example.projectprincess010.models.Threat;

public class Scientist extends CrewMember {
    private int chainReactionStacks;

    public Scientist(String name) {
        super(name, 850);
        this.role = "Scientist";
        this.chainReactionStacks = 0;
    }

    @Override
    protected void initializeAbilities() {}

    @Override
    protected int getBaseHP() {
        return 850;
    }

    @Override
    public AbilityResult useAbility(String abilityName, CrewMember target, Threat threat) {
        switch(abilityName) {
            case "Exploited Vulnerability":
                return useExploitedVulnerability(target, threat);
            case "Chemical Catalyst":
                return useChemicalCatalyst();
            case "Plasma Burst":
                return usePlasmaBurst(target, threat);
            case "Chain Reaction":
                return useChainReaction(threat);
            default:
                return new AbilityResult("Invalid ability", 0, 0, 0, false);
        }
    }

    private AbilityResult useExploitedVulnerability(CrewMember target, Threat threat) {
        if (!isAbilityAvailable("Exploited Vulnerability")) {
            return new AbilityResult("Exploited Vulnerability is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        if (target.isIncapacitated()) {
            return new AbilityResult("Weak Linkage: Cannot use while partner is incapacitated!", 0, 0, 0, false);
        }

        int bonusDamage = target.getDamageDealtLastTurn() * 5;
        threat.takeDamage(bonusDamage);

        abilityLockoutTurns.put("Exploited Vulnerability", 3);

        String message = name + " exploits vulnerability, dealing " + bonusDamage + " bonus damage!";
        return new AbilityResult(message, bonusDamage, 0, 0, true);
    }

    private AbilityResult useChemicalCatalyst() {
        if (!isAbilityAvailable("Chemical Catalyst")) {
            return new AbilityResult("Chemical Catalyst is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        int damageReceived = getTotalDamageLastNTurns(2);
        int healing = (int)(damageReceived * 0.15);
        heal(healing);

        buffs.put("damageNullification", 15);
        buffs.put("damageNullificationDuration", 2);

        abilityLockoutTurns.put("Chemical Catalyst", 2);

        String message = name + " applies Chemical Catalyst, healing " + healing + " HP and nullifying 15% damage for 2 turns!";
        return new AbilityResult(message, 0, healing, 0, true);
    }

    private AbilityResult usePlasmaBurst(CrewMember target, Threat threat) {
        if (!isAbilityAvailable("Plasma Burst")) {
            return new AbilityResult("Plasma Burst is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        if (target.isIncapacitated()) {
            return new AbilityResult("Weak Linkage: Cannot use while partner is incapacitated!", 0, 0, 0, false);
        }

        if (target.getDamageReceivedLastTurn() == 0 && target.getDamageDealtLastTurn() == 0) {
            debuffs.put("damageIncrease", 15);
            debuffs.put("damageIncreaseDuration", 1);
            return new AbilityResult("Miscalculated! Plasma Burst fails. Taking 15% more damage this turn.", 0, 0, 0, false);
        }

        int bonusDamage = (int)(target.getDamageReceivedLastTurn() * 0.2);
        threat.takeDamage(bonusDamage);

        String message = name + " unleashes Plasma Burst, dealing " + bonusDamage + " bonus damage!";
        return new AbilityResult(message, bonusDamage, 0, 0, true);
    }

    private AbilityResult useChainReaction(Threat threat) {
        if (!isAbilityAvailable("Chain Reaction")) {
            return new AbilityResult("Chain Reaction is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        if (chainReactionStacks < 4) {
            chainReactionStacks++;
        }

        threat.addDamageReductionDebuff(10 * chainReactionStacks, 1);

        String message = name + " initiates Chain Reaction, reducing threat damage by " + (10 * chainReactionStacks) + "%!";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    @Override
    protected int calculateDamageReduction(int damage, String source) {
        int reducedDamage = super.calculateDamageReduction(damage, source);

        if (buffs.containsKey("damageNullification")) {
            reducedDamage = (int)(reducedDamage * (1 - buffs.get("damageNullification") / 100.0));
        }

        if (debuffs.containsKey("damageIncrease")) {
            reducedDamage = (int)(reducedDamage * (1 + debuffs.get("damageIncrease") / 100.0));
        }

        return reducedDamage;
    }
}