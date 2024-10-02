#  Tutorial-Replay-Plugin

**Tutorial-Replay-Plugin** is a Bukkit/Spigot Minecraft plugin that enhances parkour maps by providing replay recording/playback and comprehensive tutorial management, allowing server administrators to create engaging and interactive parkour experiences.

## Features

- **Replay System:**
  - **Start Recording:** Capture player movements and actions.
  - **Stop Recording:** End the replay recording session.
  - **Play Replays:** Playback recorded replays for demonstration or review.

- **Tutorial Management:**
  - **Create Tutorials:** Set up custom tutorials with specific names.
  - **Delete Tutorials:** Remove existing tutorials.
  - **Add Components:** Integrate replays, start/end points, timers, and text messages into tutorials.
  - **Start Tutorials:** Execute tutorials with predefined sequences and actions.

- **Command Handling:**
  - Comprehensive commands to manage replays and tutorials seamlessly.

- **Player Management:**
  - Track and manage player states during tutorials and replays.

## Configuration

Customize the plugin by editing the configuration files located in `plugins/TutorialFiles/`. Adjust settings related to replays, tutorials, and other gameplay mechanics as needed.

## Commands

### Replay Commands

- **Start Recording:**
  ```
  /replay start <filename>
  ```
  Begins recording a replay with the specified filename.

- **Stop Recording:**
  ```
  /replay stop <filename>
  ```
  Ends the replay recording session.

- **Play Replay:**
  ```
  /replay play <filename>
  ```
  Plays back the specified replay.

### Tutorial Commands

- **Create Tutorial:**
  ```
  /tutorial create <tutorialName>
  ```
  Creates a new tutorial with the given name.

- **Delete Tutorial:**
  ```
  /tutorial delete <tutorialName>
  ```
  Deletes the specified tutorial.

- **Add Replay to Tutorial:**
  ```
  /tutorial add replay <tutorialName> <replayName>
  ```

- **Add Start Point:**
  ```
  /tutorial add start <tutorialName>
  ```

- **Add End Point:**
  ```
  /tutorial add end <tutorialName>
  ```

- **Add Timer:**
  ```
  /tutorial add timer <tutorialName> <timerName>
  ```

- **Add Text Message:**
  ```
  /tutorial add text <tutorialName> <number> "<text>" <duration>
  ```

- **Change Text Message:**
  ```
  /tutorial change text <tutorialName> <number> "<text>" <duration>
  ```

- **Start Tutorial:**
  ```
  /tutorial start <tutorialName>
  ```
  Initiates the specified tutorial.

## Usage

Once installed and configured, use the `/replay` and `/tutorial` commands to manage replays and tutorials:

1. **Recording a Replay:**
   - Start recording with `/replay start <filename>`.
   - Perform the desired actions in-game.
   - Stop recording with `/replay stop <filename>`.

2. **Playing a Replay:**
   - Execute `/replay play <filename>` to watch the recorded session.

3. **Managing Tutorials:**
   - Create a tutorial using `/tutorial create <tutorialName>`.
   - Add components like replays, start/end points, timers, and text messages.
   - Start the tutorial with `/tutorial start <tutorialName>` to guide players through the parkour map.

## Permissions

Integrate with your server’s permission system to manage access to commands and administrative controls:

- **Admin Permissions:**
  - Required to execute replay and tutorial commands.
  - Example permissions:
    - `parkour.replay`
    - `parkour.tutorial`

Customize permissions as needed to control who can manage replays and tutorials on your server.
