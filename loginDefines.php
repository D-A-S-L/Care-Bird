<?php  
// This service validates login and generates a SessionToken
// Now as a function
// should work by just calling logIn(), it pulls credentials from $_POST
// Upon successful login returns a SessionToken, as a standard string.

// Functionality:
// caredb.herokuapp.com/login.php?UName=cdmurphy&Pass=chris'

// for debugging: echo empty($SessionToken).", ".empty($UName).", ".empty($Pass);

// Test with curl -d "param1=value1&param2=value2" http://example.com/base/
require 'dbconn.php';
function logIn(){	
	$conn = connect();
	$status=0;
	$statusMessage="Default Message";
	$data=null;
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
				$status =400;
				$status_Message = "No user found with that combination";
				$data=false;
			}
			// Good Username/Password Combination
			else {
				// User and Pass validated, Delete old SessionToken
				$query="delete from SessionTokens where UName='$UName';";
				$rTable=pg_query($conn,$query);
				$SessionToken=bin2hex(mcrypt_create_iv(128, MCRYPT_DEV_RANDOM));			
				
				//stores a session key & returns the new session key
				$query="insert into SessionTokens values ('$UName','$SessionToken');";
				$rTable=pg_query($conn,$query);
				
				$status=200;
				$status_Message="User logged in.";
				$data=$SessionToken;
				
			}
		}
		// Username or Password were not entered
		else {
			$status=400;
			$data=false;
		}	
	pg_close($conn);
	return deliver_response($status,$statusMessage,$data);
}

function loggedIn(){
	$conn = connect();
	$status=0;
	$statusMessage="Default Message";
	$data=null;
		if($_POST["SessionToken"])
		{		
			$SessionToken = $_POST["SessionToken"];
			
			// Find out if that SessionToken is in use
			$query="select UName from SessionTokens where SessionToken='$SessionToken';";
			$rTable=pg_query($conn,$query);
			$row = pg_fetch_row($rTable);
			
				//  Bad Session Key
				if(empty($rTable)){
					$status =204;
					$status_Message = "Invalid Session Key";
					$data=false;
				}
				// Good SessionToken
				else {
					$status=200;
					$status_Message="User logged in.";
					$data=true;
					
				}
		}
		// Username or Password were not entered
		else {
			$status=400;
			$data=false;
		}	
	pg_close($conn);
	return deliver_response($status,$statusMessage,$data);
}

?>