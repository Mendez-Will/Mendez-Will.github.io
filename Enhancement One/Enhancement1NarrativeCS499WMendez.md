**Software Design and Engineering Narrative**

By Wilfredo Mendez

This artifact is an event notification app I originally developed during my CS360: Mobile Architect & Programming course. It 
allows users to store event details, receive notifications based on those events, and protects this data behind a login screen 
for privacy. I chose this artifact for my ePortfolio because when I submitted the application, I felt that the project had 
many areas for improvement. Revisiting it allowed me to showcase my growth as a developer, both in terms of my problem-solving 
skills and my ability to build a complete, full-stack application from the ground up.

In the enhancement I demonstrate my UI/UX skills through the redesign and implementation of updates across the application 
screens. I also showcase my back-end development abilities by utilizing multiple local databases simultaneously throughout the 
application. The updated code for the databases shows my ability to put security first, as I use parameterized queries to 
prevent SQL injection attacks instead of magic numbers, prevent brute force attacks with attempt tracking, encrypt login 
credentials, and separate the event and login databases for data separation.

Enhancements included updated comments throughout all files for clarity, a redesigned UI to enhance usability, an EditEvent 
screen utilizing spinners for consistent input, improved iconography, a restructured SQL database to hold only event details 
while creating a separate database for login credentials, a robust login attempt tracking feature, secure hashing of 
credentials, fixed SMS permission requests, enabled notifications for devices with API 26 or later, and improved the central 
RecyclerView layout to display events immediately after creation.

Reflecting on the enhancement process, I learned the importance of clear and distinct naming for classes, as ambiguity in the 
original project cost me a lot of time and was very confusing to work with at times. Testing and optimizing the code using 
feedback on every step of a loop was another very good lesson. For example, I encountered challenges with displaying events 
from the database because I discovered that a class responsible for loading events was mistakenly clearing the data in the 
loop that held the data. Once I corrected that, everything functioned as expected, and I only found this by implementing 
testing on all parts of the loop instead of just test cases, which I was originally doing. I faced issues in requesting 
permissions from the phone for SMS notifications, which I resolved by switching to system-based notifications. These 
challenges taught me the importance of thoroughly testing and refining both the front-end and back-end aspects of my 
applications.

Overall, this enhancement showcases my ability to work with SQL local databases, ensure login security, design UI, and 
integrate the database and UI for a cohesive application. This artifact demonstrates my ability to meet outcomes three and 
five, which relate to designing and evaluating computing solutions, and developing a security mindset.

Course Outcome Three; Design and evaluate computing solutions that solve a given problem using algorithmic principles and 
computer science practices and standards appropriate to its solution while managing the trade-offs involved in design choices. 
This outcome was met during the app rework by reworking all UI in the application, and critically assessing how data storage 
and retrieval were implemented. I made conscious design choices that balanced efficiency and security with ease of use, 
ensuring that the application could handle multiple data operations without compromising performance or the user experience.

Course Outcome Five; Develop a security mindset that anticipates adversarial exploits in software architecture and designs to 
expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources. This 
outcome was met during the app rework by implementing robust security measures, including login attempt tracking, hash 
encryption, and the creation of separate databases for event and login credentials. These improvements reflect a proactive 
approach to security, emphasizing my commitment to safeguarding user data and ensuring application integrity.
