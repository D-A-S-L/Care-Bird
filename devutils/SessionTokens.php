<?php
require '../loginDefines.php';
$conn = connect();
header("Content-Type: application/json");

$sql = "SELECT * from SessionTokens";
$result = pg_query($conn, $sql);
$result = pg_fetch_all($result);

if(empty($result))
	echo deliver_response(200, "No entries in table 'SessionTokens'", NULL);
else
	echo deliver_response(200, "Table 'SessionTokens'", $result);
pg_close ($conn);
?>