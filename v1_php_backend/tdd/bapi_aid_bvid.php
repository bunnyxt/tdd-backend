<?php 

header('Access-Control-Allow-Origin:*');

$aid = -1;
if(isset($_GET['aid'])){
    $aid = $_GET['aid'];
}

$bvid = -1;
if(isset($_GET['bvid'])){
    $bvid = $_GET['bvid'];
}

if ($aid == -1 && $bvid == -1) {
    echo('{"status":"error","aid":'.$aid.',"bvid":"'.$bvid.'"}');
    die();
}

if ($aid != -1) {
    $json = file_get_contents('https://api.bilibili.com/x/web-interface/view?aid='.$aid);
    $obj = json_decode($json);
    if ($obj->code == 0) {
        echo('{"status":"success","aid":'.$aid.',"bvid":"'.$obj->data->bvid.'"}');
        die();
    } else {
        echo('{"status":"error","aid":'.$aid.',"bvid":"'.$bvid.'"}');
        die();
    }
}

if ($bvid != -1) {
    $json = file_get_contents('https://api.bilibili.com/x/web-interface/view?bvid='.$bvid);
    $obj = json_decode($json);
    if ($obj->code == 0) {
        echo('{"status":"success","aid":'.$obj->data->aid.',"bvid":"'.$bvid.'"}');
        die();
    } else {
        echo('{"status":"error","aid":'.$aid.',"bvid":"'.$bvid.'"}');
        die();
    }
}

?>