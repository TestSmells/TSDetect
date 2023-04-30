# Headless Mode Code Inspection


<!-- test description -->

[//]: # (General description of the test)
The purpose of this test is to manually inspect the functionality of the TSDetect plugin's capacity to be run from the command line 
(known as "Headless Mode"). 


### 1: Criteria
- The plugin shall be runnable from the command line, accepting the paths of projects to be analysed as arguments
### 1: Satisfied by
- verify that paths are read in as arguments in the "runIDE" method located in the **TSDetect\build.gradle.kts**
  class file.

### 2: Criteria
- The plugin shall have an entry point that can be accessed while being run in headless mode
### 2: Satisfied by
- Verify that an analysis is run on in the "main" method found in the
  **TSDetect\src\main\java\org\scanl\plugins\tsdetect\config\TSDetectCommandLine.java** class file .

### 3: Criteria
- The plugin shall run it's analysis on ALL projects pointed to by the provided URL parameters
### 3: Satisfied by
- Verify that the analysis is run on each project located at the provided url parameters in the "main" method found in the
  **TSDetect\src\main\java\org\scanl\plugins\tsdetect\config\TSDetectCommandLine.java** class file.

### 4: Criteria
- The analysis of a given project, when done in headless mode shall be identical to a similar analysis done from inside the Intellij IDE
### 4: Satisfied by
- Verify that headless mode uses an instance of "DetectTestSmells" to analyse a given project in the "runAnalysis" method found in the
  **TSDetect\src\main\java\org\scanl\plugins\tsdetect\config\TSDetectCommandLine.java** class file.

### 5: Criteria
- Once a given project is analysed by TSDtect in headless mode a .csv file shall be generated in the project's top-level directory
  detailing the results of the analysis in the following format:
       "Smell Type,Infected Classes,Infected Methods"
### 5: Satisfied by
- Verify that verify that a csv file is printed with the given format at the location of the given project in the "writeCSV" method found in the
  **F:\TSDetect\src\main\java\org\scanl\plugins\tsdetect\model\ExecutionResult.java** class file.