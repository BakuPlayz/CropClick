:: Purpose: Avoid having issues with addons whilst contributing to the project.

:: Steps-to-make-it-work:
:: 1. Run this command in your IDE's (e.g. INTELLIJ) maven console.
:: 2. Your fine to have no more issues with the addons (Your welcome :D)

mvn install:install-file -Dfile=src\lib\OfflineGrowth-1.6.4.jar \
    -DgroupId=es.yellowzaki.offlinegrowth -DartifactId=OfflineGrowth \
    -Dversion=1.6.4 -Dpackaging=jar