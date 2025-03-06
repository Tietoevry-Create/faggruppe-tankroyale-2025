![Tietoevry Tank Royale 2025!!](./images/087C215C-0F8B-4C1F-86E7-E3FAE3F3333B.JPEG)

# Faggruppe, Utvikling -- Tank Royale 2025 

## Introduction

[Tank Royale (Robocode)](https://robocode-dev.github.io/tank-royale/articles/intro.html) is a programming game where the goal is to code a bot in the form of a tank to compete against other bots in a virtual battle arena.

## Prerequisites

Robocode is running on a Java Runtime Environment (JRE) and needs Java 11 as a minimum. Run `java -version` to check your current Java version.

## Getting Started

In order for you to participate in the Tank Royale you need to do the following:

1. Clone this repository
2. Create a feature branch for your new bot
3. Create a new bot in the **tankroyale/competition-bots** subdirectory (or modify the existing ExampleBot, but make sure to rename the directory as well as all of the files)
4. Create a PR to the `main` branch containing __only__ the files in your bot directory

## Developing your bot

In order for your bot to actually do anything cool you need to make use of the [Bot API](https://robocode.sourceforge.io/docs/robocode/). You should start by exploring the `Robot` class first, which contains methods for moving, tourning your turret and firing bullets.


## Testing your bot locally

Once you have created a functioning bot, you can easily test it locally by starting the GUI application (tankroyale/robocode-tankroyale-gui-0.30.0.jar). Then you are able to start a local match by navigating to **Battle > Start battle** and doing the steps in the below screenshot.

![Testing your bot locally](images/running-locally.png)

Script for running the bot, i.e. a sh file (macOS and Linux) or cmd (Windows) file.
JSON config file that describes the bot, and specify which game types it was designed for.

Utvikling / Development
