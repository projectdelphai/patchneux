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

    gright, gleft, gahead, gbehind

To create rooms:

    cr

###Bugs###

 1. Room names may not yet contain any spaces.
 1. If you type g then a word that is not an accepted direction, the server will crash. Just reset both server and client.

Code
----------

patchneux is my first project written in Java and is my way of figuring out the language. I do have experience in ruby and python, but I am inexperienced with compiled languages. So this is a test for me, however I plan to stay with patchneux long after I'm done with Java.

This does mean, however, that I will make mistakes. And many of them. Bear with me and feel free to correct me and/or make pull requests when I do something wrong.

And since this is a patchwork world, I want to allow for plugins, extensions, and modules(?) that can be written in different languages. 

The way I am currently building patchneux is through a server/client method EVEN LOCALLY. This means that a patchneux server holds all the commands and the thinking, but the user never works with the server. Different clients are built to interact with the server ranging from the easiest (command line prompt) to the harder (graphical art with gui) to the hardest (oculus rift virtual graphics or verbal command prompt). I will be focusing more on the server than the clients through I may try my hand at some if no one steps up to try them. 

Be forewarned that the default client will be VERY basic since I will just be using it to test new features in the patchneux server.

Goals
--------

###Outside of patchneux###

  1. Learn about lower-level programs
  1. Write better docs
  1. Learn about game programming
  1. Learn about multiplayer networking
  1. Learn to write tests in java

 ~~1. Learn about packaging~~

###Inside of patchneux###

 ~~1. Moving around the rooms~~

  1. Shortcuts to certain rooms (portals)
  1. Items
  1. Dynamic rooms

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
 1. Grab the codebase
 1. Make your changes
 1. Make a pull request
 1. Write tests (optional but recommended for now)

Changelog
-----------
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
