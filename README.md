# Midterm Project - To-Do List App
## Purpose: Provide a quality application for tracking, editing, and viewing tasks
### Authors: Timothy Gilmore, Sehoon Park

Features:
- Clean view of the tasks
- Add new tasks
- Limits tasks to 7 at a time

App Screenshot:

Requirements:
- The tasks need to be shown in a RecyclerView.
- No empty task slot should be shown on the screen.
- An add button allows the user to enter a new task using a different activity.
- The user input should be validated. a maximum of 25 characters is allowed.
- In the home screen, if all the seven slots are used, the add button is replaced with text saying: "Too much work!".
- Upon clicking a task, a new screen opens and shows the task name in addition to other four buttons, that are: Mark as complete, Cancel, Previous task, and Next task.
- The Previous task, and Next task buttons allow the user to navigate the tasks without returning back to the home screen.
- The Cancel button closes the current activity. The same functionality is expected in the task addition activity.
- The Mark as complete button, closes the current screen and removes the task from the to-do list.
- The app includes "About Activity", that provides information about the app and its two developers. This activity is accessible using a button from the home screen.
- The task activity and the about activity should follow the UI design provided in the attached app diagram below.
- Develop the UI using XML layouts.
- Choose a launcher icon for the App.
- Rotating the device does not show different UI components orientation.
- Rotating the device keeps the data shown in the screen as is.
- Every time the user opens the app, the last saved tasks should appear.
- Provide sufficient comments to explain your Kotlin code.
- Add both TAs and me as contributors to your project.
- Both group members are expected to contribute to the GitHub repository.
- Provide a ReadMe file in your GitHub repository.
- Add a screenshot of the app to your repo. Show the screenshot in the ReadMe file.