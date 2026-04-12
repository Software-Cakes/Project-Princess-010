package com.example.projectprincess010.battleSystem;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import com.example.projectprincess010.crewMembers.CrewMember;
import com.example.projectprincess010.missions.Mission;
import com.example.projectprincess010.models.AbilityResult;
import com.example.projectprincess010.models.Threat;
import java.util.ArrayList;
import java.util.List;

public class BattleSystem {
    private CrewMember characterA;
    private CrewMember characterB;
    private Mission mission;
    private boolean isCharacterATurn;
    private boolean missionCompleted;
    private boolean missionFailed;
    private List<String> battleLog;
    private int turnCount;

    public BattleSystem(CrewMember charA, CrewMember charB, Mission mission) {
        this.characterA = charA;
        this.characterB = charB;
        this.mission = mission;
        this.isCharacterATurn = true;
        this.missionCompleted = false;
        this.missionFailed = false;
        this.battleLog = new ArrayList<>();
        this.turnCount = 0;

        characterA.resetStatus();
        characterB.resetStatus();
        mission.initializeThreat();
    }

    public ThreatAttackResult executeThreatAttack() {
        turnCount++;
        Threat threat = mission.getThreat();
        if (isCharacterATurn && characterA.isIncapacitated() && !characterB.isIncapacitated()) {
            isCharacterATurn = false;
        } else if (!isCharacterATurn && characterB.isIncapacitated() && !characterA.isIncapacitated()) {
            isCharacterATurn = true;
        }
        CrewMember target = isCharacterATurn ? characterA : characterB;
        threat.updateDebuffs();
        int damage = threat.calculateDamage();
        if (target.getRole().equals("Medic")) {
            damage = (int)(damage * 1.05);
        }
        target.takeDamage(damage, "threat");
        String roleMessage = mission.getThreatMessage(target);
        String fullMessage = roleMessage + " " + target.getName() + " takes " + damage +
                " damage. Choose your next move to neutralize the threat!";

        if (target.isIncapacitated() && !target.isRevived()) {
            if (characterA.isIncapacitated() && characterB.isIncapacitated()) {
                missionFailed = true;
                fullMessage = "Critical failure! Initiate Recovery Protocol 67: locate the nearest habitable planet immediately for recovery. Any found repairs will be completed there before Project Princess 010 can continue her voyage through the stars. Returning to the control room…";
                battleLog.add(fullMessage);
                awardXP(false);
            } else {
                CrewMember remaining = (target == characterA) ? characterB : characterA;
                fullMessage = target.getName() + " has succumbed to the damages and requires immediate medical attention. " +
                        remaining.getName() + " must now complete the mission alone. Good luck!";
                battleLog.add(fullMessage);

                if (target == characterA && !characterB.isIncapacitated()) {
                    isCharacterATurn = false;
                } else if (target == characterB && !characterA.isIncapacitated()) {
                    isCharacterATurn = true;
                }
            }
        } else {
            battleLog.add(fullMessage);
        }
        return new ThreatAttackResult(target, damage, fullMessage);
    }

    public AbilityResult executeCrewAction(String abilityName) {
        CrewMember activeCharacter = isCharacterATurn ? characterA : characterB;
        CrewMember partner = isCharacterATurn ? characterB : characterA;
        Threat threat = mission.getThreat();

        if (!activeCharacter.isAbilityAvailable(abilityName)) {
            String lockedMessage = abilityName + " is currently unavailable. Choose a different ability to neutralize the threat.";
            battleLog.add(lockedMessage);
            return new AbilityResult(lockedMessage, 0, 0, 0, false);
        }

        activeCharacter.updateTurnHistory();
        AbilityResult result = activeCharacter.useAbility(abilityName, partner, threat);

        if (result.success) {
            if (result.damageToThreat > 0) {
                threat.takeDamage(result.damageToThreat);
                activeCharacter.setDamageDealtLastTurn(result.damageToThreat);
            }
            if (result.healingToSelf > 0) {
                activeCharacter.heal(result.healingToSelf);
            }
            if (result.healingToAlly > 0) {
                partner.heal(result.healingToAlly);
            }

            String actionMessage = activeCharacter.getName() + " deploys " + abilityName + " to neutralize the threat!";
            battleLog.add(actionMessage);
            if (!result.message.isEmpty() && !result.message.equals(actionMessage)) {
                battleLog.add(result.message);
            }
        } else {
            battleLog.add(result.message);
        }

        if (threat.isDefeated()) {
            missionCompleted = true;
            String victoryMessage = "Mission accomplished! You have successfully neutralized the threat and kept the passengers safe. " +
                    "Project Princess 010 can now resume her journey through the stars. Returning to the control room…";
            battleLog.add(victoryMessage);
            awardXP(true);
            result.message = victoryMessage;
        }

        if (characterA.isIncapacitated() && characterB.isIncapacitated()) {
            missionFailed = true;
            String failureMessage = "Critical failure! Initiate Recovery Protocol 67: locate the nearest habitable planet immediately for recovery. Any found repairs will be completed there before Project Princess 010 can continue her voyage through the stars. Returning to the control room…";
            battleLog.add(failureMessage);
            awardXP(false);
            result.message = failureMessage;
        }

        isCharacterATurn = !isCharacterATurn;

        // Skip the next character if they are incapacitated and the other is not
        if (!missionCompleted && !missionFailed) {
            if (isCharacterATurn && characterA.isIncapacitated() && !characterB.isIncapacitated()) {
                isCharacterATurn = false;
            } else if (!isCharacterATurn && characterB.isIncapacitated() && !characterA.isIncapacitated()) {
                isCharacterATurn = true;
            }
        }
        return result;
    }

    private void awardXP(boolean missionSuccess) {
        if (missionSuccess) {
            characterA.incrementCompletedMissions();
            characterB.incrementCompletedMissions();
            if (!characterA.isIncapacitated() && !characterB.isIncapacitated()) {
                characterA.addXP(10);
                characterB.addXP(10);
            } else if (characterA.isIncapacitated() && !characterB.isIncapacitated()) {
                characterA.addXP(5);
                characterB.addXP(15);
            } else if (!characterA.isIncapacitated() && characterB.isIncapacitated()) {
                characterA.addXP(15);
                characterB.addXP(5);
            }
        }
    }

    public boolean isAbilityAvailable(CrewMember character, String abilityName) {
        return character.isAbilityAvailable(abilityName);
    }

    public CrewMember getActiveCharacter() { return isCharacterATurn ? characterA : characterB; }

    public CrewMember getInactiveCharacter() { return isCharacterATurn ? characterB : characterA; }

    public boolean isMissionCompleted() { return missionCompleted; }

    public boolean isMissionFailed() { return missionFailed; }

    public List<String> getBattleLog() { return battleLog; }

    public Threat getThreat() { return mission.getThreat(); }

    public CrewMember getCharacterA() { return characterA; }

    public CrewMember getCharacterB() { return characterB; }

    public static class ThreatAttackResult {
        public CrewMember target;
        public int damage;
        public String message;
        public ThreatAttackResult(CrewMember target, int damage, String message) {
            this.target = target;
            this.damage = damage;
            this.message = message;
        }
    }
}
