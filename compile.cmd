@echo off
setlocal enabledelayedexpansion

dir /s /B *.java > sources.txt

javac -cp "lib\gdx-backend-lwjgl-natives.jar;lib\gdx-backend-lwjgl.jar;lib\gdx-freetype-natives.jar;lib\gdx-freetype.jar;lib\gdx-natives.jar;lib\gdx-sources.jar;lib\gdx-tools.jar;lib\gdx.jar;lib\tween-engine-api-sources.jar;lib\tween-engine-api.jar" -d bin @sources.txt
del /f sources.txt
pause