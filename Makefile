SHELL := bash
fabric_mod_version := 1.0.0
gradle_version := 8.7

all: enigmatics_bingo_goals.zip enigmatics_bingo_goals-${fabric_mod_version}.jar

enigmatics_bingo_goals.zip: $(shell find datapack -type f)
	(cd datapack && zip -FSr ../enigmatics_bingo_goals.zip ./)

fabric_mod/gradle/wrapper/gradle-wrapper.jar:
	curl -oL fabric_mod/gradle/wrapper/gradle-wrapper.jar https://services.gradle.org/distributions/gradle-${gradle_version}-bin.zip

.PHONY: clean enigmatics_bingo_goals-1.0.0.jar

enigmatics_bingo_goals-${fabric_mod_version}.jar: fabric_mod/gradle/wrapper/gradle-wrapper.jar
	(cd fabric_mod && ./gradlew build && cp build/libs/enigmaticsbingogoals-${fabric_mod_version}.jar ..)

clean: fabric_mod/gradle/wrapper/gradle-wrapper.jar
	rm -f enigmatics_bingo_goals.zip
	(cd fabric_mod && ./gradlew clean)
	rm -f enigmaticsbingogoals-${fabric_mod_version}.jar
