package com.example.projectprincess010.missions;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

public class Mission4 extends Mission {
    public Mission4(int threatLevel) {
        super(
                "Mysterious Illness Outbreak",
                "Following a visit to a recently visited planet, passengers are falling ill. After examining patients in the isolation pods, it is revealed that the discovered pathogen is unlike anything in the fleet’s medical database. Your mission is to contain the outbreak before it spreads ship wide. Remember, the isolation pods cannot hold forever.",
                threatLevel
        );
    }

    @Override
    protected void initializeMissionMessages() {
        missionMessages.put("Pilot", "Infected crew members are no longer fit for duty, and failing automated systems are surging through the flight controls.");
        missionMessages.put("Medic", "Passengers in the quarantine area are deteriorating faster than you can treat them.");
        missionMessages.put("Engineer", "The ship's ventilation system is spreading the pathogen to quarantine-free zones.");
        missionMessages.put("Scientist", "Time is running out. The pathogen seems to start mutating, and the treatment formula you are currently developing may become ineffective.");
        missionMessages.put("Soldier", "Some passengers are starting to panic. They may soon cause ship-wide hysteria and risk spreading the illness throughout the ship.");
    }

    @Override
    protected String getThreatName() {
        return "Alien Pathogen";
    }
}