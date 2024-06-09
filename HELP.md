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
 docker run --name laien-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=laien_info -e MYSQL_USER=user -e MYSQL_PASSWORD=password -p 3306:3306 -d mysql:latest

```

### build fat jar
```
./gradlew clean fatJar
```


### run / convert 
```
java -jar build/libs/K2Converter-0.0.2-SNAPSHOT.jar 
 
```

### import sql into mysql db

```
 
docker cp abda.sql laien-db:/
mysql -proot -uroot < abda.sql
```



# select Anwendungshinweise from Fertigarzneimittel table by Pharmazentralnummer
```
select Anwendungshinweise from FAM_L
LEFT JOIN PACFAM_L ON FAM_L.Key_FAM = PACFAM_L.Key_FAM
WHERE PACFAM_L.PZN = 1353;
;


select * from `FAM_L`
 LEFT JOIN `PACFAM_L` 
 ON  `FAM_L`.`Key_FAM` = `PACFAM_L`.`Key_FAM`
WHERE `PACFAM_L`.`PZN` = 1353;
;

```
