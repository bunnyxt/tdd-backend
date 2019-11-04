<?php

// error display
ini_set("display_errors", 0);
error_reporting(E_ALL ^ E_NOTICE);
error_reporting(E_ALL ^ E_WARNING);

// header
header('Access-Control-Allow-Origin:*');

?>