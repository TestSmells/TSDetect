@echo off
::Windows script to run TSDetect in headless mode from the command line
:: RUN
::    tsdetect.bat \absolute_path_to_project\

::EXAMPLE    tsdetect.bat C:\Users\user\Documents\TSDetect

call ./gradlew "--stop"
call ./gradlew -Dorg.gradle.jvmargs="-Djava.awt.headless=true" runIde --console=plain -Ppaths="%1"