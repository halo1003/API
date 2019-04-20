				

# INSTALL TUTORIALS [mrs project] OS: Windows 

Note: Work well in Apache Tomcat 8.0.27.0 and Java 1.8

Download Netbean IDE at https://netbeans.org/downloads/8.0.2/ >> Open project mrs >> In Netbean IDE, point to "..\Source Packages\load_database.java" right click and Run File


# INSTALL TUTORIALS [API_CTV project] OS: Windows

Note: Dump20190420 folder contain alot tables.

Download and extract Dump20190420.rar at https://drive.google.com/drive/folders/1vV0d6cRHOY6VqXZrljqAmtiEPwrR5MXa?usp=sharing

1./ Import from Dump project folder Dump20190420 (it maybe take time) MYSQL workbench.


2./ ..\API_CTV\src\main\resources\application.properties 	// Project database configuration in here

- localhost:3306/sings is the MYSQL database connection link
- spring.datasource.url=jdbc:mysql://localhost:3306/sings?verifyServerCertificate=false&useSSL=true 
- spring.datasource.username={root}
- spring.datasource.password={root}

3./ - 2 ways to run this project
	A.	Download framework at https://spring.io/tools3/sts/all >> Import project API_CTV into this frameword (it maybe take time) >> In left corner, Click expand arrow and run API_CTV .
	B. 	Make sure that maven work well >> Open terminal, point to ..\API_CTV folder and run mvn package >> point to ..\API_CTV\target\ >> run java -jar BE_BBTS-0.0.1-SNAPSHOT.jar . 

# API

** API Check {uid} and {uid} table exist or not: 
- localhost::8090/cluster/isExist/uid={uid} 				

** API Create {uid} and {uid} table if it is not exist: 
- localhost::8090/cluster/create/uid={uid} 				

** API Show {uid} table: 
- localhost::8090/cluster/uid_history={uid}				

** API Update {uid} table when user listen music: 
- localhost::8090/cluster/uid={uid}/trackid={trackid} 	

** API Show recomendation list: 
- localhost::8090/cluster/uid={uid} 						

** API Refactor cluster table when the unbalance of each clusters appear: 
- localhost::8090/cluster/refactor			