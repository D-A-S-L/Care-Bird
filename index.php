<?   $conn = pg_connect("host=ec2-54-225-136-187.compute-1.amazonaws.com    

    port=5432    

    dbname=d86fo56ie0kqjt    

    user=yndbtfxmnwkcgi    

    password=hiAXar8M6tn8OQNC1zrEDskrKO");   

$sql = "SELECT * from ourgroup";   

$result = pg_query($conn, $sql);   

$row = pg_fetch_object($result, 0);   

echo "Record is: " .$row->'FName';   

?>