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

### convert 
```
export DB=LaienInfo
export SQL_FILE_PATH=abda.sql
./gradlew run --stacktrace
 
```

### import sql

```
 
docker cp abda.sql laien-db:/
mysql  -proot -uroot < abda.sql
```

