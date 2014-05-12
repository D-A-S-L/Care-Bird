<?php
require 'dbconn.php';
header("Content-Type: application/json");

$sql = "SELECT * from Users";
$result = pg_query($conn, $sql);

if(empty($result))
	deliver_response(200, "No entries in table 'Users'", NULL);
else
	deliver_response(200, "Table 'Users'", $result);
pg_close ($conn);
?>