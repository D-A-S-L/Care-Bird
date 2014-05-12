<?php
require '../dbconn.php';
$sql = "SELECT * from QRToken";
$result = pg_query($conn, $sql);

if(empty($result))
	deliver_response(200, "No entries in table 'QRToken'", NULL);
else
	deliver_response(200, "Table 'QRToken'", $result);

pg_close($conn);
?>