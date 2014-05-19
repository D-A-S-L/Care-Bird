<?php  
// This service validates login and generates a SessionToken
// Now as a function
// should work by just calling logIn(), it pulls credentials from $_GET
// returns true only if the user is logged in



// Old functionality
// caredb.herokuapp.com/login.php?UName=cdmurphy&Pass=chris&SessionKey='SuperSecret'



// for debugging: echo empty($SessionKey).", ".empty($UName).", ".empty($Pass);
function logIn(){
require 'dbconn.php';
$UName = $_GET["UName"];
$Pass = $_GET["Pass"];
$SessionKey = $_GET["SessionKey"];
	if (empty($SessionKey) and empty($UName) and empty($Pass))
	{	
		pg_close ($conn);
		return false;
	}
	else if(!empty($SessionKey))
	{
		$query="select UName from SessionKeys where SessionKey='$SessionKey';";
		$rTable=pg_query($conn,$query);
		$row = pg_fetch_row($rTable);		
		pg_close ($conn);
		if(empty($row)){return false;}
		else {return true;}//Returns True if valid SessionKey found
	}
	else if(!(empty($UName) or empty($Pass)))
	{
		$query="select Pass from Users where UName='$UName';";
		$rTable=pg_query($conn,$query);
		$row = pg_fetch_row($rTable);
		if($row[0]!=$Pass){
			pg_close ($conn);
			return false;
		} else {
			$query="delete from SessionKeys where UName='$UName';";
			$rTable=pg_query($conn,$query);
			$SessionKey=bin2hex(mcrypt_create_iv(128, MCRYPT_DEV_RANDOM));
			$query="insert into SessionKeys values ('$UName','$SessionKey');";
			$rTable=pg_query($conn,$query);
			// This query can probably be avoided...
			$query="select SessionKey from SessionKeys where UName='$UName';";
			$rTable=pg_query($conn,$query);
			pg_close ($conn);
			//stores a session key & returns the new session key
			$responseArray=pg_fetch_all($rTable);
			print_r(json_encode($responseArray[0]));
			return $responseArray[0]; //Username and Pass were valid, new key created
		}
	} else {pg_close ($conn);return false();}
}
?>