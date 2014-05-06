<?   
$conn = pg_connect("host=ec2-54-225-136-187.compute-1.amazonaws.com    

    port=5432    

    dbname=d86fo56ie0kqjt    

    user=yndbtfxmnwkcgi    

    password=hiAXar8M6tn8OQNC1zrEDskrKO");   

header("Content-Type: application/json");

$sql = "SELECT * from ourgroup";
$result = pg_query($conn, $sql);

if(empty($result))
	deliver_response(200, "No entries in table 'ourgroup'", NULL);
else
	deliver_response(200, "Table 'ourgroup'", $result);

while ($row = pg_fetch_row($result)) {
  echo "Name: $row[0]<br/>\n
		LastName: $row[1] <br/>\n
		Coolness: $row[2] <br/>\n <br/>\n";
}  
function deliver_response($status, $status_message, $data){
	header("HTTP/1.1 $status $status_message");
	$response['status']=$status;
	$response['status_message']=$status_message;
	$response['data']=$data;
	
	$responseArray=pg_fetch_all($result);
	$json_response=json_encode($responseArray);
	echo $json_response;
}
pg_close ($conn);
?>