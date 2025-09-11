
<p align="center">
  <a href="https://youtu.be/I741sSjlwh0?si=DiBZN-41UxAiezoA" target="_blank" rel="noreferrer"><img src="https://i.imgur.com/iJnsQco.png" alt="Flappy Bird (Clone)"></a>
</p>


**[Flappy Bird _(Clone)_](https://youtu.be/I741sSjlwh0?si=DiBZN-41UxAiezoA)** is a faithful Java recreation of the classic, where players tap to keep their bird airborne, navigating it through a series of challenging pipes. One wrong move means a crash, so <u>timing is key</u>! Rack up high scores to earn medals and push yourself to beat your personal best in this addictive, retro-style game.

<div style="width: 100%; display: flex; justify-content: space-between;">
  
  <div style="width: 48%; text-align: center;">
    <p align="center">
      <img src="https://i.imgur.com/3l2189u.gif" alt="Daytime Menu" width="45%">
      <img src="https://i.imgur.com/w2xREia.gif" alt="Nighttime Menu" width="45%">
    </p>
  </div>
</div>

<div style="width: 100%; display: flex; justify-content: space-between;">

## ⚙️ Technologies Used
- **Java SDK 22**: Core programming language and development kit  
- **Swing/AWT**: Used for GUI components and graphics rendering  
- **Custom Game Engine**: Built from scratch with game state management  
- **Thread-based Game Loop**: Maintains consistent 60 FPS gameplay  
- **BufferedImage**: Handles sprite loading and image manipulation 

## ✨Key Features 

### 1. Game Loop System

Maintains smooth 60 FPS gameplay with precise timing and frame management.  
&nbsp;
**How it works**: A dedicated thread continuously updates game state and renders frames at consistent intervals.
```java
@Override  
public void run() {  
  double timePerFrame = 1000000000.0 / 60;  
  long lastFrame = System.nanoTime();  
  long now;  
  
  while(true) {  
	  now = System.nanoTime();  
	  if(now - lastFrame >= timePerFrame) {  
		  update();  
		  repaint();  
		  lastFrame = now;  
	  } 
    }
 }
 ```
### 2. Smooth Graphics Rendering

Double-buffered rendering system prevents screen tearing and ensures fluid animation.  
&nbsp;
**How it works**: Draws game elements to an off-screen buffer before displaying them on screen.
```java
@Override  
protected void paintComponent(Graphics g) {  
 if(scene == null) {  
	 scene = createImage(GAME_WIDTH, GAME_HEIGHT);  
	 pen = scene.getGraphics();  
 }  
 pen.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);  
 if(getCurrentState() != null) getCurrentState().draw(pen);  
 g.drawImage(scene, 0, 0, this);  
}
 ```
### 3. Precise Collision Detection

Accurate hitbox-based collision system for interactions between bird and obstacles.  
&nbsp;
**How it works**: Uses rectangle-based hitboxes with precise overlap detection.
```java
public boolean overlaps(Hitbox hb) {  
  return (x <= hb.x + hb.width)  &&  
         (x + width >= hb.x)     &&  
         (y <= hb.y + hb.height) &&  
         (y + height >= hb.y);  
}
 ```
### 4. Realistic Physics System

Smooth gravity-based movement with responsive jump mechanics.  
&nbsp;
**How it works**: Implements physics calculations for bird movement with configurable parameters.
```java
public void move() {  
  if(dead)return;  
  vy += GRAVITY;  
  y += vy;  
  y = Math.max(y, 0);  
  // ...death logic...
}
 ```
### 5. Dynamic Bird Colors

Randomized bird colors with smooth animation system.  
&nbsp;
**How it works**: Randomly selects between yellow, red, or blue bird sprites with fluid wing animations every round!
```java
private String getBirdColor() {  
  switch (ThreadLocalRandom.current().nextInt(3) + 1) {  
  default -> { return "yellow"; }  
  case 2 -> { return "red"; }  
  case 3 -> { return "blue"; }  
  }
}
 ```

  <div style="width: 48%; text-align: center;">
    <p align="center">
      <img src="https://i.imgur.com/qXD27ac.png" alt="Different Bird Colors" width="85%">
    </p>
  </div>
  
 ### 6. Medal System

Tiered achievement system that rewards player skill. Awarded on scores of 10+, 20+, 30+, and 40+ points, respectively.  
&nbsp;
**How it works**: Awards increasingly valuable medals based on score thresholds, with visual feedback.
```java
public enum Medal {  
  
 BRONZE, SILVER, GOLD, PLATINUM;  
  
 private final int minimumScore;  
  
 Medal() {    
  minimumScore = (ordinal() + 1) * 10;  
 }  

public static Medal getMedal(int score) {  
 Medal medal = null;  
 for(Medal m : values()) {  
  if(score >= m.getMinimumScore())  
	  medal = m;  
 }  
 return medal;  
}  
 ```

  <div style="width: 48%; text-align: center;">
    <p align="center">
      <img src="https://i.imgur.com/fTSeljf.png" alt="Score Medal Types" width="85%">
    </p>
    
<br><Br>
## ⚠️ Disclaimer
<p align="center">Graphical and auditory assets used in this clone are owned by DOTGEARS (.GEARS) Studios © 2011 - 2024.<br>This project is created for <b>educational and entertainment purposes only</b> & has no affiliation with the original game.</p>


