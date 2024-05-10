spreadplayers 0 0 1000 1000 true @a
execute at @a run spawnpoint @p ~ ~ ~
bingo start --gamemode lockout --difficulty bingo:hard --allow-never-goals-in-lockout --player-tracker --require-client green aqua
effect give @a minecraft:slowness 120 255 true
effect give @a minecraft:jump_boost 120 255 true
effect give @a minecraft:blindness 120 255 true
effect give @a minecraft:mining_fatigue 120 255 true
effect give @a minecraft:weakness 120 255 true
effect give @a minecraft:saturation 120 255 true
clear @a
time set day
gamerule doDaylightCycle true
gamerule doWeatherCycle true
