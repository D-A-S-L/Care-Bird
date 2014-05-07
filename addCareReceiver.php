<?   
// This service expects Four values
// CRID - Username of the care receiver
// CGPass - Password of the care Giver
// CGID - Password of the care receiver
// Token - A secret "key" shared by the QRCode creator, with the QRScanner, to verify the authenticity of the request
// Pass values by doing:
// caredb.herokuapp.com/addCareGiver.php?CRID=cdmurphy&CGPass=chris&CGID=dnscianni&Token='SuperSecret'

require 'dbconn.php';
header("Content-Type: application/json");
$crid = $_GET["CRID"];
$cgid = $_GET["CGID"];
$cgpass = $_GET["CRPass"];
$Token = $_GET["Token"];
    
$authQuery="select Pass from Users where UName='$cgid'";
$authResponse=pg_query($conn,$authQuery);
$authRow = pg_fetch_row($authResponse);


if(empty($authRow))
	deliver_response(200, "No User with that username", NULL);
else if($cgpass != $authRow[0])
	   deliver_response(200, "Invalid password", NULL);
else{
    // User found and Pass was correct
    // So check the QRToken's if the token is there.    
    $checkTokenQuery="select token from QRToken where CRID='$crid'";
    $checkTokenResponse=pg_query($conn,$addTokenQuery);
    $checkTokenRow = pg_fetch_row($checkTokenResponse);
    if($Token != $checkTokenRow[0]);
	   deliver_response(200, "Invalid Authentication Token", NULL);
    else{
        // User found, pass was correct, token was valid
        // So add a reference to the CanCare4Table (CRID, CGID)
        $addCareRefQuery="insert into CanCareFor values ('$crid',$'cgid')";
        $addCareRefResponse=pg_query($conn, $addCareRefQuery);
        $addCareRefRow = pg_fetch_row($addCareRefResponse);
        deliver_response(200, "addCareRef Query Result", $addCareRefRow);
    }
}



	//deliver_response(200, "User found", $authResponse);


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