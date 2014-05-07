<?   
$conn = pg_connect("host=ec2-54-225-136-187.compute-1.amazonaws.com    

    port=5432    

    dbname=d86fo56ie0kqjt    

    user=yndbtfxmnwkcgi    

    password=hiAXar8M6tn8OQNC1zrEDskrKO");   

header("Content-Type: application/json");

$sql = "SELECT * from Users";
$result = pg_query($conn, $sql);

if(empty($result))
	deliver_response(200, "No entries in table 'Users'", NULL);
else
	deliver_response(200, "Table 'Users'", $result);
 
function deliver_response($status, $status_message, $data){
	header("HTTP/1.1 $status $status_message");
	$response['status']=$status;
	$response['status_message']=$status_message;
	$response['data']=$data;
	
	$responseArray=pg_fetch_all($data);
	$json_response=json_encode($responseArray);
	echo $json_response;
}
pg_close ($conn);
?>