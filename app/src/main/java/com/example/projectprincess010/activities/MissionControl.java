package com.example.projectprincess010.activities;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectprincess010.R;
import com.example.projectprincess010.crewMembers.CrewMember;
import com.example.projectprincess010.missions.*;
import com.example.projectprincess010.utils.CrewRepository;

import java.util.List;

public class MissionControl extends AppCompatActivity {
    private int missionNumber;
    private TextView missionTitleText;
    private TextView missionDescriptionText;
    private TextView crewAName, crewARole, crewALevel, crewAXP;
    private ImageView crewARoleIcon;
    private TextView crewBName, crewBRole, crewBLevel, crewBXP;
    private ImageView crewBRoleIcon;
    private Button selectCrewAButton, selectCrewBButton, startMissionButton;
    private ImageButton btnPrevA, btnNextA, btnPrevB, btnNextB;
    private List<CrewMember> crewMembers;
    private int selectedCrewAIndex = -1;
    private int selectedCrewBIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_control);

        missionNumber = getIntent().getIntExtra("missionNumber", 1);
        crewMembers = CrewRepository.getCrewMembers();

        initializeViews();
        setupMissionInfo();
        setupBottomNavigation();
        updateCrewSelectionUI();
    }

    private void initializeViews() {
        missionTitleText = findViewById(R.id.missionTitleText);
        missionDescriptionText = findViewById(R.id.missionDescriptionText);
        
        crewAName = findViewById(R.id.crewAName);
        crewARole = findViewById(R.id.crewARole);
        crewALevel = findViewById(R.id.crewALevel);
        crewAXP = findViewById(R.id.crewAXP);
        crewARoleIcon = findViewById(R.id.crewARoleIcon);
        
        crewBName = findViewById(R.id.crewBName);
        crewBRole = findViewById(R.id.crewBRole);
        crewBLevel = findViewById(R.id.crewBLevel);
        crewBXP = findViewById(R.id.crewBXP);
        crewBRoleIcon = findViewById(R.id.crewBRoleIcon);
        
        selectCrewAButton = findViewById(R.id.selectCrewAButton);
        selectCrewBButton = findViewById(R.id.selectCrewBButton);
        startMissionButton = findViewById(R.id.startMissionButton);
        
        btnPrevA = findViewById(R.id.btn_prev_crew_a);
        btnNextA = findViewById(R.id.btn_next_crew_a);
        btnPrevB = findViewById(R.id.btn_prev_crew_b);
        btnNextB = findViewById(R.id.btn_next_crew_b);

        selectCrewAButton.setOnClickListener(v -> showCrewSelectionDialog(true));
        selectCrewBButton.setOnClickListener(v -> showCrewSelectionDialog(false));
        
        btnPrevA.setOnClickListener(v -> cycleCrew(true, -1));
        btnNextA.setOnClickListener(v -> cycleCrew(true, 1));
        btnPrevB.setOnClickListener(v -> cycleCrew(false, -1));
        btnNextB.setOnClickListener(v -> cycleCrew(false, 1));
        
        startMissionButton.setOnClickListener(v -> startMission());
    }

    private void setupMissionInfo() {
        Mission mission = createMission(missionNumber, 1);
        String missionTitle = "MISSION " + missionNumber + ": " + mission.getMissionName().toUpperCase();
        missionTitleText.setText(missionTitle);
        missionDescriptionText.setText(mission.getMissionDescription());
    }

    private void setupBottomNavigation() {
        findViewById(R.id.bottomNavControlRoom).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.bottomNavAddCrew).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddNewCrewMember.class);
            startActivity(intent);
        });
    }

    private void cycleCrew(boolean isCrewA, int direction) {
        if (crewMembers.isEmpty()) return;
        if (isCrewA) {
            if (selectedCrewAIndex == -1) {
                selectedCrewAIndex = direction > 0 ? 0 : crewMembers.size() - 1;
            } else {
                selectedCrewAIndex = (selectedCrewAIndex + direction + crewMembers.size()) % crewMembers.size();
            }
        } else {
            if (selectedCrewBIndex == -1) {
                selectedCrewBIndex = direction > 0 ? 0 : crewMembers.size() - 1;
            } else {
                selectedCrewBIndex = (selectedCrewBIndex + direction + crewMembers.size()) % crewMembers.size();
            }
        }
        updateCrewSelectionUI();
    }

    private void showCrewSelectionDialog(final boolean isCrewA) {
        if (crewMembers.isEmpty()) {
            Toast.makeText(this, "No crew members available.", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] names = new String[crewMembers.size()];
        for (int i = 0; i < crewMembers.size(); i++) {
            CrewMember m = crewMembers.get(i);
            names[i] = m.getName() + " (" + m.getRole() + ")";
        }
        new AlertDialog.Builder(this)
                .setTitle("Select Crew Member")
                .setItems(names, (dialog, which) -> {
                    if (isCrewA) selectedCrewAIndex = which;
                    else selectedCrewBIndex = which;
                    updateCrewSelectionUI();
                })
                .show();
    }

    private void updateCrewSelectionUI() {
        if (selectedCrewAIndex >= 0 && selectedCrewAIndex < crewMembers.size()) {
            CrewMember crewA = crewMembers.get(selectedCrewAIndex);
            crewAName.setText(crewA.getName());
            crewARole.setText(crewA.getRole());
            crewALevel.setText("Lvl. " + crewA.getLevel());
            crewAXP.setText(crewA.getTotalXP() + " XP");
            crewARoleIcon.setImageResource(getRoleIcon(crewA.getRole()));
            crewARoleIcon.setVisibility(View.VISIBLE);
        } else {
            crewAName.setText("Select Player");
            crewARole.setText("");
            crewALevel.setText("");
            crewAXP.setText("");
            crewARoleIcon.setVisibility(View.GONE);
        }
        if (selectedCrewBIndex >= 0 && selectedCrewBIndex < crewMembers.size()) {
            CrewMember crewB = crewMembers.get(selectedCrewBIndex);
            crewBName.setText(crewB.getName());
            crewBRole.setText(crewB.getRole());
            crewBLevel.setText("Lvl. " + crewB.getLevel());
            crewBXP.setText(crewB.getTotalXP() + " XP");
            crewBRoleIcon.setImageResource(getRoleIcon(crewB.getRole()));
            crewBRoleIcon.setVisibility(View.VISIBLE);
        } else {
            crewBName.setText("Select Player");
            crewBRole.setText("");
            crewBLevel.setText("");
            crewBXP.setText("");
            crewBRoleIcon.setVisibility(View.GONE);
        }
        startMissionButton.setEnabled(true);
    }

    private int getRoleIcon(String role) {
        switch (role) {
            case "Pilot": return R.drawable.pilot_icon;
            case "Medic": return R.drawable.medic_icon;
            case "Engineer": return R.drawable.engineer_icon;
            case "Scientist": return R.drawable.scientist_icon;
            case "Soldier": return R.drawable.soldier_icon;
            default: return R.drawable.medic_icon;
        }
    }

    private void startMission() {
        if (selectedCrewAIndex == -1 || selectedCrewBIndex == -1) {
            Toast.makeText(this, "Please select players for the mission", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedCrewAIndex == selectedCrewBIndex) {
            Toast.makeText(this, "You must select two different crew members!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, MissionBattle.class);
        intent.putExtra("charAIndex", selectedCrewAIndex);
        intent.putExtra("charBIndex", selectedCrewBIndex);
        intent.putExtra("missionNumber", missionNumber);
        startActivity(intent);
    }

    private Mission createMission(int missionNumber, int threatLevel) {
        switch (missionNumber) {
            case 1: return new Mission1(threatLevel);
            case 2: return new Mission2(threatLevel);
            case 3: return new Mission3(threatLevel);
            case 4: return new Mission4(threatLevel);
            case 5: return new Mission5(threatLevel);
            default: return new Mission1(threatLevel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        crewMembers = CrewRepository.getCrewMembers();
        if (selectedCrewAIndex >= crewMembers.size()) selectedCrewAIndex = -1;
        if (selectedCrewBIndex >= crewMembers.size()) selectedCrewBIndex = -1;
        updateCrewSelectionUI();
    }
}
