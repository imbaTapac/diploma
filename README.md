# Student Rating
## This project should facilitate the calculation of the student's rating for receiving a scholarship.

### Current version : 0.8.RELEASE

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
