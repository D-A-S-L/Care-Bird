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
$status=0;
$statusMessage="Default Message";
$data=null;

if(!$loggedIn){
	$status =400;
	$status_Message = "No user found with that combination";
	$data = false;
}else{
	// Valid Session Token but no Permission Token Provided...
	if(!$_POST["PermissionToken"])
	{		
		$status=400;
		$statusMessage="Invalid Input";
		$data=false;
	}
	// Valid Session Token, and a PermissionToken Provided.
	// Try to find it in the QRTokens table
	else{	
		$PermissionToken=$_POST["PermissionToken"];
		$SessionToken=$_POST["SessionToken"];
	    // Temporary solution to "cannot redeclare connect() error"
		$conn=connect();
		$query="
		insert into CanCareFor
			select receiver.CRID, giver.CGID
			from (select UName as CRID from QRTokens where Token='$PermissionToken') as receiver
			    ,(select UName as CGID from SessionTokens where SessionToken='$SessionToken') as giver
		";
		$response=pg_query($conn,$query);
		pg_close ($conn);
		if(!$response){
			$status=400;
			$statusMessage="An error occured: Possibly record already exists";
			$data=$response;
		}else{
			$status=200;
			$statusMessage="The Query was a success. Either nothing was added, or a new relationship was";
			$data=true;
		}
	}
}
echo deliver_response($status, $statusMessage,$data);
?>