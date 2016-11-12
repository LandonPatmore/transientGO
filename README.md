# TransientGo

## Synopsis and Motivation
* The main purpose of this application is to generate enthusiasm and awareness about the LSST project amongst the users.

## Description
* The application lets users create an account so they can “catch” transients on the virtual sky plotted on the device using a sky map. By zooming in on a cluster of stars on the sky map, the user can find and read about that particular transient at that point. Furthermore, the “caught” transients will be saved on the user’s account for future research and perusal. The areas with the most transients would be labelled as “hotspots” that users can potentially “teleport to” so as to collect more transients and increase their rewards.

### Environment
* Android 5.0+

### Development Tools
* Android Studio
* Atom Text Editor
* Eclipse/Netbeans

### Data Sources
* [Caltech MLS Telescope Data](http://nesssi.cacr.caltech.edu/MLS/CRTSII_Allns.html)
* [Comet Transient Broker](http://comet.transientskp.org)

### Languages
* Java (API 19)
* SQL
* Python

# How to Contribute
**First Time Usage**
1. Fork the project onto your personal profile by clicking `fork` at the top of this page and selecting your own profile.

```sh
- cd [directory]
- git config --global user.email "EMAIL USED FOR GITLAB"
- git config --global user.name "NAME ON GITLAB PROFILE"
- git clone [HTTPS URL at top of repo]
- git remote add upstream https://gitlab.com/CSC380Team10Repo/CSC380Team10Repo.git
```

**Syncing Local and Fork (DO BEFORE ANYTHING)**
```sh
- git checkout master
- git fetch upstream master
- git rebase upstream/master
- git push origin master
- git status
```

**After First Time**
```sh
- git status
- git add .
- git commit -m "MESSAGE"
- git push origin master
- git status
```
1. Go back onto GitLab website and go to the tab that says `Merge Requests`.
2. Make a new merge request and select source branch and target branch.
3. Add a title and description of the merge and then submit it.
4. Your merge will be looked over and accepted or asked to be edited.
5. **It will need to be accepted by atleast 1 person to be merged into the upstream.**
6. Landon Patmore will then approve the Merge Request.

# Contributors:

* **Sushmita Banerjee** - *sbanerje@oswego.edu* **(Developer)**

* **Zackary Keller** - *zkeller@oswego.edu* **(Developer)**

* **Landon Patmore** - *lpatmore@oswego.edu* **(Developer)**

* **Jacob Brooks** - *jbrooks2@oswego.edu* **(Developer)**

* **Dominic Dewhurst** - *ddewhurs@oswego.edu* **(Developer)**

* **Dr. James Early** - *james.early@oswego.edu* **(Advisor)**

* **Dr. Shashi Kanbur** - *shashi.kanbur@oswego.edu* **(Advisor)**

* **Ashish Mahabal** - *aam@astro.caltech.edu* **(Advisor)**

* **Andrew Drake** - *ajd@astro.caltech.edu* **(Advisor)**

## Licenses
* MIT License
