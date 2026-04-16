package com.example.projectprincess010.utils;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import android.content.Context;
import android.content.SharedPreferences;
import com.example.projectprincess010.crewMembers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

public class CrewRepository {
    private static Map<String, CrewMember> crewMembers = new HashMap<>();
    private static List<String> idList = new ArrayList<>();
    
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
                    String id = obj.optString("id", UUID.randomUUID().toString());
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
                        
                        crewMembers.put(id, member);
                        idList.add(id);
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
            for (String id : idList) {
                CrewMember m = crewMembers.get(id);
                if (m == null) continue;
                
                JSONObject obj = new JSONObject();
                obj.put("id", id);
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
        String id = UUID.randomUUID().toString();
        crewMembers.put(id, member);
        idList.add(id);
    }

    public static List<CrewMember> getCrewMembers() {
        List<CrewMember> list = new ArrayList<>();
        for (String id : idList) {
            list.add(crewMembers.get(id));
        }
        return list;
    }

    public static CrewMember getCrewMember(int index) {
        if (index >= 0 && index < idList.size()) {
            return crewMembers.get(idList.get(index));
        }
        return null;
    }
    
    public static CrewMember getCrewMemberById(String id) {
        return crewMembers.get(id);
    }

    public static int getCrewCount() {
        return idList.size();
    }

    public static void clearCrewMembers() {
        crewMembers.clear();
        idList.clear();
    }
}
