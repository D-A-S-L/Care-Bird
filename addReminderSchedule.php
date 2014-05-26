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
	if(!(isset($_POST["SessionToken"])	 && isset($_POST["CareReceiverUserName"])  && isset($_POST["name"]) && isset($_POST["hour"]) && isset($_POST["minute"]) && isset($_POST["interval"])))
	{		
		$status=400;
		$data=false;
	}
	// Valid Session Token Provided.
	else{	
		$cgToken=$_POST["SessionToken"];
		$crUName=$_POST["CareReceiverUserName"];
		$name=$_POST["name"];
		$hour=$_POST["hour"];
		$minute=$_POST["minute"];
		$interval=$_POST["interval"];
		$conn=connect();
		// Find out if the care giver is the same person as the care receiver
			$query="		
				select UName from SessionTokens where SessionToken='$cgToken'
			";
			$response=pg_query($conn,$query);
			$resArray = pg_fetch_row($response);
		$himself = ($crUName==$resArray[0]);
		// Find out, if the care giver has permission over the care receiver 
		$legit=false;
		if(!$himself){
			$query="		
					select CRID from CanCareFor
						where  CRID='$crUName' and CGID in (select UName as CGID from SessionTokens where SessionToken='$cgToken');
			";
						/* A result means that the care giver has correct permissions */
			$response=pg_query($conn,$query);
			$resArray = pg_fetch_row($response);
			echo $resArray[0];
			$legit = ($crUName==$resArray[0]);
		}

			// at this point the care giver is either the same person as the care receiver,
			// or the care giver has permission over the care receiver	

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
			
			if(pg_affected_rows($response)<1){
				$status=203;
				$statusMessage="An error occured: Possibly record already exists";
				$data=$response;
			}else{
				$status=202;
				$statusMessage="ReminderSchedule added";
				$data=true;
			}
		}
	}
}

pg_close ($conn);
echo deliver_response($status, $statusMessage,$data);
?>