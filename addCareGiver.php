<?   
// This service expects Four values
// CRID - Username of the care receiver
// CRPass - Password of the care receiver
// CGID - Password of the care receiver
// Token - A secret "key" shared by the QRCode creator, with the QRScanner, to verify the authenticity of the request
// Pass values by doing:
// caredb.herokuapp.com/addCareGiver.php?CRID=cdmurphy&CRPass=chris&CGID=dnscianni&Token='SuperSecret'

require 'dbconn.php';
header("Content-Type: application/json");
$crid = $_GET["CRID"];
// Not currently used -> ... $cgid = $_GET["CGID"];
$crpass = $_GET["CRPass"];
$Token = $_GET["Token"];
    
$authQuery="select Pass from Users where UName='$crid'";
$authResponse=pg_query($conn,$authQuery);
$authRow = pg_fetch_row($authResponse);


if(empty($authRow))
	deliver_response(200, "No User with that username", NULL);
else if($crpass != $authRow[0])
	   deliver_response(200, "Invalid password", NULL);
else{
    // User found and Pass was correct
    // So add the Token to the QRToken's table.    
    $addTokenQuery="insert into QRToken values ('$crid', '$Token')";
    $addTokenResponse=pg_query($conn,$addTokenQuery);
    deliver_response(200, "addToken Query Result", $addTokenResponse);
}
pg_close ($conn);
?>