package com.example.projectprincess010.activities;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectprincess010.R;
import com.example.projectprincess010.battleSystem.BattleSystem;
import com.example.projectprincess010.crewMembers.CrewMember;
import com.example.projectprincess010.missions.*;
import com.example.projectprincess010.models.AbilityResult;
import com.example.projectprincess010.utils.CrewRepository;

public class MissionBattle extends AppCompatActivity {
    private BattleSystem battleSystem;
    private CrewMember characterA;
    private CrewMember characterB;
    private Mission mission;
    private TextView battleMessage;
    private ImageView imgCharA, imgCharB;
    private TextView nameCharA, lvlCharA, hpTextCharA;
    private TextView nameCharB, lvlCharB, hpTextCharB;
    private ProgressBar hpBarCharA, hpBarCharB;
    private Button[] abilityButtons = new Button[4];
    private boolean isWaitingForAction = false;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_battle);

        loadCharactersAndMission();
        if (isFinishing()) return;
        
        setupUI();
        startBattle();
    }

    private void loadCharactersAndMission() {
        int indexA = getIntent().getIntExtra("charAIndex", -1);
        int indexB = getIntent().getIntExtra("charBIndex", -1);
        int missionNumber = getIntent().getIntExtra("missionNumber", 1);

        characterA = CrewRepository.getCrewMember(indexA);
        characterB = CrewRepository.getCrewMember(indexB);

        if (characterA == null || characterB == null) {
            Toast.makeText(this, "Crew data error! Returning home.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        int avgLevel = (characterA.getLevel() + characterB.getLevel()) / 2;

        switch(missionNumber) {
            case 1: mission = new Mission1(avgLevel); break;
            case 2: mission = new Mission2(avgLevel); break;
            case 3: mission = new Mission3(avgLevel); break;
            case 4: mission = new Mission4(avgLevel); break;
            case 5: mission = new Mission5(avgLevel); break;
            default: mission = new Mission1(avgLevel);
        }

        battleSystem = new BattleSystem(characterA, characterB, mission);
    }

    private void setupUI() {
        battleMessage = findViewById(R.id.battle_message);
        imgCharA = findViewById(R.id.img_char_a);
        imgCharB = findViewById(R.id.img_char_b);
        nameCharA = findViewById(R.id.name_char_a);
        lvlCharA = findViewById(R.id.lvl_char_a);
        hpTextCharA = findViewById(R.id.hp_text_char_a);
        hpBarCharA = findViewById(R.id.hp_bar_char_a);
        nameCharB = findViewById(R.id.name_char_b);
        lvlCharB = findViewById(R.id.lvl_char_b);
        hpTextCharB = findViewById(R.id.hp_text_char_b);
        hpBarCharB = findViewById(R.id.hp_bar_char_b);

        abilityButtons[0] = findViewById(R.id.btn_ability_1);
        abilityButtons[1] = findViewById(R.id.btn_ability_2);
        abilityButtons[2] = findViewById(R.id.btn_ability_3);
        abilityButtons[3] = findViewById(R.id.btn_ability_4);

        // Set Images based on role
        imgCharA.setImageResource(getRoleImage(characterA.getRole()));
        imgCharB.setImageResource(getRoleImage(characterB.getRole()));

        // Set Names and Levels
        nameCharA.setText(characterA.getName());
        lvlCharA.setText("Lvl. " + characterA.getLevel());
        nameCharB.setText(characterB.getName());
        lvlCharB.setText("Lvl. " + characterB.getLevel());

        // Bottom Navigation
        findViewById(R.id.btn_control_room).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btn_add_crew).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddNewCrewMember.class);
            startActivity(intent);
        });

        updateUI();
    }

    private int getRoleImage(String role) {
        if (role == null) return R.drawable.medic;
        switch (role) {
            case "Pilot": return R.drawable.pilot;
            case "Medic": return R.drawable.medic;
            case "Engineer": return R.drawable.engineer;
            case "Scientist": return R.drawable.scientist;
            case "Soldier": return R.drawable.soldier;
            default: return R.drawable.medic;
        }
    }

    private void startBattle() {
        handler.postDelayed(() -> {
            if (isFinishing()) return;
            BattleSystem.ThreatAttackResult result = battleSystem.executeThreatAttack();
            updateUI();
            battleMessage.setText(result.message);
            if (battleSystem.isMissionCompleted() || battleSystem.isMissionFailed()) {
                endMission();
            } else {
                showAbilityButtons();
            }
        }, 1000);
    }

    private void showAbilityButtons() {
        CrewMember active = battleSystem.getActiveCharacter();
        String[] abilities = getAbilitiesForRole(active.getRole());

        for (int i = 0; i < 4; i++) {
            if (i < abilities.length) {
                String ability = abilities[i];
                abilityButtons[i].setVisibility(View.VISIBLE);
                abilityButtons[i].setText(ability);
                abilityButtons[i].setEnabled(battleSystem.isAbilityAvailable(active, ability));
                abilityButtons[i].setOnClickListener(v -> onAbilitySelected(ability));
            } else {
                abilityButtons[i].setVisibility(View.GONE);
            }
        }
        isWaitingForAction = true;
    }

    private String[] getAbilitiesForRole(String role) {
        if (role == null) return new String[]{};
        switch (role) {
            case "Pilot": return new String[]{"Evasive Maneuver", "Slipstream Draft", "Navigation Lock", "Emergency Burn"};
            case "Medic": return new String[]{"Field Triage", "Antidote Protocol", "Adrenaline Shot", "Emergency Revival"};
            case "Engineer": return new String[]{"Overload Circuit", "EMP Pulse", "Reinforce Armor", "System Reroute"};
            case "Scientist": return new String[]{"Exploited Vulnerability", "Chemical Catalyst", "Plasma Burst", "Chain Reaction"};
            case "Soldier": return new String[]{"Protection Shield", "Iron Wall", "Suppressive Fire", "Last Stand"};
            default: return new String[]{};
        }
    }

    private void onAbilitySelected(String ability) {
        if (!isWaitingForAction) {
            String lockedMessage = ability + " is currently unavailable. Choose a different ability to neutralize the threat.";
            battleMessage.setText(lockedMessage);
            return;
        }
        isWaitingForAction = false;

        AbilityResult result = battleSystem.executeCrewAction(ability);
        updateUI();
        battleMessage.setText(result.message);

        if (battleSystem.isMissionCompleted() || battleSystem.isMissionFailed()) {
            endMission();
            return;
        }

        handler.postDelayed(() -> {
            if (isFinishing()) return;
            BattleSystem.ThreatAttackResult threatResult = battleSystem.executeThreatAttack();
            updateUI();
            battleMessage.setText(threatResult.message);

            if (battleSystem.isMissionCompleted() || battleSystem.isMissionFailed()) {
                endMission();
            } else {
                showAbilityButtons();
            }
        }, 1500);
    }

    private void updateUI() {
        updateCharacterHP(characterA, hpBarCharA, hpTextCharA);
        updateCharacterHP(characterB, hpBarCharB, hpTextCharB);
    }

    private void updateCharacterHP(CrewMember character, ProgressBar hpBar, TextView hpText) {
        if (character == null) return;
        hpBar.setMax(character.getMaxHP());
        hpBar.setProgress(character.getCurrentHP());
        hpText.setText(character.getCurrentHP() + "/" + character.getMaxHP() + " HP");
    }

    private void endMission() {
        isWaitingForAction = false;
        for (Button btn : abilityButtons) btn.setEnabled(false);

        handler.postDelayed(() -> {
            if (isFinishing()) return;
            Toast.makeText(this, "Mission Over. Returning to Control Room...", Toast.LENGTH_LONG).show();
            finish();
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
