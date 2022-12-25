<?php

require_once("db/db_sprint_daily_record.php");
require_once("db/db_sprint_video.php");
require_once("db/db_member.php");
require_once("util/Wrapper.php");

require_once("util/init.php");

// GET param
$date = -1;
if (isset($_GET['date'])) {
    $date = intval($_GET['date']);
}
// must provide date
if ($date == -1) {
    echo('{"status":400,"message":"Must provide date!"}');
    die();
}

$limit = 0;
if(isset($_GET['limit'])){
    $limit = intval($_GET['limit']);
}

// db operation
$result = sprint_daily_record_query($date, $limit);

// data processing
$data = [];
$count = 0;
foreach($result as $item)
{
    unset($item->added);

    // TODO use table join to optimization

    // add sprint video
    $sprint_video = sprint_video_query($item->aid);
    $sprint_video = $sprint_video[0];
    unset($sprint_video->added);
    unset($sprint_video->tid);
    unset($sprint_video->cid);
    unset($sprint_video->typename);
    unset($sprint_video->arctype);
    unset($sprint_video->pages);
    unset($sprint_video->copyright);
    unset($sprint_video->status);
    $item->sprint_video = $sprint_video;

    // add member
    $member = member_query($sprint_video->mid);
    $member = $member[0];
    unset($member->added);
    $item->sprint_video->member = $member;

    $count++;
}

// wrap
$wrapper = new wrapper();
$wrapper->status = 200;
$wrapper->meta->count = $count;
$wrapper->data = $result;

echo(json_encode($wrapper, JSON_UNESCAPED_UNICODE));

?>