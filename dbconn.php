<?php  	
/*
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
		 dbname=$database"
	);
*/

function connect() {
$dburl;
	if ($_ENV['DATABASE_URL']){
		$dburl = $_ENV['DATABASE_URL'];
		extract(parse_url($dburl));
		$conn=pg_connect("sslmode=require user=$user password=$pass host=$host dbname=" . substr($path, 1));
	}
	else{
		$dburl="postgres://caredb:caredb@localhost:5432/cdb";
		extract(parse_url($dburl));
		$conn=pg_connect("user=$user password=$pass host=$host dbname=" . substr($path, 1));
	}
  return $conn;
}




function deliver_response($status, $status_message, $data){
	header("Content-Type: application/json");
	header("HTTP/1.1 $status $status_message");
	$response['status']=$status;
	$response['status_message']=$status_message;
	$response['data']=$data;

	$json_response=json_encode($data);
	return $json_response;
}
?>