README
Group f262-01p1d: Geoff Berl, Piper Chester, Colin Ferris, Ty Kennedy, Allen Thomas

Functional Limitations:
	Studies aren’t being saved

Settings:
	Currently the settings file is being saved in the root directory of the project

How to run:
The program comes bundled as a .jar file that can be run from the command line with the command: java -jar filename

How to use:
Open:
If a default study was previously selected, the default study will open.  If, however, a default study was not previously selected you will be prompted with a list of studies.

Select Study:
To select a study, highlight a study from the list, choose whether or not you want to see the study as a default automatically upon opening the application in the future, then click the “Open” button.

Viewing a Study:
Once a study has been selected there are several command buttons in the toolbar at the top of the application.
Prev: This button will move to the previous image(s) in the study.

Open: This button will present a list of studies for the user to select. The user can then select a study, and check a checkbox to select if this study should serve as the default study on load. When the OK button has been pressed, the popup will dismiss and the user will be presented with the first image of the study.

Save View: This button will save the current image and view type being viewed for the current study.  That is to say that if you are viewing Images 4 - 8 of the “Lung” study in the four image view, the next time you open this view it will automatically open to images 4 - 8 of the “Lung” study.

Save Study: This button is currently not functional.  However, its intended use is to allow a user to save the current view as a new study or overwrite the current study.

View: This button will present the current image of the study, and the next 3 images of the study in a 2x2 presentation view. If pressed again, it will present the first image of the study as a single view. This button toggles between the single and the 2x2 views for the images in the study.

Next: This button will move to the next image(s) in the study.  If viewing in the four image view and the last set of images is not an increment of four, there will be blank placeholders for where those images would normally be.
