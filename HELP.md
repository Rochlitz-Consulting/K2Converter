# Getting Started

### Reference Documentation
For further reference, please consider the following sections:



### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

         mvn compile exec:java -Dexec.mainClass= org.rochlitz.K2Converter.K2Converter

# Testing

## example for convert and import 

### start mysql
```
docker run --name laien-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=LaienInfo -e MYSQL_USER=dev -e MYSQL_PASSWORD=dev -p 3306:3306 -d mysql:latest
```

### build fat jar
```
./gradlew fatJar
```


### run / convert 
```
java -jar build/libs/K2Converter-0.0.2-SNAPSHOT.jar -d=laien_info -i=src/test/resources/ABDATA/ -o=laieninfo.sql
 
```

### import sql into mysql db

```
 
docker cp abda.sql laien-db:/
mysql  -proot -uroot < abda.sql
```



# select Anwendungshinweise from Fertigarzneimittel table by Pharmazentralnummer
```
select Anwendungshinweise from FAM_L
LEFT JOIN PACFAM_L ON FAM_L.Key_FAM = PACFAM_L.Key_FAM
WHERE PACFAM_L.PZN = 1353;
;
```
