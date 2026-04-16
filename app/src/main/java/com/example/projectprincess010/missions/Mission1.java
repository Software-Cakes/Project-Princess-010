package com.example.projectprincess010.missions;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

public class Mission1 extends Mission {
    public Mission1(int threatLevel) {
        super(
                "Fly Through the Uncharted Asteroid Field",
                "An uncharted asteroid field has suddenly appeared directly in the cruise’s path. However, the navigational system is completely offline. Your mission is to guide the ship through the field manually, dodging debris and avoiding collisions while they reboot the system.",
                threatLevel
        );
    }

    @Override
    protected void initializeMissionMessages() {
        missionMessages.put("Pilot", "A dense cluster of boulders is closing in fast.");
        missionMessages.put("Medic", "Shockwaves from nearby impacts have injured several crew members.");
        missionMessages.put("Engineer", "Collision impacts are causing electrical surges throughout the ship. If the power grid destabilizes, the reboot of the navigational system will fail entirely.");
        missionMessages.put("Scientist", "Asteroid debris is releasing a cloud of ionized particles that are interfering with the ship's sensors.");
        missionMessages.put("Soldier", "A large asteroid fragment is heading straight to the ship.");
    }

    @Override
    protected String getThreatName() {
        return "Asteroid Swarm";
    }
}