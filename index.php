<?php
require 'login.php';
$token = bin2hex(mcrypt_create_iv(128, MCRYPT_DEV_RANDOM));
echo $token."\n";
echo "Not messing with db currently";
?>