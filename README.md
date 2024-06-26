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
#gradle build
gradle clean customFatJar
```
Running the Project (needs Java 21)
To run the project, use the following command:
```bash
java -jar build/libs/K2Converter-1.0-SNAPSHOT.jar  -i /path/to/input -d my_database -o my_output.sql

```

# Project Structure
The main class of the project is K2Converter.java which contains the main logic for data conversion. It uses Apache Camel routes to read data from a file, process it, and write the output to another file.  

# Features

Data conversion from ASCII to SQL.
Logging of processed data.
Error handling during data conversion.

## TODO / Status
- SQL ~ 80%
- K Record ~ 100% 
- F Record ~ 90%
- I Record ~ 80%
- E Record ~ 0%
- U Record ~ 0%
- D Record ~ 0%
- Data Typ ~ 99%
- Control Sequences ~ 0%
- (Unicode may be not needed?)
- Validation (Input, Output) ~ 20%
- Testing (Coverage): ~ 50 %
- Monitoring (Logging, CPU..) ~ 5%
- build 50%


# To Solve / Problems
- I-Sätze ID :
  - datatype is not defined
  - 10 is missing 
  - index is not pure numeric
- type ID1 is not precise
  - Zeichenkette aus den Ziffern 0-9, den Buchstaben A-Z sowie a-z und dem Unter-
      strich (ASCII 95dez. )
- UPD files have F records ?? - why?? - how to solve
- there are no FK flagged !!!! 
- PACFAM_L feld FeldRecord Komponentennr ist anders im ER-Modell
- 4.2  F-Sätze field length in Byte, are not always correct 
  - solution: tolerance buffer  


# Version
(business funktionality)

0.1 "import LAIEN FAM_L" [03.06.2024]
----
 - added: 
   - FAM_L table converter: object converter, sqlconverter
   - records types: K, F, I
   - data types conversion:  
                         AL1
                         AN1
                         AN2
                         AN3
                         ATC
                         B64
                         DT8
                         FLA
                         FN1
                         FN2
                         GK1
                         GRU
                         ID1
                         IKZ
                         IND
                         MPG
                         NU1
                         NU2
                         NU3
                         NU4
                         PNH
                         PRO
                         PZN
                         PZ8
                         WGS
   - GES import


# Planning 
(each version ~ 5 days)

V0.2 "Get Anwendungshinweise from Fertigarzneimittel table by Pharmazentralnummer" [10.06.2024]
----
   - PAC_FAM_L table
   - records types: E
   - converter validation 

V0.3 "Artikelstamm PAC_APO Import, Artikelstamm (K2_FORMAT Version 07.04.2017)" [17.06.2024]
----
  - records types: D   
  - data type conversion: DTV

V0.4 "Update - import UPD files, Date format handling (K2_FORMAT Version 20.12.2016)" [24.06.2024]
----
- records types: U
- UPD import


V0.6 ??? ...
----
next table????




V1.0 "HTML & DB" [01.07.2024]
----
   -  SQL Client 
   -  convert control sequences to html

V1.1 "Deamon"  [08.07.2024] 
----
  - continuously monitor a folder 
  - automatic import to database 
  - delete imported files to clean input folder after import  

V1.2 "Automatic import from provider"  [15.07.2024]
----
   - Download new import .GES, .UPD files or extract from email or ..

V1.3 "Publish report"   [22.07.2024]
----
   - import to 2 db separatly
   - send report to chat or email

