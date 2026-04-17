# Project Princess 010 🦋
Group Name: 010

Member(s): Muhasana Muskan (002275684) and Suthinee Segkhoonthod (000371030)

<br>

## Welcome Aboard to Project Princess 010!
Project Princess 010 has successfully come to fruition: people can now enjoy a fun cruise exploring space! The crew members of Project Princess 010 are tasked with ensuring and maintaining the well-being of the passengers and a safe journey through space. However, they often find themselves in dangerous situations, from maneuvering through an asteroid field to avoiding incoming solar flares. Players must ensure that threats are eliminated so that the passengers of Project Princess 010 have a safe, wonderful journey as they experience space exploration.

> Claude and Gemini were used in this assignment to explain and help fix coding errors and encountered bugs.

<br>

## Table of Contents
* [Game Features](#game-features)
* [UML Diagram](#uml-diagram)
* [UI Wireframes](#ui-wireframes)
* [Utilized Tools](#utilized-tools)
* [Demonstration Video](#demonstration-video)
* [Installation](#installation)
* [Missions of Project Princess 010](#missions-of-project-princess-010)
* [Meet the Crew](#meet-the-crew)
* [Completing a Mission](#completing-a-mission)
* [Gaining XP and Leveling Up](#gaining-xp-and-leveling-up)
* [Implemented Features and Points Table](#implemented-features-and-points-table)

<br>

## Game Features
### Home Page 🏠
The Home page displays the user's crew members in a Card Layout. Each card contains the crew member's given name (which can be renamed by the user himself/herself), current level, current XP, and number of successfully completed missions. To change a character's name, the user presses on the name string. Once clicked, a keyboard will pop up. After modifying the character's name, the user clicks "Enter" on the keyboard and the game will automatically update the newly modified input data. 

Another primary feature of the Home Page is the Mission Control section. There, the user can select a mission task he/she wishes to complete. For example, if the user selects the mission "Fly Through the Uncharted Asteroid Field," the application will move forward to the Mission Control Page, where details about the task are stated and the user selects two characters to complete the mission.

<br>

### Mission Control Page 🔭
A crew member increases their XP by completing a mission with another selected character. In the Mission Control page, the application displays details about the task selected in the Home page. If the user wishes to play the game, he/she must select two crew members. Once ready, the user clicks on the "Start Mission" button, which will direct the user to the Mission Battle Page. If the user prefers a different mission, he/she may click on the "Control Room" icon in the bottom navigation bar. The application then directs the user back to the Home page.

<br>

### Mission Battle Page 🚀
Attacks and damages take place in this page. Underneath both characters, the health bars are displayed. Additionally, a box containing various attack and defense options is shown for the user to select for the crew members. The application will switch between two characters, allowing the user to decide how to fight against the threats presented in the mission. 

If a crew member's health bar reaches 0, the system will automatically disable the character from its continuance in the game, therefore leaving only one playable crew member for the user to play with in completing the task. If the second crew member's health bar also reaches 0, the application will automatically end the game before showing a notification with a preexisting UI message. If the user, however, does successfully complete the mission, then the application will display a congratulatory message. 

<br>

### Add New Crew Member Page 🧑‍🚀
Users can add a new crew member to their collection. After pressing on the "Add New Crew Member" icon in the bottom navigation bar, the application navigates the user to a new screen featuring a search bar and a full list of available crew members with implementation of a RecyclerView Layout. Details about the crew members are visible on the card for the user to read (e.g., characteristics, attributes, powers, and weaknesses). 

To add an additional character, the user may press the Add to Your Crew button, which is located at the bottom of the card. A text box will appear for the user to set the newly added crew member's name. If the user no longer wishes to add an additional crew member, he/she click on the "Control Room" icon in the bottom navigation bar. The application then directs the user back to the Home page.

<br>

## UML Diagram
![](uml_diagram.png)

<br>

## UI Wireframes
### Preliminary UI Wireframe Designs
![](ui%20wireframes/initial_designs.jpg)

### Final UI Wireframe Designs
| Home Page | Mission Control Page | Mission Battle Page | Add New Crew Member Page |
| :---: | :---: | :---: | :---: |
| ![](ui%20wireframes/home_page.jpg) | ![](ui%20wireframes/mission_control_page.jpg) | ![](ui%20wireframes/battle_page.jpg) | ![](ui%20wireframes/add_new_crew_member_page.jpg) |

<br>

## Utilized Tools
* FigJam: Developing the application's UML diagram
* Procreate: Sketching in the initial UI wireframes and designing the application's game assets
* Figma: Designing the application's UI wireframes
* Android Studio: Serving as the application's development IDE
* Groovy DSL and Java: Primary build configuration on Android Studio and coding language
* Claude and Gemini: Additional assistance tool for providing coding issues elucidation (e.g., inappropriate functionality implementation and build configuration errors)

<br>

## Demonstration Video
Link: https://drive.google.com/file/d/1e7e3sBgeVwfMVCm4vxR54ThkKJGkUAqv/view?usp=sharing

<br>

## Installation
### Prerequisites 
1. Installation of Android Studio
2. Proficient knowledge in Java

<br>

### Loading Project Princess 010
To install and play Project Princess 010, download this repository as a ZIP file. Once downloaded, open the ZIP file. On Android Studio, click "Open" and select the downloaded project folder. Once the application is set up on the IDE, enjoy completing missions of Project Princess 010 to continue the amazing journey through space!

<br>

## Missions of Project Princess 010
### Mission 1: Fly Through the Uncharted Asteroid Field 🧭
An uncharted asteroid field has suddenly appeared directly in the cruise’s path. However, the navigational system is completely offline. Your mission is to guide the ship through the field manually, dodging debris and avoiding collisions while they reboot the system.

<br>

### Mission 2: Reactor Core Meltdown 💥
Project Princess 010’s core is overheating and may soon spiral towards critical failure. Your mission is to stabilize the core by engaging in emergency cooling protocols and mitigate the situation before the core breaches containment. 

<br>

### Mission 3: Oxygen System Failure 🩺
Life support units are failing one by one across the ship. While CO₂ levels are climbing gradually, the crew and passengers will experience rapid critical health deterioration if it reaches the upper deck. Your mission is to repair the units before the CO₂ levels become life-threatening.

<br>

### Mission 4: Mysterious Illness Outbreak 🦠
Following a visit to a recently visited planet, passengers are falling ill. After examining patients in the isolation pods, it is revealed that the discovered pathogen is unlike anything in the fleet’s medical database. Your mission is to contain the outbreak before it spreads ship wide. Remember, the isolation pods cannot hold forever.

<br>

### Mission 5: Breach in an Observation Deck 🔭
A micrometeorite has punched through the hull of an observation deck, leaving a gaping hole open to the void. Air pressure is bleeding fast, and the clock is already running. Your mission is to seal the breach and restore pressure before oxygen loss becomes irreversible. 

<br>

## Meet the Crew
### Pilot 🚀
<p align="center">
  <img src="game%20assets/crew_members/pilot.png" width="400">
</p>
<p align="center">
  <i>Razor-focused and cool under pressure, the Pilot is the brain of any mission. The Pilot reads incoming threats, making split-second calls to keep everyone safe while simultaneously mapping escape routes in the case the worst comes to pass.</i>
</p>
<table>
  <thead>
    <tr>
      <th colspan="2">Powers and Abilities</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Evasive Maneuver</b></td>
      <td>Grants the second crew member a dodge buff for the next 4 turn, reducing their incoming damage by 15%.</td>
    </tr>
    <tr>
      <td><b>Slipstream Draft</b></td>
      <td>Nullifies 30% of damage the Pilot receives this turn and the following 3 turns.</td>
    </tr>
    <tr>
      <td><b>Navigation Lock</b></td>
      <td>Reduces the current threat’s outgoing damage by 20% and raises the second crew member’s attack by 25% for the next 2 turns.</td>
    </tr>
    <tr>
      <td><b>Emergency Burn</b></td>
      <td>Nullifies 50% of the net HP damage the second crew member absorbed in the previous 4 turns.</td>
    </tr>
  </tbody>
  <thead>
    <tr>
      <th colspan="2">Weaknesses</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Exposed Cockpit</b></td>
      <td>Takes 15% additional damage whenever targeted directly.</td>
    </tr>
    <tr>
      <td><b>Automatic Control Loss</b></td>
      <td>Emergency Burn becomes unusable if the second crew member is incapacitated.</td>
    </tr>
    <tr>
      <td><b>Motion Sickness</b></td>
      <td>If the Pilot takes 45% or more damage to their max HP in a single turn, Navigation Lock is cancelled and becomes unusable that turn.</td>
    </tr>
    <tr>
      <td><b>Tunnel Vision</b></td>
      <td>After using Evasive Maneuver or Slipstream Draft, the Pilot’s own defense decreases by 10% the following turn.</td>
    </tr>
  </tbody>
</table>

<br>

### Medic 💉
<p align="center">
  <img src="game%20assets/crew_members/medic.png" width="400">
</p>
<p align="center">
  <i>Equal parts scientist and caregiver, the Medic keeps the crew mission-ready and the passengers safe and sound. The Medic, alongside with the Scientist, synthesizes treatments on the fly, reverses the worst conditions imaginable, and can push the crew members past their normal limits in when the situation demands it.</i>
</p>
<table>
  <thead>
    <tr>
      <th colspan="2">Powers and Abilities</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Field Triage</b></td>
      <td>Reverses 40% of damage the target received in the previous 2 turns.</td>
    </tr>
    <tr>
      <td><b>Antidote Protocol</b></td>
      <td>Reduces incoming damage to the second crew member by 20 next turn and reflects x2 that reduced amount back to the threat.</td>
    </tr>
    <tr>
      <td><b>Adrenaline Shot</b></td>
      <td>Sacrifices 15% of the Medic’s current HP to boost both crew members’ attack damage by 40% for the next 4 turns.</td>
    </tr>
    <tr>
      <td><b>Emergency Revival</b></td>
      <td>Revives the second crew member at full HP. Each subsequent use to restore HP reduces by 10% (e.g. second use: 90%; third use: 80%).</td>
    </tr>
  </tbody>
  <thead>
    <tr>
      <th colspan="2">Weaknesses</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Everyone’s Healer</b></td>
      <td>When receiving healing, it is capped at 50% of their maximum HP.</td>
    </tr>
    <tr>
      <td><b>Supply Limited</b></td>
      <td>After any ability is used, that same ability is unavailable for the next 3 turns.</td>
    </tr>
    <tr>
      <td><b>Priority Target</b></td>
      <td>Threats increase their attack damage against the Medic by 5% with each passing turn, regardless of actions taken.</td>
    </tr>
    <tr>
      <td><b>Contamination Risk</b></td>
      <td>The Medic takes an additional 10% damage per turn from all sources as the mission progresses, stacking cumulatively.</td>
    </tr>
  </tbody>
</table>

<br>

### Engineer ⚙️
<p align="center">
  <img src="game%20assets/crew_members/engineer.png" width="400">
</p>
<p align="center">
  <i>Resourceful, methodical, and relentlessly efficient, the Engineer is the fleet’s problem-solver. The Engineer improvises solutions in unprecedented circumstances while keeping every crewmate and passenger as safe as possible.</i>
</p>
<table>
  <thead>
    <tr>
      <th colspan="2">Powers and Abilities</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Overload Circuit</b></td>
      <td>Increases the second crew member’s attack effectiveness by 200% for their next 2 turns.</td>
    </tr>
    <tr>
      <td><b>EMP Pulse</b></td>
      <td>Stuns the current threat, reducing the damage they deal by 15% for the next 2 turns.</td>
    </tr>
    <tr>
      <td><b>Reinforce Armor</b></td>
      <td>Deploys a barrier on both crew members, absorbing 10% of the next 3 hits each.</td>
    </tr>
    <tr>
      <td><b>System Reroute</b></td>
      <td>Redirects the active threat’s debuff effect away from the Engineer into the mission environment, reducing the threat’s next attack by 20%.</td>
    </tr>
  </tbody>
  <thead>
    <tr>
      <th colspan="2">Weaknesses</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Overheat</b></td>
      <td>Each use of Overload Circuit costs the Engineer 10% of their current HP immediately.</td>
    </tr>
    <tr>
      <td><b>Heavy Gear</b></td>
      <td>After using EMP Pulse, the Engineer receives 10% additional damage from all sources the next turn.</td>
    </tr>
    <tr>
      <td><b>Technical Difficulties</b></td>
      <td>After deploying Reinforce Armor, the ability is unavailable for the next 3 turns.</td>
    </tr>
    <tr>
      <td><b>System Reroute Failure</b></td>
      <td>If there is no active incoming debuff to redirect, the Engineer loses their turn and takes 10% additional damage for the next 2 turns.</td>
    </tr>
  </tbody>
</table>

<br>

### Scientist ⚗️
<p align="center">
  <img src="game%20assets/crew_members/scientist.png" width="400">
</p>
<p align="center">
  <i>Dangerous, brilliant, and calculative, the Scientist weaponizes knowledge itself. The Scientist analyzes threats mid-battle, exploits every exposed vulnerability, and delivers devastating blows that bypass conventional defenses.</i>
</p>
<table>
  <thead>
    <tr>
      <th colspan="2">Powers and Abilities</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Exploited Vulnerability</b></td>
      <td>Multiplies the damage the second crew member dealt in the previous turn by x5, applying it to the current attack (e.g., partner dealt 100 ⟶ 500 bonus damage to threat).</td>
    </tr>
    <tr>
      <td><b>Chemical Catalyst</b></td>
      <td>Removes 15% of damage received in the previous 2 turns and nullifies the same amount in the next 2 turns.</td>
    </tr>
    <tr>
      <td><b>Plasma Burst</b></td>
      <td>Takes 20% of damage the second crew member received last turn (after mitigation and reflection) and deals it as bonus damage to the current attack.</td>
    </tr>
    <tr>
      <td><b>Chain Reaction</b></td>
      <td>Reduces all threat attack damage by 10% for per turn, stacking cumulatively for up to 4 turns. Stacks persist for the remainder of the mission once applied.</td>
    </tr>
  </tbody>
  <thead>
    <tr>
      <th colspan="2">Weaknesses</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Volatile Reaction</b></td>
      <td>After using Exploited Vulnerability, the ability is unavailable for the next 3 turns.</td>
    </tr>
    <tr>
      <td><b>Miscalculated</b></td>
      <td>Triggers only when the second crew member receives zero net HP damage and deals zero damage last turn. Plasma Burst fails and the Scientist takes an additional 15% of that turn’s incoming attack. Reflection counts as dealing damage and prevents this trigger.</td>
    </tr>
    <tr>
      <td><b>Weak Linkage</b></td>
      <td>Exploited Vulnerability and Plasma Burst become unusable while the second crew member is incapacitated.</td>
    </tr>
    <tr>
      <td><b>Chemical Burn</b></td>
      <td>After using Chemical Catalyst, the ability is locked for the next 2 turns.</td>
    </tr>
  </tbody>
</table>

<br>

### Soldier 🛡️
<p align="center">
  <img src="game%20assets/crew_members/soldier.png" width="400">
</p>
<p align="center">
  <i>Built for the front line, the Soldier stands between every incoming threat and the rest of the crew. The Soldier absorbs damages no one else could survive, stabilizes the situation when there is imminent danger, and refuses to go down until the mission is done.</i>
</p>
<table>
  <thead>
    <tr>
      <th colspan="2">Powers and Abilities</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Protection Shield</b></td>
      <td>Absorbs 10% of the net HP damage the second crew member received in the previous 2 turns, restoring 20% of their maximum HP. The Soldier then returns x4 the absorbed damage to the current threat.</td>
    </tr>
    <tr>
      <td><b>Iron Wall</b></td>
      <td>Nullifies 30% of incoming damage to the Soldier for the next 4 turns and reflects 25% of each blocked hit back to the attacker.</td>
    </tr>
    <tr>
      <td><b>Suppressive Fire</b></td>
      <td>Reduces all incoming threat attack by 15% for the current turn and next turn.</td>
    </tr>
    <tr>
      <td><b>Last Stand</b></td>
      <td>For the next 4 turns, the Soldier cannot be knocked out and reflects 200% of all received damage back to the attacker. After Last Stand expires, the Soldier’s HP reduces by 30% the following turn.</td>
    </tr>
  </tbody>
  <thead>
    <tr>
      <th colspan="2">Weaknesses</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Security Breach</b></td>
      <td>Iron Wall cannot be activated if the incoming hit deals 50% or more of the Soldier’s maximum HP in the current turn.</td>
    </tr>
    <tr>
      <td><b>Weakened Protection Shield</b></td>
      <td>Each repeated use of Protection reduces its reflected damage output by 5% cumulatively (e.g., second use: -5%; third use: -10%).</td>
    </tr>
    <tr>
      <td><b>Stand-alone</b></td>
      <td>Last Stand is only available when the Soldier received 50% or more damage to maximum HP.</td>
    </tr>
    <tr>
      <td><b>Fatally Fatigued</b></td>
      <td>After Iron Wall’s effect expires, the ability becomes unavailable for the next three turns.</td>
    </tr>
  </tbody>
</table> 

<br>

## Completing a Mission
### Initiating the Mission 🌟
The user selects one of the five missions available to complete in the game Project Princess 010:
1. Fly Through the Uncharted Asteroid Field,
2.	Reactor Core Meltdown,
3.	Oxygen System Failure, 
4.	Mysterious Illness Outbreak, and 
5.	Breach in an Observation Deck.

After clicking on the desired mission, the application navigates the user from the Control Room page (the application’s Home page) to the Mission Control Page. In the Mission Control Page, the mission’s assignment is detailed in the Mission Control sub-section. For example, if the user selected Mission 4: Mysterious Illness Outbreak, the application returns its task description: 
> “Following a visit to a recently visited planet, passengers are falling ill. After examining patients in the isolation pods, it is revealed that the discovered pathogen is unlike anything in the fleet’s medical database. Your mission is to contain the outbreak before it spreads ship wide. Remember, the isolation pods cannot hold forever.”

Underneath the Mission Control sub-section, the user must select two of their crew members. The possible pairings are: 
1.	Pilot and Pilot, 
2.	Pilot and Medic, 
3.	Pilot and Engineer, 
4.	Pilot and Scientist, 
5.	Pilot and Soldier, 
6.	Medic and Medic, 
7.	Medic and Engineer, 
8.	Medic and Scientist, 
9.	Medic and Soldier, 
10.	Engineer and Engineer, 
11.	Engineer and Scientist, 
12.	Engineer and Soldier, 
13.	Scientist and Scientist, 
14.	Scientist and Soldier, and 
15.	Soldier and Soldier.

After choosing two crew members, the user presses the “Start Mission” button, located beneath the Choose Your Fighters section. The application prepares the Mission Battle page. Once ready, it will transition to the Mission Battle page. 

<br>

### Mission Battle Structure and Logic 🕹
Initiating the mission, the application first generates a threat and directs it to Character A. Once damage is resolved, the application prints one of the preexisting UI messages. In this hypothetical scenario, the user has chosen a Medic and a Pilot to complete the fourth mission. After generating the threat to the Medic character, the application will print the following UI message: 
> “Passengers in the quarantine area are deteriorating faster than you can treat them. (Threat HP X/X) (Character’s name) takes (number) damage. Choose your next move to neutralize the threat!”

The game console, located in the bottom part of the screen, then displays Character A's abilities. In this hypothetical case, it prints the Medic’s powers: (1) Field Triage, (2) Antidote Protocol, (3) Adrenaline Shot, and (4) Emergency Revival. After selecting the character’s next move, the application prints out the following UI message: 
> “(Character’s name) deploys (Power’s name) to neutralize the threat!”

The system then generates a threat that attacks Character B (in this example use case, the Pilot). Once damage is resolved, it prints out the following message: 
> “Infected crew members are no longer fit for duty, and failing automated systems are surging through the flight controls. (Threat HP X/X) (Character’s name) takes (number) damage. Choose your next move to neutralize the threat!”

The game console then displays Character B's abilities. In this hypothetical case, it prints the Pilot’s powers: (1) Evasive Maneuver, (2) Slipstream Draft, (3) Navigation Lock, and (4) Emergency Burn. After selecting the character’s next move, the application prints the following UI message: 
> “(Character’s name) deploys (Power’s name) to neutralize the threat!”

<br>

### Incapacitation and Solo Play 🪐
The system switches turns in generating threats and attacking the characters. If one of the characters’ HP reaches 0 and cannot be revived (i.e., revival is unavailable), the application prints the following UI message:
> “(Character’s name) has succumbed to the damages and requires immediate medical attention. (Character’s name) must now complete the mission alone. Good luck!”

The game then resumes with the remaining playable character until either the mission’s assignment is complete (i.e., the threat reaches 0) or the second crew member’s HP reaches 0. 

<br>

If the mission is successfully completed, the application will print the following UI message: 
> “Mission accomplished! You have successfully neutralized the threat and kept the passengers safe. Project Princess 010 can now resume her journey through the stars. Returning to the control room…”

However, in the case where the user does not successfully complete the mission:
> “Critical failure! Initiate Recovery Protocol 67: locate the nearest habitable planet immediately for recovery. Any found repairs will be completed there before Project Princess 010 can continue her voyage through the stars. Returning to the control room…”

The application returns the user to the Mission Control page, where the user can complete the mission again with either the same characters or different crew members. 

<br>

## Gaining XP and Leveling Up
When the user adds a new crew member to the fleet, the character starts at Level 1 with 0 XP. Characters increases their XP by completing missions. XP is determined by each character’s status at mission end, not by events occurring during battle. 
<table>
  <thead>
    <tr>
      <th>Scenario</th>
      <th>XP Earned</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Both crew members survive and mission is successfully completed</td>
      <td>Each crew member gains 10XP.</td>
    </tr>
    <tr>
      <td>A crew member was revived mid-game and survives to completion</td>
      <td>They gain 10 XP.</td>
    </tr>
    <tr>
      <td>A crew member is incapacitated (unrevivable) and their partner solos to success</td>
      <td>The incapacitated crew member gains 5XP; the solo player gains 15XP.</td>
    </tr>
    <tr>
      <td>A crew member is incapacitated (unrevivable) but remaining partner fails the mission</td>
      <td>Both crew members gain 0XP.</td>
    </tr>
    <tr>
      <td>Both crew members are incapacitated and the mission fails</td>
      <td>Both crew members gain 0XP.</td>
    </tr>
  </tbody>
</table>
NOTE: The 5XP reward for incapacitation only applies when the second crew member successfully solos the mission to completion. If the mission fails for any reason, no XP is awarded to either crew member.

<br>A crew member’s level increases by the following formula: 

<p align="center">
  <i>Total XP Required to Reach Level N = Cummulative Total XP + (N x 10)</i>
</p>

To advance from Level 1 to Level 2, a character needs 20XP. To advance to Level 3, the character must earn an additional 30XP, and so on. XP accumulates across missions and does not reset upon leveling up. For example: 

To advance a Level 4 Medic to a Level 5 Medic: 

<p align="center">
  <i>90XP (Cummulative Total XP) + 50XP (to reach Level 5) = 140XP Total Required</i>
</p>

The mission’s difficulty and threat damage levels increase based on each character’s current level. If two crew members who were selected to complete the mission have varying levels, threat scaling is based on the average of both characters’ levels, rounded down. For example, A Level 3 Medic paired with a Level 1 Pilot produces an average of threat level 2. 

<br>

## Implemented Features and Points Table
<table>
  <thead>
    <tr>
      <th>Feature</th>
      <th>Points</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Mandatory Requirements</td>
      <td>13</td>
    </tr>
    <tr>
      <td>RecyclerView</td>
      <td>1</td>
    </tr>
    <tr>
      <td>Crew Images</td>
      <td>1</td>
    </tr>
    <tr>
      <td>Mission Visualization</td>
      <td>2</td>
    </tr>
    <tr>
      <td>Tactical Combat</td>
      <td>2</td>
    </tr>
    <tr>
      <td>Statistics</td>
      <td>1</td>
    </tr>
    <tr>
      <td>Fragments</td>
      <td>2</td>
    </tr>
    <tr>
      <td>Data Storage and Loading</td>
      <td>2</td>
    </tr>
     <tr>
      <td>Statistics Visualization</td>
      <td>2</td>
    </tr>
     <tr>
      <td><b>Total points</b></td>
      <td><b>26</b></td>
    </tr>
  </tbody>
</table>
