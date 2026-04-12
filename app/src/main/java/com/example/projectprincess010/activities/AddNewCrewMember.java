package com.example.projectprincess010.activities;

// Claude and Gemini were used to help in explaining and fixing reported code errors and bugs.

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectprincess010.R;
import com.example.projectprincess010.crewMembers.*;
import com.example.projectprincess010.utils.CrewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddNewCrewMember extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText searchBar;
    private RecruitmentAdapter adapter;
    private List<RecruitmentRole> allRoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crew);

        initializeRoles();
        setupUI();
        setupBottomNavigation();
    }

    private void initializeRoles() {
        allRoles = new ArrayList<>();

        // Pilot
        allRoles.add(new RecruitmentRole("Pilot",
                "Razor-focused and cool under pressure, the Pilot is the brain of any mission. The Pilot reads incoming threats, making split-second calls to keep everyone safe while simultaneously mapping escape routes in the case the worst comes to pass.",
                "1. Evasive Maneuver: Grants the second crew member a dodge buff for the next 4 turns, reducing their incoming damage by 15%.\n" +
                        "2. Slipstream Draft: Nullifies 30% of damage the Pilot receives this turn and the following 3 turns.\n" +
                        "3. Navigation Lock: Reduces the current threat’s outgoing damage by 20% and raises the second crew member’s attack by 25% for the next 2 turns.\n" +
                        "4. Emergency Burn: Nullifies 50% of the net HP damage the second crew member absorbed in the previous 4 turns.",
                "1. Exposed Cockpit: Takes 15% additional damage whenever targeted directly.\n" +
                        "2. Automatic Control Loss: Emergency Burn becomes unusable if the second crew member is incapacitated.\n" +
                        "3. Motion Sickness: If the Pilot takes 45% or more damage to their max HP in a single turn, Navigation Lock is cancelled and becomes unusable that turn.\n" +
                        "4. Tunnel Vision: After using Evasive Maneuver or Slipstream Draft, the Pilot’s own defense decreases by 10% the following turn.",
                R.drawable.pilot));

        // Medic
        allRoles.add(new RecruitmentRole("Medic",
                "Equal parts scientist and caregiver, the Medic keeps the crew mission-ready and the passengers safe and sound. The Medic, alongside with the Scientist, synthesizes treatments on the fly, reverses the worst conditions imaginable, and can push the crew members past their normal limits when the situation demands it.",
                "1. Field Triage: Reverses 40% of damage the target received in the previous 2 turns.\n" +
                        "2. Antidote Protocol: Reduces incoming damage to the second crew member by 20 next turn and reflects x2 that reduced amount back to the threat.\n" +
                        "3. Adrenaline Shot: Sacrifices 15% of the Medic’s current HP to boost both crew members’ attack damage by 40% for the next 4 turns.\n" +
                        "4. Emergency Revival: Revives the second crew member at full HP. Each subsequent use to restore HP reduces by 10% (e.g., second use: 90%; third use: 80%).",
                "1. Everyone’s Healer: When receiving healing, it is capped at 50% of their maximum HP.\n" +
                        "2. Supply Limited: After any ability is used, that same ability is unavailable for the next 3 turns.\n" +
                        "3. Priority Target: Threats increase their attack damage against the Medic by 5% with each passing turn, regardless of actions taken.\n" +
                        "4. Contamination Risk: The Medic takes an additional 10% damage per turn from all sources as the mission progresses, stacking cumulatively.",
                R.drawable.medic));

        // Engineer
        allRoles.add(new RecruitmentRole("Engineer",
                "Resourceful, methodical, and relentlessly efficient, the Engineer is the fleet’s problem-solver. The Engineer improvises solutions in unprecedented circumstances while keeping every crewmate and passenger as safe as possible.",
                "1. Overload Circuit: Increases the second crew member’s attack effectiveness by 200% for their next 2 turns.\n" +
                        "2. EMP Pulse: Stuns the current threat, reducing the damage they deal by 15% for the next 2 turns.\n" +
                        "3. Reinforce Armor: Deploys a barrier on both crew members, absorbing 10% of the next 3 hits each.\n" +
                        "4. System Reroute: Redirects the active threat’s debuff effect away from the Engineer into the mission environment, reducing the threat’s next attack by 20%.",
                "1. Overheat: Each use of Overload Circuit costs the Engineer 10% of their current HP immediately.\n" +
                        "2. Heavy Gear: After using EMP Pulse, the Engineer receives 10% additional damage from all sources the next turn.\n" +
                        "3. Technical Difficulties: After deploying Reinforce Armor, the ability is unavailable for the next 3 turns.\n" +
                        "4. System Reroute Failure: If there is no active incoming debuff to redirect, the Engineer loses their turn and takes 10% additional damage for the next 2 turns.",
                R.drawable.engineer));

        // Scientist
        allRoles.add(new RecruitmentRole("Scientist",
                "Dangerous, brilliant, and calculative, the Scientist weaponizes knowledge itself. The Scientist analyzes threats mid-battle, exploits every exposed vulnerability, and delivers devastating blows that bypass conventional defenses.",
                "1. Exploited Vulnerability: Multiplies the damage the second crew member dealt in the previous turn by x5, applying it to the current attack (e.g., partner dealt 100 -> 500 bonus damage to threat).\n" +
                        "2. Chemical Catalyst: Removes 15% of damage received in the previous 2 turns and nullifies the same amount in the next 2 turns.\n" +
                        "3. Plasma Burst: Takes 20% of damage the second crew member received last turn (after mitigation and reflection) and deals it as bonus damage to the current attack.\n" +
                        "4. Chain Reaction: Reduces all threat attack damage by 10% per turn, stacking cumulatively for up to 4 turns. Stacks persist for the remainder of the mission once applied.",
                "1. Volatile Reaction: After using Exploited Vulnerability, the ability is unavailable for the next 3 turns.\n" +
                        "2. Miscalculated: Triggers only when the second crew member receives zero net HP damage and deals zero damage last turn. Plasma Burst fails and the Scientist takes an additional 15% of that turn’s incoming attack. Reflection counts as dealing damage and prevents this trigger.\n" +
                        "3. Weak Linkage: Exploited Vulnerability and Plasma Burst become unusable while the second crew member is incapacitated.\n" +
                        "4. Chemical Burn: After using Chemical Catalyst, the ability is locked for the next 2 turns.",
                R.drawable.scientist));

        // Soldier
        allRoles.add(new RecruitmentRole("Soldier",
                "Built for the front line, the Soldier stands between every incoming threat and the rest of the crew. The Soldier absorbs damages no one else could survive, stabilizes the situation when there is imminent danger, and refuses to go down until the mission is done.",
                "1. Protection Shield: Absorbs 10% of the net HP damage the second crew member received in the previous 2 turns, restoring 20% of their maximum HP. The Soldier then returns x4 the absorbed damage to the current threat.\n" +
                        "2. Iron Wall: Nullifies 30% of incoming damage to the Soldier for the next 4 turns and reflects 25% of each blocked hit back to the attacker.\n" +
                        "3. Suppressive Fire: Reduces all incoming threat attack by 15% for the current turn and next turn.\n" +
                        "4. Last Stand: For the next 4 turns, the Soldier cannot be knocked out and reflects 200% of all received damage back to the attacker. After Last Stand expires, the Soldier’s HP reduces by 30% the following turn.",
                "1. Security Breach: Iron Wall cannot be activated if the incoming hit deals 50% or more of the Soldier’s maximum HP in the current turn.\n" +
                        "2. Weakened Protection Shield: Each repeated use of Protection reduces its reflected damage output by 5% cumulatively (e.g., second use: -5%; third use: -10%).\n" +
                        "3. Stand-alone: Last Stand is only available when the Soldier received 50% or more damage to maximum HP.\n" +
                        "4. Fatally Fatigued: After Iron Wall’s effect expires, the ability becomes unavailable for the next three turns.",
                R.drawable.soldier));
    }

    private void setupUI() {
        recyclerView = findViewById(R.id.recycler_recruitment);
        searchBar = findViewById(R.id.search_bar);
        adapter = new RecruitmentAdapter(allRoles, this::onRoleSelected);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRoles(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterRoles(String query) {
        List<RecruitmentRole> filtered = allRoles.stream()
                .filter(r -> r.roleName.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        adapter.updateList(filtered);
    }

    private void onRoleSelected(RecruitmentRole role) {
        showNameInputDialog(role);
    }

    private void showNameInputDialog(RecruitmentRole role) {
        final EditText nameInput = new EditText(this);
        nameInput.setHint("Enter Character Name");
        int padding = (int) (24 * getResources().getDisplayMetrics().density);
        nameInput.setPadding(padding, padding, padding, padding);

        new AlertDialog.Builder(this)
                .setTitle("Recruit " + role.roleName)
                .setView(nameInput)
                .setPositiveButton("Recruit", (dialog, which) -> {
                    String name = nameInput.getText().toString().trim();
                    if (!name.isEmpty()) {
                        createCrewMember(role.roleName, name);
                    } else {
                        Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void createCrewMember(String role, String name) {
        CrewMember newMember = null;
        switch (role) {
            case "Pilot": newMember = new Pilot(name); break;
            case "Medic": newMember = new Medic(name); break;
            case "Engineer": newMember = new Engineer(name); break;
            case "Scientist": newMember = new Scientist(name); break;
            case "Soldier": newMember = new Soldier(name); break;
        }

        if (newMember != null) {
            CrewRepository.addCrewMember(newMember);
            CrewRepository.saveCrew(this);
            Toast.makeText(this, "Welcome aboard! " + name + " joined the crew as a " + role + "!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setupBottomNavigation() {
        findViewById(R.id.bottomNavControlRoom).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.bottomNavAddCrew).setOnClickListener(v -> {
            Toast.makeText(this, "You are already here", Toast.LENGTH_SHORT).show();
        });
    }

    private static class RecruitmentRole {
        String roleName;
        String description;
        String abilities;
        String weaknesses;
        int imageResId;

        RecruitmentRole(String roleName, String description, String abilities, String weaknesses, int imageResId) {
            this.roleName = roleName;
            this.description = description;
            this.abilities = abilities;
            this.weaknesses = weaknesses;
            this.imageResId = imageResId;
        }
    }

    private static class RecruitmentAdapter extends RecyclerView.Adapter<RecruitmentAdapter.ViewHolder> {
        private List<RecruitmentRole> roles;
        private final RoleClickListener listener;

        interface RoleClickListener {
            void onAddClick(RecruitmentRole role);
        }

        RecruitmentAdapter(List<RecruitmentRole> roles, RoleClickListener listener) {
            this.roles = new ArrayList<>(roles);
            this.listener = listener;
        }

        void updateList(List<RecruitmentRole> newList) {
            this.roles.clear();
            this.roles.addAll(newList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recruitment_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RecruitmentRole role = roles.get(position);
            holder.roleTitle.setText(role.roleName.toUpperCase());
            holder.charDesc.setText(role.description);
            holder.abilitiesText.setText(role.abilities);
            holder.weaknessesText.setText(role.weaknesses);
            holder.charImage.setImageResource(role.imageResId);
            holder.btnAdd.setOnClickListener(v -> listener.onAddClick(role));
        }

        @Override
        public int getItemCount() {
            return roles.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView roleTitle, charDesc, abilitiesText, weaknessesText;
            ImageView charImage;
            Button btnAdd;

            ViewHolder(View itemView) {
                super(itemView);
                roleTitle = itemView.findViewById(R.id.role_title);
                charDesc = itemView.findViewById(R.id.char_desc);
                abilitiesText = itemView.findViewById(R.id.abilities_text);
                weaknessesText = itemView.findViewById(R.id.weaknesses_text);
                charImage = itemView.findViewById(R.id.char_image);
                btnAdd = itemView.findViewById(R.id.btn_add_to_crew);
            }
        }
    }
}