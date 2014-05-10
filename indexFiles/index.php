<?php

$token = bin2hex(mcrypt_create_iv(128, MCRYPT_DEV_RANDOM));
echo $token
?>