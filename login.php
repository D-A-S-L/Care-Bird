<?php  
// This service validates login and generates a SessionToken
// Now as a function
// should work by just calling logIn(), it pulls credentials from $_GET
// Upon successful login returns a SessionToken, as a standard string.

// Functionality:
// caredb.herokuapp.com/login.php?UName=cdmurphy&Pass=chris'

// for debugging: echo empty($SessionToken).", ".empty($UName).", ".empty($Pass);

// Test with curl -d "param1=value1&param2=value2" http://example.com/base/


require 'loginDefines.php';
$key=logIn();
if($key){
	echo $key;
}
else {
	echo deliver_response(404, "Log In Error", false);
}
?>