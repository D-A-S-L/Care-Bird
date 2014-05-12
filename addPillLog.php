<?php
/*   In an attempt to make this easy, the database records will be based on this function from David
	  To store a pillLog in the database use
	  caredb.herokuapp.com/addPillLog?SessionKey=validSessionKey&alert_time=someString&log_time=someString&message=someString&action_taken=someString
																					(30 chars)					(30 chars)			(250 chars)				(10 chars)
												These field sizes should be considered more carefully. These are just assumptions.
+        public void addPillLog(Date orginalAlertTime, String message, String action){

+            Date date = new Date();

+            String query = "INSERT INTO" + db + "." + PILL_LOGGER_TABLE +

+                    "(alert_time, log_time, message, action_taken)" +

+                    "VALUES" +

+                    "(" + orginalAlertTime + ", " + new Date() + ", " + message + ", " +action + ");";

+            db.execSQL(query);

+
*/
require 'login.php';
if (!logIn()){
	echo '<br>Invalid SessionKey</br>';
}
else if (false){//(!(in_array('alert_time',$_GET) and in_array('log_time',$_GET) and in_array('message',$_GET) and in_array('action_taken',$_GET))
		//or !(isset($_GET['alert_time']) and isset($_GET['log_time']) and isset($_GET['message']) and isset($_GET['action_taken']))){
		// Just checking that the values exist...
	echo '<br>Null Fields not allowed</br>';	
}	
else{ // The SessionKey is valid and the fields aren't blanks
$at=$_GET['alert_time'];
$lt=$_GET['log_time'];
$m=$_GET['message'];
$a=$_GET['action_taken'];

$conn=connect();
$SessionKey=$_GET['SessionKey'];
$query="select UName from SessionKeys where SessionKey='$SessionKey'";
$response=pg_query($conn,$query);
$row=pg_fetch_row($response);
$uname = $row[0];


$query="insert into PillRecord values ('$uname','$at', '$lt', '$m','$a');";
$response=pg_query($conn,$query);
$row=pg_fetch_row($response);
print_r($row);

//header("Content-Type: application/json");


pg_close ($conn);
}
?>