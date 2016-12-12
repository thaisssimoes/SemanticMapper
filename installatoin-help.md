# Setting up your OS and Environment

This instructions are supposed to help you configuring your OS and its environment so you can build and run SemanticMapper. If you are not used to configuring environment variables or installing software by command line it is highly recommended that you create a virtual machine to set everything up, so you won't mess up you're computer.

For the following instructions We have used [VirtualBox](https://www.virtualbox.org/) as the virtualization software and [Ubuntu 16.04LTS](https://www.ubuntu.com/download/desktop) (x64) as the operational system. Nonetheless, feel free to try the tips on any Unix-like OS, considering the adaptations necessary to all commands.

## First things first!

### Virtual Machine add-ons

If you are going to use a virtual machine, make sure to install any guest complements necessary. If you are under VirtualBox, this means that with you VM open, go to `evices > Insert Guest Adittions CD Image` and run the installation "CD".

### Proxy settings

The following proceedings should not be difficult to follow if you're running it in a open network. If you'r behind a proxy, make sure to configure your OS according to your network administrator's orientation. Some softwares might not follow your  global proxy settings, requiring specific configuration. This is quite common for APT and Maven. For those situations you'll have to look for specific guidance on the web (sorry...).

## Installing required softwares

### Install Oracle Java 8 JDK

```sh
$ sudo add-apt-repository ppa:webupd8team/java
$ sudo apt-get update
$ sudo apt-get install oracle-java8-installer
```

### Install auxiliary tools

*(Only for fresh OS installations)*

```sh
$ sudo apt-get install vim
$ sudo apt-get install maven
$ sudo apt-get install git
```

### Install WordNet 3.0 (and 3.1 update)

*IMPORTANT*: WordNet **MUST** be installed manually, not through APT, YUM or other package managing tools.

Go to [Princeton's WordNet official website](https://wordnet.princeton.edu/) and download version 3.0 for Linux operational systems. The download will consist of a `.tar.gz` file. Extract it's contents to anywhere on your computer (We recomment the `/opt` folder, which is the usual place for third-party software).

Since WordNet won't be run by command line, it is not necessary to compile its source code. Nonethelesse, if you're familiar with the "configure/make/install" process, feel free to do it.

Once WordNet has been extracted (or compiled) download the update 3.1, which is just a new `dict/` folder that must substitute the original folder in WordNet's directory.

### Adjust Environment variables

With JDK 8 installed an WordNet extracted, it is time to set up `JAVA_HOME` and `WNHOME` variables. As super user (*sudo*) open `/etc/profile` file and add the following two lines below all of its content.

```sh
export WNHOME=/opt/WordNet-3.0
export JAVA_HOME=/usr/lib/jvm/java-8-oracle
```

*Obs*.: Make sure that both paths refer to the paths used by you when installing JDK 8 and WordNet.

After inserting both lines log out or reboot your OS to guarantee that the OS reloads the variables correctly. To check if everything is as they should be, check the variables content with the `echo` command. You should see the configured content.

```sh
$ echo $JAVA_HOME
/opt/WordNet-3.0

$ echo $WNHOME
/usr/lib/jvm/java-8-oracle
```

### Install MySQL

Run the following command and follow any necessary instructions to set up a root password
```sh
$ sudo apt-get install mysql-server mysql-client
```
Make sure the database is up and running by checking its status

```sh
$ sudo service mysql status
```

Optionally install MySQL Workbench to navigate the SGBD through a graphic interface.

```sh
$ sudo apt-get install mysql-workbench
```

Create a schema name `supersense_mapping` for the system to save all mappings. If you wish, you can also create a separated user to own the schema and configure it when preparing to execute the application.

### Install Eclipse

**ATTENTION!** Do not install Eclipse through package managing utility!

Go to Eclipse's official website and download the installer. Extract it to a accessible folder (such as `/opt`) and run the installer.

```sh
./eclipse-installer
```

Select the "Eclipse IDE for Java EE Developers" option and change the installation place to `/opt`.

## You're all set! :)

That's it! You should be able to package and run the project with these simple instructions. Once the application has been executed all mappings generated will be availabe in the database.
