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
	$query="select SessionKey from SessionKeys where SessionKey='$SessionKey';";
	$rTable=pg_query($conn,$query);
	$row = pg_fetch_row($rTable);
	if(empty($row))
	{
		deliver_response(200, "User is not logged in", $rTable);//Returns null
	}
	else {deliver_response(200, "SessionKey response", $rTable);}//Returns false if found
}
else if(!(empty($UName) or empty($Pass)))
{
	$query="select Pass from Users where UName='$UName';";
	$rTable=pg_query($conn,$query);
	if($rTable[0]['pass']!=$Pass){
		deliver_response(200, "Username/password combo Not found", null); 
	} else {
		$query="delete from SessionKeys where UName='$UName';";
		$rTable=pg_query($conn,$query);
		$SessionKey=bin2hex(mcrypt_create_iv(128, MCRYPT_DEV_RANDOM));
		$query="insert into SessionKeys values ('$UName','$SessionKey');";
		$rTable=pg_query($conn,$query); 
		$result="[{$UName:$SessionKey}]";
		deliver_response(200, "Username found", $result);
		//deliver_response(200, "Username found", $rTable); //returns null but stores a session key
	}
} else {deliver_response(200,"Invalid authentication", null);}
pg_close($conn);
?>