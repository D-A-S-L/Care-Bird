<?php  
// This service validates login and generates a SessionToken
// Now as a function
// should work by just calling logIn(), it pulls credentials from $_GET
// Upon successful login returns a SessionKey, as a standard string.

// Functionality:
// caredb.herokuapp.com/login.php?UName=cdmurphy&Pass=chris'

// for debugging: echo empty($SessionKey).", ".empty($UName).", ".empty($Pass);

// Test with curl -d "param1=value1&param2=value2" http://example.com/base/


$status=0;
$statusMessage="Default Message";
$data=null;
require 'dbconn.php';
	if($_POST["UName"] and $_POST["Pass"])
	{		
		$UName = $_POST["UName"];
		$Pass = $_POST["Pass"];
		
		// Find out the password associated with that user
		$query="select Pass from Users where UName='$UName';";
		$rTable=pg_query($conn,$query);
		$row = pg_fetch_row($rTable);
		
		//  Bad Username/Password Combination
		if(empty($rTable) or ($row[0]!=$Pass)){
			$status =204;
			$status_Message = "No user found with that combination";
			$data=false;
		}
		// Good Username/Password Combination
		else {
			// User and Pass validated, Delete old SessionKey
			$query="delete from SessionKeys where UName='$UName';";
			$rTable=pg_query($conn,$query);
			$SessionKey=bin2hex(mcrypt_create_iv(128, MCRYPT_DEV_RANDOM));			
			
			//stores a session key & returns the new session key
			$query="insert into SessionKeys values ('$UName','$SessionKey');";
			$rTable=pg_query($conn,$query);
			
			$status=200;
			$status_Message="User logged in.";
			$data=$SessionKey;
			
		}
	}
	// Username or Password were not entered
	else {
		$status=400;
		$data=false;
	}
	
pg_close($conn);

/* Deliver the response now */

header("Content-Type: application/json");
deliver_response($status, $statusMessage, $data);







?>