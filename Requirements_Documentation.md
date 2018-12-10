# CSC 380 Software Engineering (Group 10)
###### September 29,2016
## Requirements Document

* *Landon Patmore*
* *Zackary Keller*
* *Sushmita Banerjee*
* *Dominic Dewhurst*
* *Jacob Brooks*

### Use Cases
1. Educators
2. Researchers 3. Students
4. Developers 5. Physicists
6. Astronomers

## Requirements Abstract
TransientGO will be an application that will be used on an Android or iOS platform. The main purpose of this application is to generate enthusiasm and awareness about the LSST project amongst the users. The application lets users create an account so they can “catch” transients on the virtual sky plotted on the device using a sky map. By zooming in on a cluster of stars on the sky map, the user can find and read about that particular transient at that point. Furthermore, the “caught” transients will be saved on the user’s account for future research and perusal. The areas with the most transients would be labelled as “hotspots” that users can potentially “teleport to” so as to collect more transients and increase their rewards.

## Requirements Outline
* Java
    * Eclipse and/or Netbeans
* Backend SQL
     * Database
* Sketch 3
     * Designing the UI Elements for the Application
* Android Studio
     * Link between Java, SQL and UI Elements
* User Registration
     * 3rd Party Services (Google, Facebook, etc.)
* The user must have Android 5.0+.
* Version Control
     * Git protocol (Bitbucket)
* User interface
     * Sky-Maps API
     * Accelerometer control
     * Gyroscope
     * GPS
     * Walkthrough for on-boarding when the user first opens the app

## Questions
1. Brief introduction to transients, astronomical terms.
2. Is there an API that we can use to access the transient information?
3. Are there any preferences for the Graphical User Interface?
4. Would “catching” a transient be uniquely available to the user? (The registration of the transient
would remain with one user but other users would be able to look at it but not “catch” it)
5. What kind of control mechanisms do you want? (Accelerometer or gesture based)
6. What’s the ideal density of transient on the screen at one time?
7. Should the user account be linked to third party sites? (Facebook, Twitter etc..)
8. Should there be multiple modes to the app? (Informative mode or General mode)
9. Is this an app that's going to be used mostly at night? Night vision?
10. “Teleport” feature that lets you access the skies of various places?
11. Would there be a leaderboard function?
12. Is there a picture associated with each transient?
13. Is the data stream in dumps or a live stream through the night?
