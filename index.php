<?php
echo 'index.php is nothing. I use it for testing';
require 'login.php';
$loggedIn=logIn();
echo $loggedIn;

echo "
<br/><br/><br/>
	Updates to add to commit 1:00AM :<br/>
	- login.php changed to be a php function: logIn()
	  The plan is to have log-in logic on relevant webserver requests. Just include the Token in the url for me to pull with \$_GET['SessionKey']
	  Alternatively you can just always use the username and password and it should return a session key. This is an unintended 'feature' I wouldn't suggest going for it.
	  Only a true logIn should be accepting usernames and passwords; everything else should want a token.
	  This still requires some tweaking but if you want to play with the sessionKeys just make a call to:<br/>
	  caredb.herokuapp.com/loginCopy?UName=username&Pass=password<br/>
	  caredb.herokuapp.com/loginCopy?SessionToken='someToken'

	  
	  <br/><br/><br/>
	Updates to add to commit 12:00AM :<br/>
	-Offline build environment is working well<br/>
	-php queries not related to app functionality (such as those that let you see what just happened in the database) have been moved to /devutils<br/>
	 For example you can access them at:
	 caredb.herokuapp.com/devutils/Users.php
	 caredb.herokuapp.com/devutils/Reset.php
	 but app related functions have been left unchanged
	 For example:
	 caredb.herokuapp.com/login.php?SessionToken=someBadassToken
";


echo '<br/><br/><br/>';
$token = bin2hex(mcrypt_create_iv(128, MCRYPT_DEV_RANDOM));
echo $token."\n";
echo "Now I mess with the db offline and update it online when good changes are made";
?>