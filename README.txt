This is intended to be the repository for hosting a PostgreSQL database for our CareBird app.

The PostgreSQL database is a heroku addon described by this page:
https://devcenter.heroku.com/articles/heroku-postgresql
>>>>>>> fabcae3b4c83da9f04acb89d9adf1c56390c843c

The webservice is under development and the API is changing rapidly.

contact Chris Murphy for any questions for now.

Webservices that are in the prototype stage are

https://caredb.herokuapp.com/addUser.php
currently requires: 
User, Pass, FName, LName
to add a new user to the USERS table.
Currently not checking for valid entries...

https://caredb.herokuapp.com/addCareGiver.php
Currently Requires:
CRID=Username&CRPass=Pass
&Token="SomeUniqueToken"

https://caredb.herokuapp.com/addCareReceiver.php
Currently Requires:
CRID=Username
&CGID=Username&CGPass=Pass
&Token="TokenReceivedFromQRCode

Some TEMPORARY web services you can use for testing are:
https://caredb.herokuapp.com/devutils/Users.php
https://caredb.herokuapp.com/devutils/CanCareFor.php
https://caredb.herokuapp.com/devutils/QRTokens.php
https://caredb.herokuapp.com/devutils/Reset.php # Which will drop all dables and reset the database to a default (empty) state.
