find . | grep ".java" > sources.txt

javac -cp "lib/*" -d bin @sources.txt
rm sources.txt