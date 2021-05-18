.DEFAULT_GOAL := build-run

build-run: build run

run:
	java -jar target/simple_calculator-1.0-SNAPSHOT-jar-with-dependencies.jar

clean:
	rd /s/q target

build:
	mvn clean package

update:
	mvn versions:update-parent versions:update-properties versions:display-plugin-updates