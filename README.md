# Odyssey

## Synopsis and Motivation
* The main purpose of this application is to generate enthusiasm and awareness about the LSST project amongst the users.

## Description
* The application lets users create an account so they can “catch” transients on the virtual sky plotted on the device using a sky map. By zooming in on a cluster of stars on the sky map, the user can find and read about that particular transient at that point. Furthermore, the “caught” transients will be saved on the user’s account for future research and perusal. The areas with the most transients would be labelled as “hotspots” that users can potentially “teleport to” so as to collect more transients and increase their rewards.

## Environment
* Android 5.0+

## Development Tools
* Android Studio
* Atom Text Editor
* Eclipse/Netbeans

## Data Sources
* [Caltech MLS Telescope Data](http://nesssi.cacr.caltech.edu/MLS/CRTSII_Allns.html)
* [Comet Transient Broker](http://comet.transientskp.org)

## Languages
* Java (API 20)
* SQL
* Python

## How to Contribute
**First Time Usage**
1. Fork the project onto your personal profile by clicking `fork` at the top of this page and selecting your own profile.
2. After it brings you to your profile with the forked repo, go to terminal/command line.
3. `cd` into the directory you want the folder to be placed and then type `git clone <HTTPS URL at top of repo>`.
4. `git remote add upstream https://gitlab.com/CSC380Team10Repo/CSC380Team10Repo.git`

**Syncing Local and Fork (DO BEFORE ANYTHING)**
1. `git checkout master` to change to the master branch if you are on another branch
2. `git pull upstream` pulls and merges changes from upstream to local repo
5. `git push origin master` pushes the changes from local repo master to forked origin
6. `git status` checks the status of git

**After First Time**
1. After contributing, open terminal/command line and `cd` into the folder directory.
2. Type `git status` to check the status of the local repository.
3. Type `git add .` to add all the files for staging.
4. Type `git commit -m "<message>"` to commit the files to be uploaded to forked repo (your personal copy of the repo).
5. Type `git push origin master` to push the files to the forked repo (personal copy).
6. Go back onto GitLab website and go to the tab that says `Merge Requests`.
7. Make a new merge request and select source branch and target branch.
8. Add a title and description of the merge and then submit it.
9. Your merge will be looked over and accepted or asked to be edited.

## Contributors:

* **Sushmita Banerjee** - *sbanerje@oswego.edu* **(Developer)**

* **Zackary Keller** - *zkeller@oswego.edu* **(Developer)**

* **Landon Patmore** - *lpatmore@oswego.edu* **(Developer)**

* **Jacob Brooks** - *jbrooks2@oswego.edu* **(Developer)**

* **Dominic Dewhurst** - *ddewhurs@oswego.edu* **(Developer)**

* **Dr. James Early** - *james.early@oswego.edu* **(Advisor)**

* **Dr. Shashi Kanbur** - *shashi.kanbur@oswego.edu* **(Advisor)**

* **Ashish Mahabal** - *aam@astro.caltech.edu* **(Advisor)**

## Licenses
* MIT License
