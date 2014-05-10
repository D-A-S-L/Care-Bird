<?php  	
	$DATABASE_URL=getenv('DATABASE_URL');
	$explodedURL = explode(':' , $DATABASE_URL);
	$passAndHostExplodedURL = explode('@', $explodedURL[2]);
	$portAndDatabaseExplodedURL=explode('/',$explodedURL[3]);
	$user=trim($explodedURL[1], '//');
	$pass=$passAndHostExplodedURL[0];
	$host=$passAndHostExplodedURL[1];
	$port=$portAndDatabaseExplodedURL[0];
	$database=$portAndDatabaseExplodedURL[1];
	
$conn = pg_connect(
	"host=$host  
    port=$port
    user=$user
    password=$pass
	 dbname=$database");

function deliver_response($status, $status_message, $data){
	header("HTTP/1.1 $status $status_message");
	$response['status']=$status;
	$response['status_message']=$status_message;
	$response['data']=$data;

	$responseArray=pg_fetch_all($data);
	$json_response=json_encode($responseArray);
	echo $json_response;
}
?>