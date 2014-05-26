<?php  
// This service expects Four values
// CRID - Username of the care receiver
// CRPass - Password of the care receiver
// CGID - Password of the care receiver
// Token - A secret "key" shared by the QRCode creator, with the QRScanner, to verify the authenticity of the request
// Pass values by doing:
// caredb.herokuapp.com/addCareGiver.php?SessionToken=somekey&PermissionToken'
require 'loginDefines.php';

$loggedIn = loggedIn();
$status=400;
$statusMessage="Default Message";
$data=null;

if(!$loggedIn){
	$status =203;
	$status_Message = "No user found with that combination";
	$data = false;
}else{
	// Valid Session Token but no Permission Token Provided...
	if(!$_POST["PermissionToken"])
	{		
		$status=400;
		$data=false;
	}
	// Valid Session Token, and a PermissionToken Provided.
	// For now, assume the permission token is correct
	// And assume that there isn't already a token for that user
	// Multiple Permission Tokens are allowed for a user because they are short lived and they may have more than 1 caregiver
	else{	
		$pToken=$_POST["PermissionToken"];
		$SessionToken=$_POST["SessionToken"];
		$conn=connect();
		$query="		
			insert into QRTokens
			select UName, '$pToken' as Token from Users
			where UName in (select UName from SessionTokens where SessionToken='$SessionToken');
		";
		$response=pg_query($conn,$query);
		if(pg_affected_rows($response)<1){
			$status=203;
			$statusMessage="An error occured: Possibly record already exists";
			$data=$response;
		}else{
			$status=202;
			$statusMessage="The Query was a success. New QRToken added";
			$data=true;
		}
	}
}
pg_close ($conn);
echo deliver_response($status, $statusMessage,$data);
?>