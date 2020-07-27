# csvsql

This is a Java application that allows users to run SQL queries on CSV files

# How to run?

```
csvql/csvsql$ gradle jar

> Task :compileJava
Note: Some input files use unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.

BUILD SUCCESSFUL in 3s
2 actionable tasks: 2 executed
csvql/csvsql$ ls
build  build.gradle  gradle  gradlew  gradlew.bat  local.properties  README.md  settings.gradle  src  test-output.vim
csvql/csvsql$ ls build/libs/
csvql-1.0-SNAPSHOT.jar
csvql/csvsql$ java -jar build/libs/csvql-1.0-SNAPSHOT.jar
csvsql > select country, city from src/test/data/mycsv2.csv
Task 'ParseCommand' completed in 9 milli seconds.


city    state   country
------------------------------------------------
 INDIA  Nellore
 INDIA  Hyderabad
 INDIA  Chennai
 INDIA  Bengaluru
 INDIA  Trivandrum
United States of America        Bellevue
 United States of America       Las Vegas
Rows processed: 7.
Task 'StreamData' completed in 24 milli seconds.
csvsql > ^C
```

## Features

I'm starting with simple `SELECT` statements, run on one CSV file (no joins as of yet).  

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

