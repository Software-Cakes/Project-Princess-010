package com.example.projectprincess010.crewMembers;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import com.example.projectprincess010.models.AbilityResult;
import com.example.projectprincess010.models.Threat;

public class Pilot extends CrewMember {
    private boolean defenseDecreased;

    public Pilot(String name) {
        super(name, 800);
        this.role = "Pilot";
        this.defenseDecreased = false;
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
            case "Evasive Maneuver":
                return useEvasiveManeuver(target);
            case "Slipstream Draft":
                return useSlipstreamDraft();
            case "Navigation Lock":
                return useNavigationLock(target, threat);
            case "Emergency Burn":
                return useEmergencyBurn(target);
            default:
                return new AbilityResult("Invalid ability", 0, 0, 0, false);
        }
    }

    private AbilityResult useEvasiveManeuver(CrewMember target) {
        if (!isAbilityAvailable("Evasive Maneuver")) {
            return new AbilityResult("Evasive Maneuver is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        target.getBuffs().put("dodgeBuff", 15);
        target.getBuffs().put("dodgeBuffDuration", 4);
        defenseDecreased = true;

        String message = name + " uses Evasive Maneuver, granting " + target.getName() + " a dodge buff for 4 turns!";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    private AbilityResult useSlipstreamDraft() {
        if (!isAbilityAvailable("Slipstream Draft")) {
            return new AbilityResult("Slipstream Draft is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        buffs.put("damageReduction", 30);
        buffs.put("damageReductionDuration", 4);
        defenseDecreased = true;

        String message = name + " initiates Slipstream Draft, reducing incoming damage by 30% for 4 turns!";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    private AbilityResult useNavigationLock(CrewMember target, Threat threat) {
        if (!isAbilityAvailable("Navigation Lock")) {
            return new AbilityResult("Navigation Lock is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        int damageThisTurn = getTotalDamageLastNTurns(1);
        if (damageThisTurn >= maxHP * 0.45) {
            conditionalLocks.put("Navigation Lock", true);
            return new AbilityResult("Motion Sickness prevents Navigation Lock!", 0, 0, 0, false);
        }

        threat.addDamageReductionDebuff(20, 2);
        target.getBuffs().put("attackBoost", 25);
        target.getBuffs().put("attackBoostDuration", 2);

        String message = name + " uses Navigation Lock, reducing threat damage and boosting " + target.getName() + "'s attack!";
        return new AbilityResult(message, 0, 0, 0, true);
    }

    private AbilityResult useEmergencyBurn(CrewMember target) {
        if (!isAbilityAvailable("Emergency Burn")) {
            return new AbilityResult("Emergency Burn is currently unavailable. Choose a different ability to neutralize the threat.", 0, 0, 0, false);
        }

        if (target.isIncapacitated()) {
            conditionalLocks.put("Emergency Burn", true);
            return new AbilityResult("Automatic Control Loss: Cannot use Emergency Burn while partner is incapacitated!", 0, 0, 0, false);
        }

        int damageAbsorbed = target.getTotalDamageLastNTurns(4);
        int healing = (int)(damageAbsorbed * 0.5);
        target.heal(healing);

        String message = name + " performs Emergency Burn, healing " + target.getName() + " for " + healing + " HP!";
        return new AbilityResult(message, 0, 0, healing, true);
    }

    @Override
    protected void updateStatusEffects() {
        super.updateStatusEffects();

        if (defenseDecreased) {
            debuffs.put("defenseDown", 10);
            debuffs.put("defenseDownDuration", 1);
            defenseDecreased = false;
        }
    }

    @Override
    protected int calculateDamageReduction(int damage, String source) {
        int reducedDamage = super.calculateDamageReduction(damage, source);

        if (debuffs.containsKey("defenseDown")) {
            reducedDamage = (int)(reducedDamage * 1.1);
        }

        return reducedDamage;
    }
}