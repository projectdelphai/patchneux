patchneux
===========
Reuben Castelino - projectdelphai@gmail.com

Description
-----------
If I had to compare the patchneux world to something, I would say that the end product should be a mixture of the world from [Ready Player One](https://en.wikipedia.org/wiki/Ready_Player_One), the world of [Neuromancer](https://en.wikipedia.org/wiki/Neuromancer), the world of [the Matrix](https://en.wikipedia.org/wiki/The_Matrix), the world from [Summer Wars](https://en.wikipedia.org/wiki/Summer_Wars), and Jarvis from the Iron Man movies. 

Quick-Use
---------
Install ant.

To start the server, enter the server directory and run:

    ant run

To start the client, enter the client directory and run:

    java Client

In-Depth
---------

To move around:

    goright, goleft, goahead, gobehind

To create rooms:

    cr

To learn about an item:

    i<itemName>

To pick up an item:

    p<itemName>

To learn about yourself and/or view profile:

    aboutme

To look around and learn about the room (important for after you've done an action and want to confirm that it worked):

    lookaround

To drop items:

    drop<itemName>

To greet NPC:

    greet<NPCname>

To give item to NPC:

    give<itemName>to<NPCName>

To apply item to doors:

    apply<itemName>to<doorDirection>

The door directions are right,left,ahead,behind

###Bugs###

 1. Room names may not yet contain any spaces.
 1. Items end with commas
 1. a/an not specified for objects

Code
----------

patchneux is my first project written in Java and is my way of figuring out the language. I do have experience in ruby and python, but I am inexperienced with compiled languages. So this is a test for me, however I plan to stay with patchneux long after I'm done with Java.

This does mean, however, that I will make mistakes. And many of them. Bear with me and feel free to correct me and/or make pull requests when I do something wrong.

The way I am currently building patchneux is through a server/client method EVEN LOCALLY. This means that a patchneux server holds all the commands and the thinking, but the user never works with the server. Different clients are built to interact with the server ranging from the easiest (command line prompt) to the harder (graphical art with gui) to the hardest (oculus rift virtual graphics or verbal command prompt). I will be focusing more on the server than the clients through I may try my hand at some if no one steps up to try them. 

Be forewarned that the default client will be VERY basic since I will just be using it to test new features in the patchneux server.

Goals
--------

###Outside of patchneux###

 ~~1. Learn about lower-level programs~~

 ~~1. Write better docs~~

  1. Learn about game programming
  1. Learn about multiplayer networking

 ~~1. Learn to write tests in java~~

 ~~1. Learn about git feature branches~~

 ~~1. Learn about packaging~~

###Inside of patchneux###

 ~~1. Moving around the rooms~~

 ~~1. Shortcuts to certain rooms (portals)~~

 ~~1. Items~~

 ~~1. Mapping the neux through a coordinate plane~~

  1. Remote traveling (the metro)
  1. Chat

 ~~1. Create rooms~~

  1. Natural Language Processing 
  1. ASCII Art (?)

 ~~1. Split server and client~~

Helping Out
----------

###Development###

 1. Create an issue (optional)
 1. Clone the codebase
 1. Create a branch
 1. Make your changes
 1. Merge branch
 1. Make a pull request
 1. Write tests

Todo
-----------
* game data creation by file
* unlock a door
* handle Character/NPC class

Changelog
-----------
0.0.5
* unlock doors

0.0.4
* introduced basic test support (using junit 4)
* introduced dynamic data folder support (maybe leading to a config file?)
* locked doors
* teleport (more foundation than for practical, though it can be used in-game)
* give item/recieve item/talk to npc

0.0.3
* basic items (info, pickup, drop)
* transitioned to flexjson from gson
* now have a profile (inventory, some stats)
* removed some bugs (null at end of intro, error at wrong move directions)
* lookaround,aboutme functions
* introduced guide

0.0.2
* you can now move in and out of rooms
* create rooms
* changed syntax for moving around

0.0.1
* split into client and server
* enabled multiple client support
* added nexus game data
* started using ant for packaging

0.0.0
*Initial Commit
