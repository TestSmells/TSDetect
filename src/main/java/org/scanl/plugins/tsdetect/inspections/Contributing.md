# Adding a Test Smell Inspection
Adding a test smell is a straightforward process

1. Create a class that extend SmellInspection
   1. Override and implement the following methods
      - buildVisitor()
      - hasSmell()
      - getSmellType()
2. Add an enum for that smell inspection to the SmellType Enum
3. Add .class file to TestSmellInspectionProvider
4. Add text data to .properties files in [Properties files](src/main/resources/I18n/inspection/)
   - Be sure that any translations are done (or verified) by a native speaker

For testing this new smell, see the [Tests](src/test/testData) directory
 Add an example of the test smell to 