# Adding a Test Smell Inspection
Adding a test smell is a straightforward process

### 1. Create a class that extend [SmellInspection](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/java/org/scanl/plugins/tsdetect/inspections/SmellInspection.java)
- Override and implement the following methods. 
  - [buildVisitor()](https://github.com/JetBrains/intellij-community/blob/a320e7b7921045dbab1efca44e1eb1a8fda03554/java/java-analysis-api/src/com/intellij/codeInspection/AbstractBaseJavaLocalInspectionTool.java#L48)
  - This is where registering a problem with [QuickFixes](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/java/org/scanl/plugins/tsdetect/quickfixes) should occur.
  - [hasSmell()](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/java/org/scanl/plugins/tsdetect/inspections/SmellInspection.java#L34)
  - [getSmellType()](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/java/org/scanl/plugins/tsdetect/inspections/SmellInspection.java#L35)
      
This class should follow the naming convention SmellNameInspection.java. 
It may be helpful to look at the existing smells as example, such as 
[Empty Method Inspection](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/java/org/scanl/plugins/tsdetect/inspections/EmptyMethodInspection.java).

### 2. Add an enum for the [SmellType](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/java/org/scanl/plugins/tsdetect/model/SmellType.java)
- This should follow the capital camel case naming convention: SMELL_NAME
  - In the case of single name smells, we have defaulted to appending _TEST to the end
    
  - Ex. VERBOSE_TEST rather than just VERBOSE
- This must be unique as it is used in the [text.properties](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/resources/I18n/inspection/text_en.properties).

### 3. Add SmellNameInspection.class file to [TestSmellInspectionProvider](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/java/org/scanl/plugins/tsdetect/inspections/TestSmellInspectionProvider.java)
- This should be the same name as the smell inspection that was created in step [1](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/java/org/scanl/plugins/tsdetect/inspections/Contributing.md#L4).

### 4. Add text data to text.properties files in [Properties files](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/resources/I18n/inspection/text_en.properties)
- All lines must be prefixed with INSPECTION.SMELL.
- This is followed by the enum for the smell as defined in step [2](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/java/org/scanl/plugins/tsdetect/inspections/Contributing.md#L12)
- Then the following must be defined
  - INSPECTION.SMELL.SMELL_NAME.DESCRIPTION=Description of the smell that is being defined
  - INSPECTION.SMELL.SMELL_NAME.NAME.DISPLAY=Name that is displayed in the UI
  - INSPECTION.SMELL.SMELL_NAME.NAME.SHORT=Abbreviation for the name of Smell
- Any [QuickFixes](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/main/java/org/scanl/plugins/tsdetect/quickfixes) that are defined must have a description given to them like below
  - INSPECTION.SMELL.SMELL_NAME.FIX.COMMENT=Comment about how commenting of the highlighted elements fixes the issue
  - INSPECTION.SMELL.SMELL_NAME.FIX.REMOVE=Comment about how removal of the highlighted elements fixes the issue

### 5. For testing this new smell, see the ReadMe in the [Tests](https://github.com/TestSmells/TSDetect/blob/4f32c1622974f10b615be1307c80ad5c2f686ba2/src/test) directory
- This must be completed before a test can be accepted into the suite of test smells
