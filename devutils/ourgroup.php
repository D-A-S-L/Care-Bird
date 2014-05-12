<?php
require 'dbconn.php';
header("Content-Type: application/json");

$sql = "SELECT * from ourgroup";
$result = pg_query($conn, $sql);

if(empty($result))
	deliver_response(200, "No entries in table 'ourgroup'", NULL);
else
	deliver_response(200, "Table 'ourgroup'", $result);
	
pg_close($conn);
?>