<?php

require_once("db/db_sprint_video.php");
require_once("db/db_member.php");
require_once("db/db_sprint_video_record.php");
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

    // TODO use table join to optimization

    // add member
    $member = member_query($item->mid);
    $member = $member[0];
    unset($member->added);
    $item->member = $member;

    // add last record
    $last_record = sprint_video_record_query($item->aid, 0, 0, 1);
    $last_record = $last_record[0];
    unset($last_record->aid);
    unset($last_record->danmaku);
    unset($last_record->reply);
    unset($last_record->favorite);
    unset($last_record->coin);
    unset($last_record->share);
    unset($last_record->like);
    $item->last_record = $last_record;

    $count++;
}

// wrap
$wrapper = new wrapper();
$wrapper->status = 200;
$wrapper->meta->count = $count;
$wrapper->data = $result;

echo(json_encode($wrapper, JSON_UNESCAPED_UNICODE));

?>