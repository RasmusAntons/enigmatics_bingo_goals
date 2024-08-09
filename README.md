# Enigmatics Bingo Goals

Alternative goals for [Bingo](https://modrinth.com/mod/bingo-mod), specifically for lockout mode. This mod works server-side only and requires the Bingo mod to be installed.

The goals are intended to be played with a [player tracker](https://modrinth.com/mod/playertracker/) and require a [patch to allow never-type goals in lockout mode](https://github.com/Gaming32/bingo/pull/13).

You can download the mod from [GitHub Releases](https://github.com/RasmusAntons/enigmatics_bingo_goals/releases/latest), or build it using the instructions below.

## Building the project

To build the project, run the following Gradle tasks:

* `buildBingoMod` (builds [Bingo with patches](https://github.com/RasmusAntons/bingo/tree/enigmatics-1.21))
* `runDatagen` (generates JSON definitions for custom Bingo goals)
* `build`

Required by Server & Client:\
Bingo mod in `libs` directory.

Required only by Server:\
Enigmatics Bingo Goals mod in `build/libs` directory.