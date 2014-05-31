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
	$status=400;
	$statusMessage="Default Message";
	$data=null;
		if($_POST["UName"] and $_POST["Pass"])
		{		
			$UName = $_POST["UName"];
			$Pass = $_POST["Pass"];
			
			// Find out the password associated with that user
			$query="select Pass from Users where UName='$UName';";
			$result=pg_query($conn,$query);
			$row = pg_fetch_row($result);
			
			//  Bad Username/Password Combination
			if(empty($result) or ($row[0]!=$Pass)){
				$status =401;
				$statusMessage = "No user found with that combination";
				$data=false;
			}
			// Good Username/Password Combination
			else {
				// User and Pass validated, Delete old SessionToken
				$query="delete from SessionTokens where UName='$UName';";
				$result=pg_query($conn,$query);
				$SessionToken=bin2hex(mcrypt_create_iv(128, MCRYPT_DEV_RANDOM));			
				
				//stores a session key & returns the new session key
				$query="insert into SessionTokens values ('$UName','$SessionToken')
						returning UName;";
				$result=pg_query($conn,$query);			
			
				if(pg_affected_rows($result)<0){
					$status=400;
					$statusMessage="An error occured";
					$data=$result;
				}else{
					$user=pg_fetch_row($result);
					$user=$user[0];  
					if ($UName==$user){
						$query = "select UName,FName,LName, '' as Pass, '$SessionToken' as token, PhoneNum from Users
						where UName='$UName';";			
						$result=pg_query($conn,$query);	
						$status=202;
						$statusMessage="User logged in.";
						$data=pg_fetch_all($result);
					}
					else{
						$status=400;
						$statusMessage="An error occured";
						$data=$result;
					}
				}
				
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
	$status=400;
	$statusMessage="To be logged in, must use SessionToken in POST";
	$data=null;
		if($_POST["SessionToken"])
		{		
			$SessionToken = $_POST["SessionToken"];
			
			// Find out if that SessionToken is in use
			$query="select UName from SessionTokens where SessionToken='$SessionToken';";
			$result=pg_query($conn,$query);
			$row = pg_fetch_row($result);
			
				//  Bad Session Key
				if(empty($result)){
					$status =401;
					$status_Message = "Invalid Session Key";
					$data=false;
				}
				// Good SessionToken
				else {
					$status=202;
					$status_Message="User logged in.";
					$data=true;
					
				}
		}
		// SessionToken was not entered
		else {
			$status=400;
			$data=false;
		}	
	pg_close($conn);
	return deliver_response($status,$statusMessage,$data);
}

?>