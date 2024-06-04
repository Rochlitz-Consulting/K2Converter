# Getting Started

### Reference Documentation
For further reference, please consider the following sections:



### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

         mvn compile exec:java -Dexec.mainClass= org.rochlitz.K2Converter.K2Converter

# Testing

## import sql in example with docker container

### start mysql
bash ```
docker run --name laien-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=LaienInfo -e MYSQL_USER=dev -e MYSQL_PASSWORD=dev -p 3306:3306 -d mysql:latest
```
## convert 
bash ```
 mvn compile exec:java -Dexec.mainClass= org.rochlitz.K2Converter.K2Converter
```

### import sql
bash ```
 
docker cp abda.sql laien-db:/
mysql  -proot -uroot < abda.sql
```

