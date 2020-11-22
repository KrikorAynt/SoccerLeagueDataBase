# SoccerLeagueDataBase
Java UI for working with SQL database for Soccer League

---

## To Run:

```
java -jar SoccerLeagueDBMS.jar
```

## To Compile:

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

