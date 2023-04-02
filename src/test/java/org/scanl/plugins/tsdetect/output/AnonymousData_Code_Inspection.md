# Anonymous Data Code Inspection


<!-- test description -->

[//]: # (General description of the test)
The purpose of this test is to manually inspect the functionality of the TSDetect plugin's ability to write data from
the tool's run to the API layer classes. Proper execution of this test will involve generating a unique user ID (UUID)
value for the user, passing that value, a timestamp from when the test was run, as well as any detected test smells
in a JSON wrapped hashmap. Success will be met with a 200 POST request. A section will be provided for each section
following each inspection step.


### 1: Criteria
- The test results generated from the TSDetect plugin shall be scanned for the following sendable information:
  - Timestamp
  - A list of test smells, and associated number of occurrences
### 1: Satisfied by
- Verify that test smells are scanned for in the *LoadSmellyData()* method in the **src/main/java/org/scanl/plugins/tsdetect/ui/tabs/TabDetectedSmellTypes.java**
  class file

### 2: Criteria
- The sendable information, along with the users uuid, shall be compiled into a hashmap with the following format:
  - "userID" : (A 36 character string)
  - "timestamp" : Timestamp in “yyyy-mm-dd hh:mm:ss.sss”
### 2: Satisfied by
- Verify that information is compiled into a hashmap using the *addData()* method in **src/main/java/org/scanl/plugins/tsdetect/model/AnonymousData.java**,
  which is invoked in the *sendData()* method. Smells are added to this hashmap in the *LoadSmellyData()* method in the class
  **src/main/java/org/scanl/plugins/tsdetect/ui/tabs/TabDetectedSmellTypes.java**
- Verify a UUID is generated in **src/main/java/org/scanl/plugins/tsdetect/config/PluginSettings.java** in the *uuid()* method,
  calling the *getUuid()* getter method in **src/main/java/org/scanl/plugins/tsdetect/config/application/AppSettingsState.java.**
- Verify a timestamp is generated in **src/main/java/org/scanl/plugins/tsdetect/model/AnonymousData.java**, in the *sendData()*
  method.

### 3: Criteria
- Testsmell Name : Number of that test smell found
  - ^ Above is repeated the required number of times to incorporate all test smells
### 4: Satisfied by
- Verify the number of a given test smell is populated in the *LoadSmellyData()* method in the **src/main/java/org/scanl/plugins/tsdetect/ui/tabs/TabDetectedSmellTypes.java**
  class.

### 4: Criteria
- The hashmap of sendable data shall be wrapped in a JSON format
  - Data is sent over HTTP as a JSON object
### 4: Satisfied by
- Verify the hashmap becomes JSON wrapped in the *sendData()* method in **src/main/java/org/scanl/plugins/tsdetect/model/AnonymousData.java,**
  then verify the HTTP request is sent as part of an HTTP request in the  *postRequest()* method in the same class.

### 5: Criteria
- The TSDetect tool shall attempt to connect to the “POST /test-results” endpoint of the TSDetect input API and send
  this json
### 5: Satisfied by
- Verify the POST request to send the JSON is attempted in the *postRequest()* method in the **src/main/java/org/scanl/plugins/tsdetect/model/AnonymousData.java**
  class

### 6: Criteria
- If the POST request does NOT return a response of 200, TSDetect shall attempt to connect again, to a maximum of five
  attempts
  - If the POST request fails to return a response of 200 five consecutive times, TSDetect will store the json as a file
    locally
  - If the POST request returns a response of 200, TSDetect shall dispose of the data collected
### 6: Satisfied by
- Verify reconnection attempts are made in the beginning of the *postRequest()* method in the **src/main/java/org/scanl/plugins/tsdetect/model/AnonymousData.java**
  class.
  - Verify that a local file is made in the *localSave()* method in the **src/main/java/org/scanl/plugins/tsdetect/model/AnonymousData.java**
    class, after 5 attempts are made in the *postRequest()* method
  - Upon a successful 200 response, verify the data is not saved from the *postRequest()* method

### 7: Criteria
- If there are any json files located in TSDetect’s designated local storage area, it shall verify that they are of the
  correct format.
  - If they are, TSDetect shall repeat the POST /test-results with this json file
### 7: Satisfied by
- Verify that in the *sendData()* method in the **src/main/java/org/scanl/plugins/tsdetect/model/AnonymousData.java** class, a check
  is performed to detect any files named "unsentAnonymousData.json" are present in the file system
  - In the same method, verify the *postRequest()* method is invoked to send unsent data in the "unsentAnonymousData.json" file
    before the current data to be sent

