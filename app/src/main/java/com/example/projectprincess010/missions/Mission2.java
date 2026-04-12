package com.example.projectprincess010.missions;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

public class Mission2 extends Mission {
    public Mission2(int threatLevel) {
        super(
                "Reactor Core Meltdown",
                "Project Princess 010’s core is overheating and may soon spiral towards critical failure. Your mission is to stabilize the core by engaging in emergency cooling protocols and mitigate the situation before the core breaches containment.",
                threatLevel
        );
    }

    @Override
    protected void initializeMissionMessages() {
        missionMessages.put("Pilot", "Violent power fluctuations from the unstable core reactor are throwing the ship off course.");
        missionMessages.put("Medic", "Radiation exposure near the core has been affecting the crew, and the symptoms are appearing fast. Untreated exposure is fatal to the fleet.");
        missionMessages.put("Engineer", "The reactor's primary coolant loop has ruptured, resulting in the core temperature spiking to dangerous levels.");
        missionMessages.put("Scientist", "The reactor is emitting an unstable burst of radiation that is jamming all systems of the cruise ship.");
        missionMessages.put("Soldier", "Pressure from the overheating core has blown the reactor bay doors open.");
    }

    @Override
    protected String getThreatName() {
        return "Reactor Meltdown";
    }
}