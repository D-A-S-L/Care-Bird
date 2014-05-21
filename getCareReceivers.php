<?php  
/** This service expects the SessionToken of the user who wants to know who his CareReceivers are
 * @param string SessionToken
 */
 
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
	// Valid Session Token
	// Find and return a list of his CareReceivers
		$SessionToken=$_POST["SessionToken"];
		$conn=connect();
		$query="
			select FName,LName,UName, null as pass from Users
			where UName in 
				(select CRID as UName from CanCareFor where CGID in 
					(select UName as CGID from SessionTokens where SessionToken='$SessionToken'));
		";
		$response=pg_query($conn,$query);
		pg_close ($conn);
		if(!$response){
			$status=400;
			$statusMessage="No Records found for that user";
			$data=false;
		}else{
			$status=202;
			$statusMessage="Success, returning CareGivers";
			$data=pg_fetch_all($response);
		}
	}
echo deliver_response($status, $statusMessage,$data);
?>