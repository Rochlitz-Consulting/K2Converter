# K2Converter

# Overview
K2Converter is a Java-based project that uses Apache Camel for routing and processing data. It is designed to convert data from one format to another, specifically from ASCII to SQL. The project uses Gradle as a build tool and Spring Boot as a framework.  

# Getting Started
#  Prerequisites
- Java 17
- Gradle
- Apache Camel 4.6.0
- Logging SLF4J

# Building the Project
To build the project, navigate to the project directory and run the following command:


# Configuration
## Environment Variables 

| Environment Variable Name |        Description        |
|---------------------------|:-------------------------:|
| DB                        |  DB name or schema name   |
| SQL_FILE_PATH             | filename of generated SQL |



# Running the Project
To build the project, navigate to the project directory and run the following command:
```bash
gradle build
```
Running the Project
To run the project, use the following command:
```bash
gradle run
```


# Project Structure
The main class of the project is K2Converter.java which contains the main logic for data conversion. It uses Apache Camel routes to read data from a file, process it, and write the output to another file.  
# Features

Data conversion from ASCII to SQL.
Logging of processed data.
Error handling during data conversion.

# TODO
SQL ~ 80%
K Record -> 100% 
F Record -> 90%
I Record -> 80%
E Record -> 0%
U Record -> 0%
Data Typ -> 99%
Control Sequences -> 0%
(Unicode ?)

Testing: -> 5 %
Monitoring -> 5%

