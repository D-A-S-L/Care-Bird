<?php  
/** This service expects the SessionToken of the user who wants to know who his CareGivers are
 * @param string SessionToken
 */
 
require 'loginDefines.php';

$loggedIn = loggedIn();
$status=400;
$statusMessage="Default Message";
$data=null;

if(!$loggedIn){
	$status =401;
	$status_Message = "No user found with that combination";
	$data = false;
}else{
	// Valid Session Token
	// Find and return a list of his CareGivers
		$SessionToken=$_POST["SessionToken"];
		$conn=connect();
		$query="
			select FName,LName,UName, '' as Pass, PhoneNum from Users
			where UName in 
				(select CGID as UName from CanCareFor where CRID in 
					(select UName as CRID from SessionTokens where SessionToken='$SessionToken'));
		";
		$response = pg_query($conn,$query);
		if(pg_num_rows($response)<1){
			$status=203;
			$statusMessage="No Records found for that user";
			$data=false;
		}else{
			$status=202;
			$statusMessage="Success, returning CareGivers";
			$data=pg_fetch_all($response);
		}
	}

pg_close ($conn);
echo deliver_response($status, $statusMessage,$data);
?>