![Alt text](https://raw.githubusercontent.com/Milchreis/JCopy/master/src/main/resources/logo.png "Screenshot1")

A small and simple backup tool for coping your data. It's a java rewrite of [CoPy](https://github.com/Milchreis/CoPy)

With JCopy you get the possibility to backup your files in a comfortable and fast way. Select all interesting directories for saving and a destination, f. e. a usb-drive. 
The first backup will copies all files and takes possibly a while. Your session (selected directories) will saved for the next time. 
After restarting the tool the last session is loaded. You are able to start the backup process directly. This programm will update all new and modified files and skips the other files. 

### Features ###
 - Backup for files and directories
 - Joins different directories to a target directory (f.e. usb stick)
 - Copies new and modified files only for a faster process
 - Remember session state for easy usage
 - Commandline interface for changeable session files
 - Languages: English, German

### Download ###
 - See the [releases](https://github.com/Milchreis/JCopy/releases)

### Use instructions ###
 - Choose your backup directories
 - Choose a destination directory (maybe a USB-Drive)
 - Backup all files
     - Copies new and modified files

### Commandline Interface ###
If you want to use the tool in bash, as cronjob or something else you can use the headless mode. Start the programm with a commandline argument and the gui will be not loaded. CoPy expects just one argument. Put an individual sessionfile (as absolute or relative path) to the programm and it will be started the backup-process.

`usage: java -jar JCopy.jar /path/to/sessionfile`

Create your session file manually in a simpel text editor or by CoPy itself. The sessionfile contains in the first line the path to the target directory. All following lines will be interpreted as source directories. If you start CoPy in GUI Mode (without arguments) you can take you configurations and run the backup process. After clicking on the backup-button, the sessionfile will be created at your home directory like `/home/you/.jcopy`.

### Dependencies ###
 - Java runtime edition 8 or higher [download here](https://java.com/de/download/)

### Build ###
maven build
