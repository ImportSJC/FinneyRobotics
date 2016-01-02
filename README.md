# FinneyRobotics
Code for Finney Robotics Team

<h2>Steps for pulling the auto code down into a eclipse project:</h2>

- Create a new "Robot Java" Project in eclipse
- Give it a name and make it an iterative robot
- Now "cd" into the project folder in Git Bash
- Create your git repository here.
	- git init
	- git remote add origin <url>
- Delete the local files/folders that are already in the git repo
	- src/
	- build.properties
	- build.xml
- Pull the remote files onto the local machine
	- git pull origin
	- git pull origin <branch>
- You can double check that everything worked by performing a "git status" on
  your local repo and you should have a clean working directory.
- Refresh your eclipse project.
