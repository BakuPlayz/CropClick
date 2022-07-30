:: Purpose: Avoid having issues with addons, while contributing to the project.
:: Requirements: Maven & jar files (https://maven.apache.org/install.html)

:: Step-by-step (to fix all dependency issues):
::  1. Make sure you have maven (command: mvn) installed.
::  2. Run this script (.\dependency_fixall.bat) in the terminal.
::  3. You should now be all good, no more dependency issues (You're welcome :D).


:: Adds OfflineGrowth to the local maven.
CALL ./growth_fix.bat


:: Adds Residence to the local maven.
CALL ./residence_fix.bat


:: Adds MockBukkit to the local maven.
CALL ./mock_fix.bat