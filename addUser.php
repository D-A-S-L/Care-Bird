<?php   
// This service expects 3 values
// User
// Pass
// FName
// LName
// These will create a new USER in the database.
// Pass values by doing:
// caredb.herokuapp.com/addCareGiver.php?User=cdmurphy&Pass=chris&FName=Chris&LName=Murphy

require 'dbconn.php';
$conn=connect();
$user = $_POST["UName"];
$pass = $_POST["Pass"];
$fname = $_POST["FName"];
$lname = $_POST["LName"];

$query="insert into users
		 values ('$fname','$lname','$user','$pass');";
$response=pg_query($conn,$query);

$query="select * from Users
		 where FName='$fname'
		 	and LName='$lname'
		 	and UName='$user'
		 	and Pass='$pass';";
$response=pg_query($conn,$query);

if(!$response){
	$status=406;
	$statusMessage="An error occured: Possibly record already exists";
	$data=false;
}else{
	$status=202;
	$statusMessage="The Query was a success. New User added.";
	$data=true;
}

pg_close ($conn);
// ALWAYS ECHO deliver_response
echo deliver_response($status,$statusMessage,$data);
?>