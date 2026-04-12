package com.example.projectprincess010.activities;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectprincess010.R;
import com.example.projectprincess010.crewMembers.CrewMember;
import com.example.projectprincess010.missions.*;
import com.example.projectprincess010.utils.CrewRepository;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout crewMembersContainer;
    private LinearLayout missionsContainer;
    private TextView welcomeBackText;
    private TextView cockpitCountText;
    private List<Mission> missionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleFirstRunReset();
        CrewRepository.loadCrew(this);
        initializeViews();
        setupBottomNavigation();
        loadMissions();
        refreshUI();
    }

    private void handleFirstRunReset() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        
        if (isFirstRun) {
            CrewRepository.clearCrewMembers();
            CrewRepository.saveCrew(this);
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }
    }

    private void initializeViews() {
        crewMembersContainer = findViewById(R.id.crewMembersContainer);
        missionsContainer = findViewById(R.id.missionsContainer);
        welcomeBackText = findViewById(R.id.welcomeBackText);
        cockpitCountText = findViewById(R.id.cockpitCountText);
    }

    private void setupBottomNavigation() {
        findViewById(R.id.bottomNavControlRoom).setOnClickListener(v -> {
            refreshUI();
            Toast.makeText(this, "Control Room", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.bottomNavAddCrew).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNewCrewMember.class);
            startActivity(intent);
        });
    }

    private void loadMissions() {
        missionsList = Arrays.asList(
                new Mission1(1),
                new Mission2(1),
                new Mission3(1),
                new Mission4(1),
                new Mission5(1)
        );

        missionsContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < missionsList.size(); i++) {
            final int missionNumber = i + 1;
            Mission mission = missionsList.get(i);
            View missionCard = inflater.inflate(R.layout.item_mission_card, missionsContainer, false);

            TextView title = missionCard.findViewById(R.id.mission_title);
            ImageView image = missionCard.findViewById(R.id.mission_image);
            Button btnComplete = missionCard.findViewById(R.id.btn_complete);

            title.setText(String.format("# Mission %d: %s", missionNumber, mission.getMissionName()));
            image.setImageResource(R.drawable.mission_background);

            View.OnClickListener listener = v -> selectMission(missionNumber);
            missionCard.setOnClickListener(listener);
            btnComplete.setOnClickListener(listener);
            btnComplete.setText("Complete");

            missionsContainer.addView(missionCard);
        }
    }

    private void refreshUI() {
        updateCockpitCount();
        loadCrewMembers();
    }

    private void updateCockpitCount() {
        int crewCount = CrewRepository.getCrewCount();
        if (cockpitCountText != null) {
            cockpitCountText.setText(crewCount + " Astronaut" + (crewCount != 1 ? "s" : "") + " in the Cockpit");
        }
    }

    private void loadCrewMembers() {
        if (crewMembersContainer == null) return;
        
        crewMembersContainer.removeAllViews();
        List<CrewMember> crewMembers = CrewRepository.getCrewMembers();
        LayoutInflater inflater = LayoutInflater.from(this);

        if (crewMembers.isEmpty()) {
            TextView emptyText = new TextView(this);
            emptyText.setText("No crew members yet.\nTap 'Add New Crew Member' below.");
            emptyText.setTextColor(getResources().getColor(R.color.white));
            emptyText.setPadding(20, 20, 20, 20);
            crewMembersContainer.addView(emptyText);
            return;
        }

        for (int i = 0; i < crewMembers.size(); i++) {
            final int index = i;
            CrewMember member = crewMembers.get(i);
            View crewCard = inflater.inflate(R.layout.item_crew_card, crewMembersContainer, false);

            ImageView crewImage = crewCard.findViewById(R.id.crew_image);
            TextView crewName = crewCard.findViewById(R.id.crew_name);
            TextView roleText = crewCard.findViewById(R.id.role_text);
            ImageView roleIcon = crewCard.findViewById(R.id.role_icon);
            TextView levelText = crewCard.findViewById(R.id.level_text);
            TextView xpText = crewCard.findViewById(R.id.xp_text);
            TextView completedMissions = crewCard.findViewById(R.id.completed_missions);

            crewName.setText(member.getName());
            roleText.setText(member.getRole());
            levelText.setText("Lvl. " + member.getLevel());
            xpText.setText(member.getTotalXP() + " XP");
            completedMissions.setText(member.getCompletedMissions() + " Completed Missions");

            crewImage.setImageResource(getRoleImageRes(member.getRole()));
            roleIcon.setImageResource(getRoleIconRes(member.getRole()));

            crewName.setOnClickListener(v -> showEditNameDialog(member, index));

            crewMembersContainer.addView(crewCard);
        }
    }

    private void showEditNameDialog(CrewMember member, int index) {
        final EditText nameInput = new EditText(this);
        nameInput.setText(member.getName());
        int padding = (int) (24 * getResources().getDisplayMetrics().density);
        nameInput.setPadding(padding, padding, padding, padding);

        new AlertDialog.Builder(this)
                .setTitle("Edit Character Name")
                .setView(nameInput)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = nameInput.getText().toString().trim();
                    if (!newName.isEmpty()) {
                        updateMemberName(index, newName);
                    } else {
                        Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void updateMemberName(int index, String newName) {
        CrewMember member = CrewRepository.getCrewMember(index);
        if (member != null) {
            member.setName(newName);
            CrewRepository.saveCrew(this); // Persist name change
            refreshUI();
            Toast.makeText(this, "Name updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private int getRoleImageRes(String role) {
        switch (role) {
            case "Pilot": return R.drawable.pilot;
            case "Medic": return R.drawable.medic;
            case "Engineer": return R.drawable.engineer;
            case "Scientist": return R.drawable.scientist;
            case "Soldier": return R.drawable.soldier;
            default: return R.drawable.medic;
        }
    }

    private int getRoleIconRes(String role) {
        switch (role) {
            case "Pilot": return R.drawable.pilot_icon;
            case "Medic": return R.drawable.medic_icon;
            case "Engineer": return R.drawable.engineer_icon;
            case "Scient_icon": return R.drawable.scientist_icon;
            case "Soldier": return R.drawable.soldier_icon;
            default: return R.drawable.pilot_icon;
        }
    }

    private void selectMission(int missionNumber) {
        Intent intent = new Intent(this, MissionControl.class);
        intent.putExtra("missionNumber", missionNumber);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUI();
    }
}
