# Enigmatics Bingo Goals

Alternative goals for [Bingo](https://modrinth.com/mod/bingo-mod), specifically for lockout mode. Both the datapack and fabric mod are server-side only.

The goals are intended to be played with a [player tracker](https://modrinth.com/mod/playertracker/) and require a [patch to allow never-type goals in lockout mode](https://github.com/Gaming32/bingo/pull/13).

## Usage

Most of the goals only require the datapack. Run `make enigmatics_bingo_goals.zip` or zip the `datapack` directory by hand and copy the resulting zip to the `world/datapacks`.

Some goals (play_music_to_other_team, wear_pumpkin, full_unique_inventory) require the fabric mod to run. Run `make all` or build the mod with gradle and copy it into the mods directory.
