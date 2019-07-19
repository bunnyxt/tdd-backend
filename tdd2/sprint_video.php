<?php

require_once("db/sprint_video.php");
require_once("util/Wrapper.php");

header('Access-Control-Allow-Origin:*');

// GET param
$aid = -1;
if(isset($_GET['aid'])){
    $aid = intval($_GET['aid']);
}

$status = 'processing';
if(isset($_GET['status'])){
    $status = $_GET['status'];
}

$limit = 0;
if(isset($_GET['limit'])){
    $limit = intval($_GET['limit']);
}

// db operation
$result = sprint_video_query($aid, $status, $limit);

// data processing
$data = [];
$count = 0;
foreach($result as $item)
{
    unset($item->added);
    unset($item->tid);
    unset($item->cid);
    unset($item->typename);
    unset($item->arctype);
    unset($item->pages);
    unset($item->copyright);
    unset($item->status);
    $count++;
}

// wrap
$wrapper = new wrapper();
$wrapper->status = 200;
$wrapper->meta->count = $count;
$wrapper->data = $result;

echo(json_encode($wrapper, JSON_UNESCAPED_UNICODE));

?>