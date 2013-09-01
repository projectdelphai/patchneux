patchneux
===========
Reuben Castelino - projectdelphai@gmail.com

Description
-----------

###Name###
patchneux pronunciation - either patchnew, patchno, patchnook, or patchnooks in order of best to worst. You can pronounce it however you look, but this standard will mean that there will be less confusion when talking about it.

The neux part came from my inability to spell nexus when typing fast. Since my fingers naturally gravitated towards neux, that's what i changed it to. Patch comes from the fact that patchneux is basically a patchwork world in which everything neux is different and can be modded or redesigned.

I combined them to make patchneux (rather than neux or patchworld which my first attempts) because the google results (for discoverability purposes) are more scarse. The only conflict is the QT neux library which isn't too big (compared to Google's nexus). 

Since patchneux is up to the users, if there is overwhelming support for a new name, work to change it.

###About###

patchneux is designed to be a virtual world in which almost everything is subject to the whim of the user. It will take a long time to achieve that state, but that is the end goal. Every commit should be designed to further that goal.

While modelled like a game, I am trying to make it bigger than just a pure entertainment project. I would like to be able to seamlessly interface with many systems (be that a virtual library, a music player, irc adapter etc). I'm not at that stage yet.

If I had to compare it to something, I would say that the end product should be a mixture of the world from [Ready Player One](https://en.wikipedia.org/wiki/Ready_Player_One), the world of [Neuromancer](https://en.wikipedia.org/wiki/Neuromancer), the world of [the Matrix](https://en.wikipedia.org/wiki/The_Matrix), the world from [Summer Wars](https://en.wikipedia.org/wiki/Summer_Wars), and Jarvis from the Iron Man movies. 

Code
----------

patchneux is my first project written in Java and is my way of figuring out the language. I do have experience in ruby and python, but I am inexperienced with compiled languages. So this is a test for me, however I plan to stay with patchneux long after I'm done with Java.

This does mean, however, that I will make mistakes. And many of them. Bear with me and feel free to correct me and/or make pull requests when I do something wrong.

And since this is a patchwork world, I want to allow for plugins, extensions, and modules(?) that can be written in different languages. 

The way I am currently building patchneux is through a server/client method EVEN LOCALLY. This means that a patchneux server holds all the commands and the thinking, but the user never works with the server. Different clients are built to interact with the server ranging from the easiest (command line prompt) to the harder (graphical art with gui) to the hardest (oculus rift virtual graphics or verbal command prompt). I will be focusing more on the server than the clients through I may try my hand at some if no one steps up to try them. 

For now, the server and the client will be merged, but as soon as I decide the best way to develop the split, I will split them. Be forewarned that the default client will be VERY basic and probably complicated to learn since I will just be using it to test new features in the patchneux server.

Quick-Use
---------

To compile the java file, run 

     javac patchNeux.java

Then to run it, use 
     
     java patchNeux

Goals
--------

###Outside of patchneux###

 1. Learn about lower-level programs
 1. Write better docs
 1. Learn about game programming
 1. Learn about multiplayer networking
 1. Learn to write tests in java

###Inside of patchneux###

 1. Moving around the rooms
 1. Shortcuts to certain rooms (portals)
 1. Items
 1. Dynamic rooms
 1. Mapping the neux through a cartesian plane
 1. Remote traveling (the metro)
 1. Chat
 1. Create rooms
 1. Natural Language Processing 
 1. ASCII Art (?)
 1. Split server and client

Helping Out
----------

###User Base###

Since this is a more personal project of mine, I may be too busy to market and instigate a community. But while I may not start them, I do encourage them, support them, and will definitely participate in them.

 1. Create a community
     1. Make a forum!
     1. Talk on IRC! (I do have a channel on irc.foonetic.net at #starkin, but it is more for my personal use than for patchneux. Make a #patchneux!)
     1. Make a subreddit!
 1. Offer suggestions
     1. Create issues
     1. Email me with ideas
 1. Play around with patchneux and explore!

Since I am a beginner as well, I would love for other interested people to contribute even if they don't know how. I just ask that you bother to google any simple questions you might have about java (since stackoverflow can definitely answer better than me). I also ask that you don't say you will do something and then never do it. Commit to it if you voluntter. Walk the walk and all that.

###Development###

 1. Create an issue (optional)
 1. Grab the codebase
 1. Make your changes
 1. Make a pull request
 1. Write tests (optional but recommended for now)


