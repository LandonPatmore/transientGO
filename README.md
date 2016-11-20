# TransientGo

## Synopsis and Motivation
* The main purpose of this application is to generate enthusiasm and awareness about the LSST project amongst the users.

## Description
* TransientGO is a competitive, single-player, mobile game developed generate awareness and enthusiasm for the LSST Telescope. Our team’s implementation is built on the Google Stardroid API, which provides the main backbone of the app. We have stripped out all unnecessary features and functionality to provide a clean and uncluttered slate for our app. Center to the apps functionality is the added ability to plot transient objects that are received from an outside source into stardroid. The main gamification aspect of the app is the collection of these transient objects, to a user's “profile”. Users will use their phones to search around the sky looking for the effect that we have created for the transients (a slow pulsation from black to a color that can be set based on type). The user will then have to keep the object centered inside of a circle for a set threshold of time before it is registered as caught to the users profile. A window with relevant information about that object will then pop-up on the screen informing them about the specific characteristics of the object, along with a picture. The challenge comes from having a steady hand and distinguishing a transient from other astronomical bodies plotted to the screen. The competitive aspect comes from the points earned by catching the transients. A leader board will be integrated showing the current top scoring users and their points.

### Environment
* Android 5.0+

### Development Tools
* Android Studio
* Atom Text Editor
* Eclipse/Netbeans

### Data Sources
* [4 Pi Sky API](http://voeventdb.4pisky.org/apiv1/)

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
- git clone [HTTPS URL at top of your fork's repo]
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

* **Landon Patmore** - *lpatmore@oswego.edu* **(Project Lead Developer)**

* **Jacob Brooks** - *jbrooks2@oswego.edu* **(Developer)**

* **Dominic Dewhurst** - *ddewhurs@oswego.edu* **(Developer)**

* **Dr. James Early** - *james.early@oswego.edu* **(Advisor)**

* **Dr. Shashi Kanbur** - *shashi.kanbur@oswego.edu* **(Advisor)**

* **Ashish Mahabal** - *aam@astro.caltech.edu* **(Advisor)**

* **Andrew Drake** - *ajd@astro.caltech.edu* **(Advisor)**

## Licenses
* MIT License
