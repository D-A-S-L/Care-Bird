<?php  
/** This class, getReminderSchedules will return all reminder schedules
 * for the specified UName to the requestor with the specified SessionKey 
 * 
 * If the user is requesting a schedule for himself, The SessionToken and the
 * UName passed will be associated in the SessionTable.
 * 
 * If the user is adding a schedule for one of his CareReceivers, the UName
 * passed will be associated to his on UName in the CanCareFor table
 * 
 * Otherwise the server should reject this request
 * Parameters:
 * // Who is making the request?
 * SessionToken = 'The SessionToken of whoever is doing the add request'
 * // Who is the request for?
 * CRName = 'The UName of the intended recipient of the schedule
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
	// Valid Session Token but no Permission Token Provided...
	if(!($_POST["SessionToken"] && $_POST["CRName"]) )
	{		
		$status=400;
		$data=false;
	}
	// Valid Session Token, and a PermissionToken Provided.
	// For now, assume the permission token is correct
	// And assume that there isn't already a token for that user
	// Multiple Permission Tokens are allowed for a user because they are short lived and they may have more than 1 caregiver
	else{	
		$sToken=$_POST["SessionToken"];
		$CRName=$_POST["CRName"];
		$conn=connect();
		// Find out if the CareGiver can actually care for the CareReceiver
		// If so, return the CRName's Reminder Schedules
		$query="		
				select UName from SessionTokens where SessionToken='$sToken'
		";
			$response=pg_query($conn,$query);
			$resArray = pg_fetch_row($response);
		$UName=$resArray[0];
		$himself = ($CRName==$UName);
		$legit=false;
		if(!$himself){
			$query="		
					select CRID from CanCareFor
						where  CRID ='$CRName'
						and CGID in (select UName as CGID from SessionTokens where SessionToken='$sToken');
			";
				$response=pg_query($conn,$query);
				$resArray = pg_fetch_row($response);
			$crUName=$resArray[0];
			$legit = (!$response && ($crUName == $CRName));
		}	
		if(!$himself && !$legit){
			$status=203;
			$statusMessage="CareGiver does not have the priveledge to care";
			$data=false;
		}else{
			// CareGiver is legit, get the ReminderSchedules

			$query="		
					select name,hour,minute,interval from ReminderSchedules where UName='$CRName';
			";
			$response=pg_query($conn,$query);
			
			if(pg_affected_rows($response)<1){
				$status=203;
				$statusMessage="No records found for that User";
				$data=$response;
			}else{
				$status=202;
				$statusMessage="Records found";
				$data=true;
			}
		}
	}
}

pg_close ($conn);
echo deliver_response($status, $statusMessage,$data);
?>