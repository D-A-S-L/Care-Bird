<?php
require '../dbconn.php';
header("Content-Type: application/json");

$sql = "SELECT * from SessionKeys";
$result = pg_query($conn, $sql);

if(empty($result))
	deliver_response(200, "No entries in table 'SessionKeys'", NULL);
else
	deliver_response(200, "Table 'SessionKeys'", $result);
pg_close ($conn);
?>