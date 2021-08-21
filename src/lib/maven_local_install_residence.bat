:: Purpose: Avoid having issues with addons whilst contributing to the project.

:: Steps-to-make-it-work:
:: 1. Run this command in your IDE's (e.g. INTELLIJ) maven console.
:: 2. Your fine to have no more issues with the addons (Your welcome :D)

mvn install:install-file -Dfile=src\lib\Residence-5.0.0.2.jar \
    -DgroupId=com.bekvon.bukkit -DartifactId=Residence \
    -Dversion=5.0.0.2 -Dpackaging=jar