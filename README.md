# LunchVoting

Application is using H2 in memory instance and tomcat7 plugin.

RESTful API is located in classes: AdminController, VotingController and TestController(for tests only, it's not
intended for production builds).
AdminController represents admin console commands which can be used to create and update users and data.
VotingController is available for regular users. It allows to list the restaurants with their menus and vote for them.
TestController has method init which creates roles and the admin user (admin/admin).

To run the application:
mvn clean install tomcat7:run

For testing you will need 2 cookies files: cookiesAdmin.jar and cookiesUser.jar. They can have any names and
can be located where it's best suited to you. These files are required to store session id of authenticated users.

I suggest the following test scenario with according curl commands (it also describes big part of the available API):

1. init - this is test purposes only. It creates an admin and a user account and roles for them:
admin/admin
user/user

curl http://localhost:8080/lunch-voting/test/init

2. login admin

curl http://localhost:8080/lunch-voting/login -c "path/to/your/cookiesAdmin.jar" -d "username=admin&password=admin"

3. create restaurant 1 (r1) - creating the first restaurant

curl -H "Content-Type: application/json" -X POST -d '{"id":null,"title":"test restaurant 1","address":"test address 1","lunchMenu": "{special:100, super:200}"}' http://localhost:8080/lunch-voting/admin/restaurants -b "path/to/your/cookiesAdmin.jar"
for Windows all json double quotes should be escaped (this is true to all the subsequent commands):
curl -H "Content-Type: application/json" -X POST -d "{\"id\":null,\"title\":\"test restaurant 1\",\"address\":\"test address 1\",\"lunchMenu\": \"{special:100, super:200}\"}" http://localhost:8080/lunch-voting/admin/restaurants -b "path/to/your/cookiesAdmin.jar"

4. create restaurant 2 (r2)

curl -H "Content-Type: application/json" -X POST -d '{"id":null,"title":"test restaurant 2","address":"test address 2","lunchMenu": "{special:200, super:500}"}' http://localhost:8080/lunch-voting/admin/restaurants -b "path/to/your/cookiesAdmin.jar"

5. get all restaurants - just to check what is created

curl http://localhost:8080/lunch-voting/voting/restaurants -b "path/to/your/cookiesAdmin.jar"

5.1 remember restaurants ids

6. vote for r1 - admin can vote for a restaurant just as a regular user

curl http://localhost:8080/lunch-voting/voting/1 -b "path/to/your/cookiesAdmin.jar"

7. create user2 - admin can create users

curl -H "Content-Type: application/json" -X POST -d '{"id": null,"name": "user2","password": "user2","roles": [{"id": 2,"type": "ROLE_USER"}],"lastVoted": null}' http://localhost:8080/lunch-voting/admin/accounts -b "path/to/your/cookiesAdmin.jar"

8. login user1 - logging as a regular user (using cookiesUser.jar)

curl http://localhost:8080/lunch-voting/login -c "path/to/your/cookiesUser.jar" -d "username=user&password=user"

9. vote r1, again vote r1 with HttpStatus 409 - Conflict, and vote r2

vote for r1:

curl http://localhost:8080/lunch-voting/voting/1 -b "path/to/your/cookiesUser.jar"

vote for r1 again - should return Http error 409 Conflict:

curl http://localhost:8080/lunch-voting/voting/1 -b "path/to/your/cookiesUser.jar"

vote for r2 - should run without errors but it will perform voting on another restaurant entity which should also decrease
the number of votes for r1 if the user is voting before 11 a.m. since it counts as "he changed his mind". You can check the
state of the restaurants before and after this vote by the command: curl http://localhost:8080/lunch-voting/voting/restaurants -b "path/to/your/cookiesUser.jar".

curl http://localhost:8080/lunch-voting/voting/2 -b "path/to/your/cookiesUser.jar"

10. login user2 - logging as the previously created user

curl http://localhost:8080/lunch-voting/login -c "path/to/your/cookiesUser.jar" -d "username=user2&password=user2"

11. vote r2

curl http://localhost:8080/lunch-voting/voting/2 -b "path/to/your/cookiesUser.jar"

12. get all restaurants

curl http://localhost:8080/lunch-voting/voting/restaurants -b "path/to/your/cookiesUser.jar"

