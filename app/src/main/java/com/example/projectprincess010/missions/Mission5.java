package com.example.projectprincess010.missions;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

public class Mission5 extends Mission {
    public Mission5(int threatLevel) {
        super(
                "Breach in an Observation Deck",
                "A micrometeorite has punched through the hull of an observation deck, leaving a gaping hole open to the void. Air pressure is bleeding fast, and the clock is already running. Your mission is to seal the breach and restore pressure before oxygen loss becomes irreversible.",
                threatLevel
        );
    }

    @Override
    protected void initializeMissionMessages() {
        missionMessages.put("Pilot", "The pressure loss from the breach is causing severe instability, making it increasingly difficult to maintain control of the ship.");
        missionMessages.put("Medic", "Rescued crew members and passengers who were in the observation deck have suffered injuries.");
        missionMessages.put("Engineer", "The breach is expanding: the micrometeorite has weakened the surrounding hull plating, and the pressure differential is tearing the gap wider by the second.");
        missionMessages.put("Scientist", "Rapid exposure to the near vacuum is degrading the hull sealant compounds.");
        missionMessages.put("Soldier", "Some of the crew members and passengers are trapped near the breach, and the suction is pulling them toward the void. However, evacuation is being blocked by debris scattered across the damaged observation deck.");
    }

    @Override
    protected String getThreatName() {
        return "Hull Breach";
    }
}