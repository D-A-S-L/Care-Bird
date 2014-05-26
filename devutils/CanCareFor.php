<?php   
require '../dbconn.php';
header("Content-Type: application/json");

$sql = "SELECT * from CanCareFor";
$result = pg_query($conn, $sql);

if(empty($result))
	echo deliver_response(200, "No entries in table 'CanCareFor'", NULL);
else
	echo deliver_response(200, "Table 'CanCareFor'", $result);

pg_close($conn);
?>