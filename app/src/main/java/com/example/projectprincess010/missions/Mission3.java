package com.example.projectprincess010.missions;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

public class Mission3 extends Mission {
    public Mission3(int threatLevel) {
        super(
                "Oxygen System Failure",
                "Life support units are failing one by one across the ship. While CO₂ levels are climbing gradually, the crew and passengers will experience rapid critical health deterioration if it reaches the upper deck. Your mission is to repair the units before the CO₂ levels become life-threatening.",
                threatLevel
        );
    }

    @Override
    protected void initializeMissionMessages() {
        missionMessages.put("Pilot", "The ship's atmospheric pressure is dropping unevenly across decks; therefore, destabilizing flight controls.");
        missionMessages.put("Medic", "Some of the fleet's crew members and passengers who were in the affected sections are showing symptoms of hypoxia.");
        missionMessages.put("Engineer", "Another life support unit has just gone offline.");
        missionMessages.put("Scientist", "A chemical contaminant has been detected in the failing oxygen supply units. Further contamination will accelerate the degradation of remaining life support units beyond repair.");
        missionMessages.put("Soldier", "Panicked passengers are attempting to onboard the locked escape pods to abandon ship.");
    }

    @Override
    protected String getThreatName() {
        return "Oxygen Depletion";
    }
}