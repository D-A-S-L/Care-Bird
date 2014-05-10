<?   
// This service expects 3 values
// User
// Pass
// FName
// LName
// These will create a new USER in the database.
// Pass values by doing:
// caredb.herokuapp.com/addCareGiver.php?User=cdmurphy&Pass=chris&FName=Chris&LName=Murphy

require 'dbconn.php';
header("Content-Type: application/json");
$user = $_GET["User"];
$pass = $_GET["Pass"];
$fname = $_GET["FName"];
$lname = $_GET["LName"];
    
$authQuery="insert into users values ('$fname', '$lname', '$user','pass');";
$authResponse=pg_query($conn,$authQuery);
$authRow = pg_fetch_row($authResponse);

deliver_response(200, "User added if it could", NULL);

pg_close ($conn);
?>