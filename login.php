<?php  
// This service validates login and generates a SessionToken
// caredb.herokuapp.com/login.php?UName=cdmurphy&Pass=chris&SessionKey='SuperSecret'

require 'dbconn.php';
header("Content-Type: application/json");
$UName = $_GET["UName"];
$Pass = $_GET["Pass"];
$SessionKey = $_GET["SessionKey"];
    
function notLoggedIn(){
	deliver_response(200, "User is not logged in", false);
}
	 
if (empty($SessionKey) and (empty($UName) or empty($Pass)){
	notLoggedIn();
} else if(!empty($SessionKey)){
	$query="select SessionKey from SessionKeys where SessionKey='SessionKey';";
	$rTable=pg_query($conn,$query);
	$row = pg_fetch_row($rTable);
	if(empty($row))
		notLoggedIn();
	else 
		deliver_response(200, "SessionKey valid", true);
} else if(!(empty($UName) or empty($Pass))){
	$query="delete from SessionKeys where UName='$Uname';";
	$rTable=pg_query($conn,$query);
	$SessionKey=bin2hex(mcrypt_create_iv(128, MCRYPT_DEV_RANDOM));
	$query="insert into SessionKeys values ('$Uname','$SessionKey');";
	$rTable=pg_query($conn,$query);
	deliver_response(200, "Username not found", $SessionKey);
} else deliver_response(200,"Invalid authentication", NULL);

pg_close($conn);
?>