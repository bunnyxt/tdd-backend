<?php

require_once("conn.php");
require_once("sql_helper.php");
require_once("model/SprintVideoRecord.php");

function sprint_video_record_query($aid, $start = 0, $end = 0 ,$limit = 0)
{
    global $conn;

    // concat sql
    $sql = init_select("tdd_sprint_video_record");
    if ($aid != -1)
    {
        $sql = add_restrict($sql, "aid", $aid);
    }
    if ($start > 0)
    {
        $sql = add_restrict($sql,"added", $start, ">=");
    }
    if ($end > 0)
    {
        $sql = add_restrict($sql,"added", $end, "<");
    }
    $sql = add_order_by($sql, 'id', true);
    $sql = add_limit($sql, $limit);

    // execute
    $array = [];
    if ($result = $conn->query($sql))
    {
        while ($row = $result->fetch(PDO::FETCH_ASSOC))
        {
            $sprintVideoRecord = new SprintVideoRecord();
            $sprintVideoRecord->id = intval($row['id']);
            $sprintVideoRecord->added = intval($row['added']);
            $sprintVideoRecord->aid = intval($row['aid']);
            $sprintVideoRecord->view = intval($row['view']);
            $sprintVideoRecord->danmaku = intval($row['danmaku']);
            $sprintVideoRecord->reply = intval($row['reply']);
            $sprintVideoRecord->favorite = intval($row['favorite']);
            $sprintVideoRecord->coin = intval($row['coin']);
            $sprintVideoRecord->share = intval($row['share']);
            $sprintVideoRecord->like = intval($row['like']);

            $array[] = $sprintVideoRecord;
        }
    }

    $array = array_reverse($array);

    return $array;
}