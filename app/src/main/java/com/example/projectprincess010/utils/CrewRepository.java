package com.example.projectprincess010.utils;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import android.content.Context;
import android.content.SharedPreferences;
import com.example.projectprincess010.crewMembers.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CrewRepository {
    private static List<CrewMember> crewMembers = new ArrayList<>();
    private static final String PREFS_NAME = "SpaceFleetPrefs";
    private static final String KEY_CREW_DATA = "crew_data_json";

    public static void loadCrew(Context context) {
        if (!crewMembers.isEmpty()) return;
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_CREW_DATA, null);
        if (json != null) {
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String name = obj.getString("name");
                    String role = obj.getString("role");
                    int level = obj.getInt("level");
                    int totalXP = obj.getInt("totalXP");
                    int completed = obj.getInt("completed");

                    CrewMember member = createMember(role, name);
                    if (member != null) {
                        member.setLevel(level);
                        member.setTotalXP(totalXP);
                        member.setCompletedMissions(completed);
                        crewMembers.add(member);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveCrew(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        try {
            JSONArray array = new JSONArray();
            for (CrewMember m : crewMembers) {
                JSONObject obj = new JSONObject();
                obj.put("name", m.getName());
                obj.put("role", m.getRole());
                obj.put("level", m.getLevel());
                obj.put("totalXP", m.getTotalXP());
                obj.put("completed", m.getCompletedMissions());
                array.put(obj);
            }
            prefs.edit().putString(KEY_CREW_DATA, array.toString()).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CrewMember createMember(String role, String name) {
        switch (role) {
            case "Pilot": return new Pilot(name);
            case "Medic": return new Medic(name);
            case "Engineer": return new Engineer(name);
            case "Scientist": return new Scientist(name);
            case "Soldier": return new Soldier(name);
            default: return null;
        }
    }

    public static void addCrewMember(CrewMember member) {
        crewMembers.add(member);
    }

    public static List<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    public static CrewMember getCrewMember(int index) {
        if (index >= 0 && index < crewMembers.size()) {
            return crewMembers.get(index);
        }
        return null;
    }

    public static int getCrewCount() {
        return crewMembers.size();
    }

    public static void clearCrewMembers() {
        crewMembers.clear();
    }
}
