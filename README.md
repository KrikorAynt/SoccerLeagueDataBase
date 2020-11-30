# Demo
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/W-AOJoLSy1c/0.jpg)](https://www.youtube.com/watch?v=W-AOJoLSy1c)

# SoccerLeagueDataBase
Java UI for working with SQL database for Soccer League

----
## For the Application to Run you need to connect to the Ryerson VPN:

You can use VPN from the following link: https://drive.google.com/drive/folders/1WmDneVZyp2KEyihlMhZqqj7Y2XSEGscA?usp=sharing

## To Compile:

First, edit the 'dbURL1' string in JdbcHelper.java to use your oracle db login information. Then:

**On UNIX environments:**
```
javac -cp ojdbc6.jar: JdbcHelper.java GuiPrototype.java
```
**On Windows:**
```
javac -cp ojdbc6.jar; JdbcHelper.java GuiPrototype.java
```

Then, to create jar file:
```
jar -cvfm SoccerLeagueDBMS.jar Manifest.txt *.class
```

## To Run:

Make sure ojdbc6.jar and all .txt files with queries are in the directory with the main jar file (SoccerLeagueDBMS.jar). Then:
```
java -jar SoccerLeagueDBMS.jar
```
