<?   
// This service expects Four values
// CRID - Username of the care receiver
// CRPass - Password of the care receiver
// CGID - Password of the care receiver
// Token - A secret "key" shared by the QRCode creator, with the QRScanner, to verify the authenticity of the request
// Pass values by doing:
// caredb.herokuapp.com/addCareGiver.php?CRID=cdmurphy&CRPass=chris&CGID=dnscianni&Token='SuperSecret'

require 'dbconn.php';
$crid = $_GET["CRID"];
$cgid = $_GET["CGID"];
$crpass = $_GET["CRPass"];
$Token = $_GET["Token"];
    
$authQuery="select Pass from Users where UName='$crid'";
$authResponse=pg_query($conn,$authQuery);
$authRow = pg_fetch_row($authResponse);


if(empty($authQuery))
	deliver_response(200, "No User with that username", NULL);
else{
	deliver_response(200, "User found", $authQuery);
}




function deliver_response($status, $status_message, $data){
	header("HTTP/1.1 $status $status_message");
	$response['status']=$status;
	$response['status_message']=$status_message;
	$response['data']=$data;
	
	$responseArray=pg_fetch_all($data);
	$json_response=json_encode($responseArray);
	echo $json_response;
}/**/
/*$sql = "SELECT * from ourgroup";
$result = pg_query($conn, $sql);*/


 

pg_close ($conn);
?>