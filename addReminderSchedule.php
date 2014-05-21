<?php  
/** This class, addReminderSchedule will add a single reminder schedule 
 * 
 * If the user is adding a schedule for himself, The SessionToken and the
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
 * UName = 'The UName of the intended recipient of the schedule
 * // Fields in a ReminderSchedule class
 * name = 'The name of the reminder schedule'
 * hour; 
 * minute;
 * interval;
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
	if(!($_POST["CareGiverSessionToken"]
	 && $_POST["CareReceiverSessionToken"]
	  && $_POST["name"] && $_POST["hour"] && $_POST["minute"] && $_POST["interval"]))
	{		
		$status=400;
		$data=false;
	}
	// Valid Session Token, and a PermissionToken Provided.
	// For now, assume the permission token is correct
	// And assume that there isn't already a token for that user
	// Multiple Permission Tokens are allowed for a user because they are short lived and they may have more than 1 caregiver
	else{	
		$cgToken=$_POST["CareGiverSessionToken"];
		$crToken=$_POST["CareReceiverSessionToken"];
		$name=$_POST["name"];
		$hour=$_POST["hour"];
		$minute=$_POST["minute"];
		$interval=$_POST["interval"];
		$conn=connect();
		// Find out if the CareGiver can actually care for the CareReceiver
		// If so, return the UName of the CareReceiver so we can add reminders for him
		$himself = ($cgToken==$crToken);
		$crUName="";
		$legit=false;
		if(!$himself){
			$query="		
					select CRID as CanCareFor from CanCareFor
						where  CRID in (select UName as CRID from SessionTokens where SessionToken='$crToken')
						and CGID in (select UName as CGID from SessionTokens where SessionToken='$cgToken');
			";
			$response=pg_query($conn,$query);
			$resArray = pg_fetch_row($response);
			$crUName=$resArray[0];
			$legit = (!$response || !$crUName);
		}
		else{
			$query="		
					select UName from SessionTokens where SessionToken='$crToken';
			";
			$response=pg_query($conn,$query);
			$resArray = pg_fetch_row($response);
			$crUName=$resArray[0];			
			}
			if(!$himself && !$legit){
				$status=203;
				$statusMessage="CareGiver does not have the priveledge to care";
				$data=false;
			}else{
				// CareGiver is legit, add the ReminderSchedule
				$query="		
					insert into ReminderSchedules values
					( '$crUName'
					, '$name', '$minute', '$hour', '$interval'
					);
				";
			$response=pg_query($conn,$query);
			$resArray = pg_fetch_row($response);
			
						
			$status=202;
			$statusMessage="ReminderSchedule added";
			$data=true;
		}
	}
}

pg_close ($conn);
echo deliver_response($status, $statusMessage,$data);
?>