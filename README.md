# K2Converter

# Overview
K2Converter is a Java-based project that uses Apache Camel for routing and processing data. It is designed to convert data from one format to another, specifically from ASCII to SQL. The project uses Gradle as a build tool.  

# Getting Started
# Prerequisites
- Java 21
- Gradle 
- Apache Camel 4.6.0
- Logging SLF4J

## provided gradle

Gradle 8.5
------------------------------------------------------------
- Build time:   2023-11-29 14:08:57 UTC
- Revision:     28aca86a7180baa17117e0e5ba01d8ea9feca598

- Kotlin:       1.9.20
- Groovy:       3.0.17
- Ant:          Apache Ant(TM) version 1.10.13 compiled on January 4 2023
- JVM:          21.0.2 (Private Build 21.0.2+13-Ubuntu-122.04.1)
- OS:           Linux 6.5.0-35-generic amd64



# Building the Project
To build the project, navigate to the project directory and run the following command:


# Configuration
## Environment Variables 

| Environment Variable Name |                      Description                      |
|---------------------------|:-----------------------------------------------------:|
| DB                        | DB name or schema name (Output)  . E.g. laien_info_db |
| SQL_FILE_PATH             |    Filename of generated SQL Output). E.g abda.sql    |
| ABDA_DIR_PATH             |               Input file like FAM_L.GES               |

- DB=LAIEN;SQL_FILE_PATH=abda.sql;ABDA_DIR_PATH='/home/andre/IdeaProjects/K2Converter/src/test/resources/GES010413'

# Running the Project
To build the project, navigate to the project directory and run the following command (it creates a fat jar):

```bash
gradle build
```
Running the Project (needs Java 21)
To run the project, use the following command:
```bash
java -jar build/libs/K2Converter-1.0-SNAPSHOT.jar
```


# Project Structure
The main class of the project is K2Converter.java which contains the main logic for data conversion. It uses Apache Camel routes to read data from a file, process it, and write the output to another file.  


# Features

Data conversion from ASCII to SQL.
Logging of processed data.
Error handling during data conversion.

## TODO / Status
- SQL ~ 80%
- K Record -> 100% 
- F Record -> 90%
- I Record -> 80%
- E Record -> 0%
- U Record -> 0%
- D Record -> 0%
- Data Typ -> 99%
- Control Sequences -> 0%
- (Unicode may be not needed?)
- Validation (Input, Output) -> 20%
- Testing (Coverage): -> 50 %
- Monitoring (Logging, CPU..) -> 5%
- build 50%

- How to handle UPD files ???

