# CS-360
Android Application Development

• <b> Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?</b>

 &emsp;&emsp;The requirements were to use a DB to store user information and display the weight, adding a goal weight feature. The app also required a screen for logging into the app and a mechanism to send a notification when the user reached a goal weight. The users’ needs addressed were the ability to track and journal their progress using a line graph to visually see their progress. Users could set a goal weight when the account was created, and it displayed this goal weight each time a new weight was added.

• <b>What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?</b>

  &emsp;&emsp;The screens used included a login screen and a main tracking screen. The main tracking screen displayed a line graph and a recycler view for scrolling through previous and current weights, allowing users to delete entries. A navigation menu was also included on this screen for logging out or navigating to the enable SMS feature. This feature enabled SMS notifications upon reaching the goal weight. If not enabled, it didn't affect the app's functionality. The design prioritized user-friendly navigation and visual representation of progress, making the app intuitive and successful in addressing user needs.

•<b> How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?</b>

 &emsp;&emsp;Design was crucial in adding the intended functionality. Understanding the layout and user requirements was vital. In future projects, this approach can be applied by maintaining a clear understanding of user needs and ensuring the design aligns with those needs to create a more effective and user-friendly app.

• <b>How did you test to ensure your code was functional? Why is this process important and what did it reveal?</b>

  &emsp;&emsp;To test the code, I wrote tests with hardcoded values for the DB and attempted to retrieve them. This process revealed that one of my queries returned an incorrect value. I logged data using the LogD function throughout all functions to identify bugs and ensure correct values were returned. After verifying the functions, I tested them in the main application to confirm everything worked as intended. Testing is crucial to identify and fix bugs, ensuring the app functions correctly and meets user expectations.

•<b> Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?</b>

  &emsp;&emsp;The biggest challenge was time constraints. To overcome this challenge, I scaled down some functionality while ensuring it still met customer requirements. Innovation was necessary in prioritizing essential features and optimizing the development process to deliver a functional app within the given timeframe.

• <b>In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?</b>

  &emsp;&emsp;In this app, I showcased my ability to apply documentation to my application effectively. I achieved this by integrating the MPCharts library, enabling me to add a line graph to the app, demonstrating my skills in leveraging external libraries to enhance app functionality and user experience.
