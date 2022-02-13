:: Purpose: Avoid having issues with addons, while contributing to the project.
:: Requirements: Maven & jar files (https://maven.apache.org/install.html)

:: Step-by-step (to fix all dependency issues):
::  1. Make sure you have maven (command: mvn) installed.
::  2. Run this script (.\dependency_fixall.bat) in the terminal.
::  3. You should now be all good, no more dependency issues (You're welcome :D).


:: Adds OfflineGrowth to the local maven.
mvn install:install-file -Dfile=OfflineGrowth-1.6.4.jar^
    -DgroupId=es.yellowzaki.offlinegrowth -DartifactId=OfflineGrowth^
    -Dversion=1.6.4 -Dpackaging=jar


:: Adds Residence to the local maven.
mvn install:install-file -Dfile=Residence5.0.1.3.jar^
    -DgroupId=com.bekvon.bukkit -DartifactId=Residence^
    -Dversion=5.0.1.3 -Dpackaging=jar