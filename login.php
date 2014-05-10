<?php  
// This service validates login and generates a SessionToken
// caredb.herokuapp.com/login.php?UName=cdmurphy&Pass=chris&SessionKey='SuperSecret'

require 'dbconn.php';
header("Content-Type: application/json");
$UName = $_GET["UName"];
$Pass = $_GET["Pass"];
$SessionKey = $_GET["SessionKey"];

// for debugging: echo empty($SessionKey).", ".empty($UName).", ".empty($Pass);

if (empty($SessionKey) and empty($UName) and empty($Pass))
{
	deliver_response(200, "User is not logged in", null);
}
else if(!empty($SessionKey))
{
	$query="select UName from SessionKeys where SessionKey='$SessionKey';";
	$rTable=pg_query($conn,$query);
	$row = pg_fetch_row($rTable);
	if(empty($row)){deliver_response(200, "User is not logged in", null);}
	else {deliver_response(200, "User is logged in", $rTable);}//Returns Username if found
}
else if(!(empty($UName) or empty($Pass)))
{
	$query="select Pass from Users where UName='$UName';";
	$rTable=pg_query($conn,$query);
	$row = pg_fetch_row($rTable);
	if($row[0]!=$Pass){
		deliver_response(200, "Username/password combo Not found", null); 
	} else {
		$query="delete from SessionKeys where UName='$UName';";
		$rTable=pg_query($conn,$query);
		$SessionKey=bin2hex(mcrypt_create_iv(128, MCRYPT_DEV_RANDOM));
		$query="insert into SessionKeys values ('$UName','$SessionKey');";
		$rTable=pg_query($conn,$query);
		// This query can probably be avoided...
		$query="select SessionKey from SessionKeys where UName='$UName';";
		$rTable=pg_query($conn,$query);
		//stores a session key & returns the new session key
		deliver_response(200, "New session key created", $rTable);
	}
} else {deliver_response(200,"Invalid authentication", null);}
pg_close($conn);
?>