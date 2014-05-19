<?php
require '../loginDefines.php';
$conn = connect();
$sql = "SELECT * from QRTokens";
$result = pg_query($conn, $sql);
$result = pg_fetch_all($result);

if(empty($result))
	echo deliver_response(200, "No entries in table 'QRTokens'", NULL);
else
	echo deliver_response(200, "Table 'QRTokens'", $result);

pg_close($conn);
?>