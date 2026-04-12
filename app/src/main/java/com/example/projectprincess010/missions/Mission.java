package com.example.projectprincess010.missions;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import com.example.projectprincess010.crewMembers.CrewMember;
import com.example.projectprincess010.models.Threat;
import java.util.HashMap;
import java.util.Map;

public abstract class Mission {
    protected String missionName;
    protected String missionDescription;
    protected Threat threat;
    protected int threatLevel;
    protected Map<String, String> missionMessages;

    public Mission(String name, String description, int threatLevel) {
        this.missionName = name;
        this.missionDescription = description;
        this.threatLevel = threatLevel;
        this.missionMessages = new HashMap<>();
        initializeMissionMessages();
    }

    protected abstract void initializeMissionMessages();
    protected abstract String getThreatName();

    public void initializeThreat() {
        this.threat = new Threat(getThreatName(), threatLevel);
    }

    public String getThreatMessage(CrewMember character) {
        String role = character.getRole();
        return missionMessages.getOrDefault(role, "The threat looms ominously!");
    }

    public Threat getThreat() { return threat; }

    public String getMissionName() { return missionName; }

    public String getMissionDescription() { return missionDescription; }
}