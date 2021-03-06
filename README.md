# Team_CPMC
### Team Members
1. **Puen Xie** :sweat_smile:
	* Code Architecture Lead
2. **Constance Yang** :rainbow:
	* UI/UX Design Lead
	* Testing Lead
3. **Michelle Watson** :v:
	* Team Manager
	* Client Liason
4. **Cameron Ventimiglia** :smile:
	* Documentation Lead
	* Data Modeler
# About Our Project
The purpose of this project is to implement a functional API that takes
the name of an input file as its parameter and return the name of an
output file as its only result. The input file will specify the functionality
of software under test using the **category-partition method**. The API will
apply the **category-partition method** to the formatted contents of the input file,
generating test cases based on the specification, and writing the results for them
to an output file in the Cucumber "given-when-then" notation. The test cases will
specify the selection of equivalence classes for the parameter and the expected test
results when the test has been running.
# About the Category-Partition Method
As a tester, the number of tests that can be performed depends on a set of conditions
and parameters of a function. The number of tests can be small, but most of the time,
it is a large amount. Therefore, most testers use different testing techniques to help
limit the total number of tests needing to be completed. The **Category-Partition Method**
allows the tester to generate equivalence classes for each parameter. For example, an
integer parameter can have equivalence classes: “Negative”, “Positive”, “Max Integer Value”,
“Min Integer Value”, and “0”. These equivalence classes eliminate the need to test every number
of the same type. An example would be the numbers Seven or Eight. Since the two numbers are both
positive integers, they will provide the same functionality within the test.
# How To Install Our Project
1. Clone the master branch of this repository to a location of your choosing
    1. For HTTPS clone
    ```
    git clone https://github.com/cventimiglia/Team_CPMC.git
    ```
    2. For SSH
    ```
    git clone git@github.com:cventimiglia/Team_CPMC.git 
    ```
# Requirements To Run
* Provided you follow the steps listed in the following sections, you should only need to open the project
in Eclipse and run it. To run the application locally, you will need to:
	* right click on gs-uploading-files-initial -> run as -> Spring Boot App
	* Once the service is started, type localhost:8080 into your browser to pull up the page.
# What To Install
1. [Simple JSON JAR File](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple111jar.htm)
    1. Once downloaded, extract the JAR file to a location of your choosing.
    2. Once extracted, right click the project and click on build path -> configure build path.
    3. Once the window opens, click on libraries -> Add external JARs -> select the extracted JAR file
2. Spring Tool Suite 4
    1. Launch Eclipse and go to the help tab at the top
    2. Click on Eclipse Marketplace and search for Spring Tools 4 (aka Spring Tool Suite 4)
    3. Install Spring Tools 4
# How To Use Testing Packages
1. For the selenium tests, you can follow either of the guides below to install the chrome driver and the selenium JAR files 
    1. [First link](https://www.guru99.com/installing-selenium-webdriver.html)
    2. [Second link](https://www.browserstack.com/guide/how-to-setup-selenium-in-eclipse)
2. You may need to install JUnit 5 in order to run the JUnit tests. Link to this is down below. **NOTE:** The tests made inside of the com.example.uploadingfiles and com.example.uploadingfiles.storage packages were not made by us and were imported using Spring.
    1. [Link to the Spring Guide](https://spring.io/guides/gs/uploading-files/)
    2. [Link to JUnit 5 download](https://junit.org/junit5/)
# Important Info/Links
* Our [gitRepo](https://github.com/cventimiglia/Team_CPMC)
* Our [Progress tracking tool](https://jira.ggc.edu/secure/RapidBoard.jspa?rapidView=93&projectKey=TC&view=planning.nodetail&epics=visible&issueLimit=100)
* Communication Platforms
	* Discord
	* Microsoft Teams
# Quick Link To Our License
[License](https://github.com/cventimiglia/Team_CPMC/blob/main/LICENSE)
