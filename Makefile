all: enigmatics_bingo_goals.zip enigmatics_bingo_goals_resources.zip

enigmatics_bingo_goals.zip: datapack
	(cd datapack && zip -FSr ../enigmatics_bingo_goals.zip ./)

enigmatics_bingo_goals_resources.zip: resourcepack
	(cd resourcepack && zip -FSr ../enigmatics_bingo_goals_resources.zip ./)

clean:
	rm *.zip
