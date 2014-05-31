<?php  
/** This class, addReminderLog will add a single reminder log 
 * 
 * If the user is adding a log for himself, The SessionToken and the
 * UName passed will be associated in the SessionTable.
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
	$status =401;
	$status_Message = "No user found with that combination";
	$data = false;
}else{
	// Valid Session Token but no Permission Token Provided...
	if(!(   isset($_POST["SessionToken"]) &&  isset($_POST["type"]) && isset($_POST["logtime"])  && isset($_POST["originalalerttime"])  ))
	{		
		$status=400;
		$data=false;
	}
	// Valid Session Token Provided.
	else{		
		$SessionToken=$_POST["SessionToken"];
		$logtime=$_POST["logtime"];
		$originalalerttime = $_POST["originalalerttime"];
		$type=$_POST["type"];
		$query="Default Query";
		$goodInput=false;
		// is Location log?
		if( true || isset($_POST["latitude"]) && isset($_POST["longitude"]) && isset($_POST["metersfromhome"]) ){
			// Type is location log
			$goodInput=true;
			$latitude=$_POST["latitude"];
			$longitude=$_POST["longitude"];
			$metersfromhome=$_POST["metersfromhome"];
			$query="	
				insert into Logs
				(UName,latitude,longitude,metersfromhome,originalalerttime,logtime,type)
				 values
				( 
				  (select UName from SessionTokens where SessionToken='$SessionToken')
				, '$latitude', '$longitude', '$metersfromhome'
				, '$originalalerttime','$logtime','$type'
				);			
			";
		}
		// is Pill log?
		else if( isset($_POST["message"]) && isset($_POST["actiontaken"])){
			// Type is location log
			$goodInput=true;
			$message=$_POST["message"];
			$actionTaken=$_POST["actiontaken"];
			$query="	
				insert into Logs
				(UName,message,actiontaken,originalalerttime,logtime,type)
				values
				( 
				  (select UName from SessionTokens where SessionToken='$SessionToken') 
				, '$message', '$actionTaken'
				, '$originalalerttime','$logtime','$type'
				);			
			";
		}
		$conn=connect();
		// Insert the log notification
		$response=pg_query($conn,$query);		

		if(!$goodInput || pg_affected_rows($response)<1){
			$status=203;
			$statusMessage="An error occured: Possibly record already exists";
			$data=$response;
		}else{
			$status=202;
			$statusMessage="Log added";
			$data=true;
		}
		
	}
}


pg_close ($conn);
echo deliver_response($status, $statusMessage,$data);
?>