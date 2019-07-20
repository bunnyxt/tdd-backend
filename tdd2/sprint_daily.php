<?php

require_once("db/db_sprint_daily.php");
require_once("util/Wrapper.php");

header('Access-Control-Allow-Origin:*');

// GET param
$date = -1;
if (isset($_GET['date'])) {
    $date = intval($_GET['date']);
}

$limit = 0;
if(isset($_GET['limit'])){
    $limit = intval($_GET['limit']);
}

// db operation
$result = sprint_daily_query($date, $limit);

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