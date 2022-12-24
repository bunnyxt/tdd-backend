<?php

require_once("db/db_member.php");
require_once("util/Wrapper.php");

require_once("util/init.php");

// GET param
$mid = -1;
if(isset($_GET['mid'])){
    $mid = intval($_GET['mid']);
}
// must provide mid
if ($mid == -1) {
    echo('{"status":400,"message":"Must provide mid!"}');
    die();
}

$limit = 0;
if(isset($_GET['limit'])){
    $limit = intval($_GET['limit']);
}


// db operation
$result = member_query($mid, $limit);

// data processing
$data = [];
$count = 0;
foreach($result as $item)
{
    unset($item->added);

    $count++;
}

// wrap
$wrapper = new wrapper();
$wrapper->status = 200;
$wrapper->meta->count = $count;
$wrapper->data = $result;

echo(json_encode($wrapper, JSON_UNESCAPED_UNICODE));

?>
