# Student Rating
## This project should facilitate the calculation of the student's rating for receiving a scholarship.

### Current version : 1.0.RELEASE (build 3055)

Change list:  

Version 0.7.RELEASE : 
- student saves in session;
- fixed issue with incorrect result on POST request in apis;
- fixed issue with current rating status;
- added DTO mapper to converting entity to DTOs;
- added integration with LDAP server to registry new students;

Version 0.8.RELEASE :
- migrating to Spring Boot;
- code refactoring;
- added ContextHolder to hold LDAPAttributes and Student while registration in process;
- returned SHAPasswordEncoder (deleted in 4.3.1 Spring ver);
- Student bounded with Specialty;
- Specialty is required in registration process;
- added special code to determine the role of the student which is registering (default STUDENT).
- fixed issue when when to the user shows the report and then he clicks to get another report, the previous not cleaned and not hidden.
- removed registration logic from controller to service.
- small bug and code fixes.

Version 0.9.RELEASE :

- clarified message when student ratings is not declined;
- implemented new page "my-rating" where student can see his rating in scope month;
- few bug-fixes in report service (work in progress);
- now rejecting process of student ratings works as must.
- now head of group/so can approve/reject or leave unchanged student ratings;
- fixed problem where status of approve student ratings shows incorrect (test in progress);
- implemented REST-request to see current application version and build number;
- refactored RatingDTO due implementing new process of approving student ratings;
- fixed and refactored queries which bound with rating;
- fixed sql-insert static data statement because some statement have duplicated items which violated process of application work.

Version 1.0.RELEASE :

- reworked report service and controller to return Resource by ResponseEntity;
- decrease in student binding to the session (new DTO instead of entity);
- fixed incorrect display student rating stage of approve for all system participants;
- added application event logging feature for log in, rating fill, logout;
- added new handlers in SecurityConfig to realization feature above;
- session control via cookies;
- fixed bug when the user via API (Postman,CurL) can post any grade of paragraph in rating saving flow;
- new application banner;
- added error controller for 403,404 HTTP status;
- added exception handler for the application exception via ErrorInfoDTO;
- added DateTime utils to simplify work with date/time and to avoid code duplication;
- code refactoring and small bug fixing;
