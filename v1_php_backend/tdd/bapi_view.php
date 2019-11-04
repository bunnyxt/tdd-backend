<?php 

header('Access-Control-Allow-Origin:*');

$aid = -1;
if(isset($_GET['aid'])){
    $aid = $_GET['aid'];
}

$view = file_get_contents("https://api.bilibili.com/x/web-interface/view?aid=".$aid);
echo($view);

?>