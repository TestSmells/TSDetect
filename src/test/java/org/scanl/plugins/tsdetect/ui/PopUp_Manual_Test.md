# PopUp Manual Test


<!-- test description -->

[//]: # (General description of the test)
The Purpose of this test is to manually confirm the functionality of the TSDetect plugin's popup asking for permission to
collect user's data anonymously. The proper execution of this test involves the tester taking the specified actions, and
either confirming or deying the validity of the statements made about the results of those actions. Results shall be written
as either "PASS", "FAIL", or "PASS W/ NOTES". A section will be provided for notes after each test step.


### Step 1

- Navigate to <kbd>..\TSDetect\build\idea-sandbox\config\options</kbd> and open <kbd>TSDetectPlugin.xml in an xml editor</kbd> 
- Ensure that there is no entry in the map with the key "OPT_IN". If there is, delete it.
- Open the TSDetect Plugin application in Intellij, and set the Run configuration to <kbd>Run Plugin</kbd>
- Run the Plugin.
- If prompted to select a project, select TSDetect.
- **Verify that a popup is displayed that reads \"Do you consent to sending test smell data for research purposes?\"**
<br>
<br>Result: PASS W/ NOTES
<br>NOTES: Popup contains additional text reading " (you can change this decision later in the TSDetect settings)"
<br>
<br>

- **Verify that a popup is displayed with three buttons reading, from left to right, \"Yes\", \'No\", and \"Cancel\"**
<br>
<br>Result: PASS
<br>NOTES: 
<br>
<br>

### Step 2

- Click the button labeled "Cancel"
- **Verify that the popup closes**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Step 3

- In the instance of Intellij, Navigate to the Applications settings menu. <kbd>(File > Settings... > Tools > TSDetect: Application Settings)</kbd>
- **Verify that the last item in the list of options reads "Opt in to data collection"**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

- **Verify that the "Opt in to data collection" item's check box is not filled in**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Step 4
- Stop 'Run Plugin', killing your instance of the plugin.
- Open the TSDetect Plugin application in Intellij, and set the Run configuration to <kbd>Run Plugin</kbd>
- Run the Plugin.
- If prompted to select a project, select TSDetect.
- **Verify that a popup is displayed that reads \"Do you consent to sending test smell data for research purposes?\"**
  <br>
- <br>Result: PASS W/ NOTES
  <br>NOTES: Popup contains additional text reading " (you can change this decision later in the TSDetect settings)"
  <br>
  <br>

### Step 5

- Click the button labeled "Cancel"
- **Verify that the popup closes**
  <br>
  <br>Result: PASS
  <br>NOTES:
  <br>
  <br>

### Step 6

- In the instance of Intellij, Navigate to the Applications settings menu. <kbd>(File > Settings... > Tools > TSDetect: Application Settings)</kbd>
- **Verify that the last item in the list of options reads "Opt in to data collection"**
  <br>
  <br>Result: PASS
  <br>NOTES:
  <br>
  <br>

- **Verify that the "Opt in to data collection" item's check box is not filled in**
  <br>
  <br>Result: PASS
  <br>NOTES:
  <br>
  <br>

### Step 7
- Click the checkbox next to the "Opt in to data collection" item in the application settings menu.
- **Verify that the "Opt in to data collection" item's check box is now filled in**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Step 8
- close the settings window.
- In the instance of Intellij, Navigate to the Applications settings menu. <kbd>(File > Settings... > Tools > TSDetect: Application Settings)</kbd>
- **Verify that the "Opt in to data collection" item's check box is still filled in**
<br>
<br>Result: FAILED
<br>NOTES: Check box is empty
<br>
<br>

### Step 9
- Stop 'Run Plugin', killing your instance of the plugin.
- Open the TSDetect Plugin application in Intellij, and set the Run configuration to <kbd>Run Plugin</kbd>
- Run the Plugin.
- If prompted to select a project, select TSDetect.
- **Verify that a popup is NOT displayed that reads \"Do you consent to sending test smell data for research purposes?\"**
  <br>
- <br>Result: FAILED
  <br>NOTES: Popup still displayed
  <br>
  <br>


### Step 10
- Stop 'Run Plugin', killing your instance of the plugin.
- Navigate to <kbd>..\TSDetect\build\idea-sandbox\config\options</kbd> and open <kbd>TSDetectPlugin.xml in an xml editor</kbd>
- delete the entry in the map with the key "OPT_IN" and save the file.
- Open the TSDetect Plugin application in Intellij, and set the Run configuration to <kbd>Run Plugin</kbd>
- Run the Plugin.
- If prompted to select a project, select TSDetect.
- **Verify that a popup is displayed that reads \"Do you consent to sending test smell data for research purposes?\"**
  <br>
- <br>Result: PASS W/ NOTES
  <br>NOTES: Popup contains additional text reading " (you can change this decision later in the TSDetect settings)"
  <br>
  <br>



### Step 11

- Click the button labeled "Yes" 
- **Verify that the popup closes**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Step 12

- In the instance of Intellij, Navigate to the Applications settings menu. <kbd>(File > Settings... > Tools > TSDetect: Application Settings)</kbd>
- **Verify that the last item in the list of options reads "Opt in to data collection"**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

- **Verify that the "Opt in to data collection" item's check box is filled in**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Step 13
- Click the checkbox next to the "Opt in to data collection" item in the application settings menu.
- **Verify that the "Opt in to data collection" item's check box is NOT filled in**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Step 14
- Click "OK" to close the settings window.
- In the instance of Intellij, Navigate to the Applications settings menu. <kbd>(File > Settings... > Tools > TSDetect: Application Settings)</kbd>
- **Verify that the "Opt in to data collection" item's check box is still NOT filled in**
<br>
<br>Result: PASS
<br>NOTES: 
<br>
<br>

### Step 15
- Stop 'Run Plugin', killing your instance of the plugin.
- Open the TSDetect Plugin application in Intellij, and set the Run configuration to <kbd>Run Plugin</kbd>
- Run the Plugin.
- If prompted to select a project, select TSDetect.
- **Verify that a popup is NOT displayed that reads \"Do you consent to sending test smell data for research purposes?\"**
  <br>
  <br>Result: PASS
  <br>NOTES:
  <br>
  <br>

### Step 16
- In the instance of Intellij, Navigate to the Applications settings menu. <kbd>(File > Settings... > Tools > TSDetect: Application Settings)</kbd>
- **Verify that the "Opt in to data collection" item's check box is still NOT filled in**
<br>
<br>Result: PASS
<br>NOTES: 
<br>
<br>

### Step 17
- Stop 'Run Plugin', killing your instance of the plugin.
- Navigate to <kbd>..\TSDetect\build\idea-sandbox\config\options</kbd> and open <kbd>TSDetectPlugin.xml in an xml editor</kbd>
- delete the entry in the map with the key "OPT_IN" and save the file.
- Open the TSDetect Plugin application in Intellij, and set the Run configuration to <kbd>Run Plugin</kbd>
- Run the Plugin.
- If prompted to select a project, select TSDetect.
- **Verify that a popup is displayed that reads \"Do you consent to sending test smell data for research purposes?\"**
  <br>
- <br>Result: PASS W/ NOTES
  <br>NOTES: Popup contains additional text reading " (you can change this decision later in the TSDetect settings)"
  <br>
  <br>


### Step 18

- Click the button labeled "No"
- **Verify that the popup closes**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Step 19

- In the instance of Intellij, Navigate to the Applications settings menu. <kbd>(File > Settings... > Tools > TSDetect: Application Settings)</kbd>
- **Verify that the last item in the list of options reads "Opt in to data collection"**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

- **Verify that the "Opt in to data collection" item's check box is NOT filled in**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Step 20
- Click the checkbox next to the "Opt in to data collection" item in the application settings menu.
- **Verify that the "Opt in to data collection" item's check box is filled in**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Step 21
- Click "OK" to close the settings window.
- In the instance of Intellij, Navigate to the Applications settings menu. <kbd>(File > Settings... > Tools > TSDetect: Application Settings)</kbd>
- **Verify that the "Opt in to data collection" item's check box is still filled in**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Step 22
- Stop 'Run Plugin', killing your instance of the plugin.
- Open the TSDetect Plugin application in Intellij, and set the Run configuration to <kbd>Run Plugin</kbd>
- Run the Plugin.
- If prompted to select a project, select TSDetect.
- **Verify that a popup is NOT displayed that reads \"Do you consent to sending test smell data for research purposes?\"**
  <br>
- <br>Result: PASS
  <br>NOTES:
  <br>
  <br>

### Step 23
- close the settings window.
- In the instance of Intellij, Navigate to the Applications settings menu. <kbd>(File > Settings... > Tools > TSDetect: Application Settings)</kbd>
- **Verify that the "Opt in to data collection" item's check box is still filled in**
<br>
<br>Result: PASS
<br>NOTES:
<br>
<br>

### Results
**Number of Test Steps:** 23<br>
**Number of Steps Passed:** 21<br>
**Notes: Some requirements updates are necessary to match increased scope of this item**