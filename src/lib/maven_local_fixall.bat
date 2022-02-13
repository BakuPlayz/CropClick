:: Purpose: Avoid having issues with addons, while contributing to the project.

:: TODO: Make better step-by-step


:: Step-by-step to solve dependency issues:
:: 1. Install mvn (https://maven.apache.org/install.html).
:: 2. Run this command in your IDE's (e.g. IntelliJ's) maven console.
:: 3. You're all good, no more addon issues (You're welcome :D).


:: Adds OfflineGrowth to the local maven.
mvn install:install-file -Dfile=OfflineGrowth-1.6.4.jar^
    -DgroupId=es.yellowzaki.offlinegrowth -DartifactId=OfflineGrowth^
    -Dversion=1.6.4 -Dpackaging=jar


:: Adds Residence to the local maven.
mvn install:install-file -Dfile=Residence5.0.1.3.jar^
    -DgroupId=com.bekvon.bukkit -DartifactId=Residence^
    -Dversion=5.0.1.3 -Dpackaging=jar