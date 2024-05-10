all: enigmatics_bingo_goals.zip

enigmatics_bingo_goals.zip: $(shell find datapack -type f)
	(cd datapack && zip -FSr ../enigmatics_bingo_goals.zip ./)

clean:
	rm enigmatics_bingo_goals.zip
