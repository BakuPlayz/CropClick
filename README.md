# CropClick

![latest_version](https://img.shields.io/spiget/version/69480?label=version&color=teal&style=for-the-badge)
![spigot_downloads](https://img.shields.io/spiget/downloads/69480?color=teal&style=for-the-badge)
![spigot_stars](https://img.shields.io/spiget/stars/69480?color=teal&style=for-the-badge)
![plugin_issues](https://img.shields.io/github/issues/BakuPlayz/CropClick?color=teal&style=for-the-badge)

# Features
- **Autofarms**: An automatic farming method making you potatoes, carrots or whatever you need! While you mine, build or AFK away :)
- **Vanilla Crops**: Supports all the vanilla crops, so that you can click-away at all of them.
- **Custom Crops**: Supports your custom crops, so you can click-away at them too.

# Preview

### Harvesting crops has never been easier! ###
Just right click a crop, get the drops and get back on the tops of your Mincraft journey!
<br>
![A video on (fast) harvesting some ground crops.](docs/media/ground_harvest.gif)
![A video on (fast) harvesting the chorus crop.](docs/media/chorus_harvest.gif)

### Autofarming has never been closer! ###
Just connect a crop, dispenser and a container to link them together (By shift left-clicking on them). Fill the the dispenser with some bonemeal, build a redstone clock and then connect it to the dispenser. And boom! You now got your own functioning autofarm!
<br>
![A video of how to create a autofarm.](docs/media/autofarm_link.gif)

# Commands
<details>
  <summary><i>Click to view or hide</i></summary>
  <br>
  
```yaml
/crop: the base command for CropClick.
/crop autofarms: shows all of the autofarms.
/crop help: shows all the commands and their permissions.
/crop reload: reloads the plugin (i.e. good for configuration changes).
/crop reset: resets the plugin to its default settings.
/crop settings: shows the most important settings for customizing CropClick.
```
</details>

# Permissions
<details>
  <summary><i>Click to view or hide</i></summary>
 
#### All
```yaml
cropclick.*: permission to access and use everything in CropClick.
cropclick.command.*: permission to use every command.
cropclick.autofarm.*: permission to use all the autofarm features.
cropclick.plant.*: permission to plant every crop.
cropclick.harvest.*: permission to harvest every crop.
cropclick.destory.*: permission to destroy every crop.
```

#### Commands
```yaml
cropclick.command.general: permission to use the general command # /crop
cropclick.command.autofarms: permission to use the autofarms command. # /crop autofarms
cropclick.command.help: permission to use the help command. # /crop help
cropclick.command.reload: permission to use the reload command. # /crop reload
cropclick.command.reset: permission to use the reset command. # /crop reset
cropclick.command.settings: permission to use the settings command. # /crop settings
```

#### Autofarms
```yaml
cropclick.autofarm.claim: permission to claim autofarms with unknown an owner.
cropclick.autofarm.link: permission to link your own autofarms.
cropclick.autofarm.unlink: permission to unlink your own autofarms.
cropclick.autofarm.unlink.others: permission to unlink others autofarms.
cropclick.autofarm.update: permission to update your own farms.
cropclick.autofarm.update.others: permission to update others autofarms.
cropclick.autofarm.toggle: permission to toggle your own autofarms.
cropclick.autofarm.toggle.others: permission to toggle others autofarms.
cropclick.autofarm.interact: permission to interact with your own autofarms.
cropclick.autofarm.interact.other: permission to interact with your others autofarms.
```

#### Crops
```yaml
# OBS! Replace the "cropName" in the following with the name of the crop, e.g. netherWart.

cropclick.plant.(cropName): permission to plant the given crop.
cropclick.harvest.(cropName): permission to harvest the given crop.
cropclick.destroy.(cropName): permission to destory the given crop.

cropNames: # A list of all the vanilla crop names (custom crops will also have their own permissions).
- bamboo
- beetroot
- brownMushroom
- cactus
- carrot
- chorus
- cocoaBean
- dripleaf
- glowBerries
- kelp
- melon
- netherWart
- potato
- pumpkin
- redMushroom
- seaPickle
- sugarCane
- sweetBerries
- twistingVines
- wheat
```

</details>


# What version should I get?

- **Legacy.jar** are for server versions 1.8 to 1.12.2.
- **Latest.jar** are for server versions 1.13+.

# Java Issues?

- All versions require **Java 8 or higher**.

# Issues

Are you experiencing a bug or just want an feature to be added? Then please head over to
the [issues tab](https://github.com/BakuPlayz/CropClick/issues), where you firstly look-around and see wheater or not
the issue(s) already been reported. If it's not yet been created, then please go-ahead and pick either bug or feature
report depending on your issue(s). Also, if you don't have a github account or have any issues with reporting then
please head over to the [discord server](https://discord.gg/HqQqz2Z) and report it over there. Additionally, please
follow the
formats: [bug report](https://github.com/BakuPlayz/CropClick/blob/release/.github/ISSUE_TEMPLATE/bug_report.md)
or [feature request](https://github.com/BakuPlayz/CropClick/blob/release/.github/ISSUE_TEMPLATE/feature_request.md) when
creating a new issue.

# Contributions

Contributions is done freely by whomever want to contribute. It may be something as small as a spelling mistake, or as big as a douplication glitch. Every contribute and contributor is welcome. Additionally, if you contribute to the project you will also be abled to redeem the "Contributor"-rank on my plugins dedicated [discord server](https://discord.gg/HqQqz2Z). 
