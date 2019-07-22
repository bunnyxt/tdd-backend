<?php

require_once("db/db_sprint_video_record.php");
require_once("util/Wrapper.php");

require_once("util/init.php");

// GET param
$aid = -1;
if(isset($_GET['aid'])){
    $aid = intval($_GET['aid']);
}

$start = 0;
if(isset($_GET['start'])){
    $start = intval($_GET['start']);
}

$end = 0;
if(isset($_GET['end'])){
    $end = intval($_GET['end']);
}

$type = 1;
if(isset($_GET['end'])){
    $end = intval($_GET['end']);
}

$limit = 0;
if(isset($_GET['limit'])){
    $limit = intval($_GET['limit']);
}

// db operation
$result = sprint_video_record_query($aid, $start, $end, $limit);

// data processing
$data = [];
$count = 0;
$need_view = ($type >> 0) % 2;
$need_danmaku = ($type >> 1) % 2;
$need_reply = ($type >> 2) % 2;
$need_favorite = ($type >> 3) % 2;
$need_coin = ($type >> 4) % 2;
$need_share = ($type >> 5) % 2;
$need_like = ($type >> 6) % 2;

foreach($result as $item)
{
    unset($item->aid);

    if ($need_view == 0)
    {
        unset($item->view);
    }
    if ($need_danmaku == 0)
    {
        unset($item->danmaku);
    }
    if ($need_reply == 0)
    {
        unset($item->reply);
    }
    if ($need_favorite == 0)
    {
        unset($item->favorite);
    }
    if ($need_coin == 0)
    {
        unset($item->coin);
    }
    if ($need_share == 0)
    {
        unset($item->share);
    }
    if ($need_like == 0)
    {
        unset($item->like);
    }

    $count++;
}

// wrap
$wrapper = new wrapper();
$wrapper->status = 200;
$wrapper->meta->aid = $aid;
$wrapper->meta->count = $count;
$wrapper->data = $result;

echo(json_encode($wrapper, JSON_UNESCAPED_UNICODE));

?>