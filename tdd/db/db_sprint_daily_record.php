<?php

require_once("conn.php");
require_once("sql_helper.php");
require_once("model/SprintDailyRecord.php");

function sprint_daily_record_query($date, $limit = 0)
{
    global $conn;

    // concat sql
    $sql = init_select("tdd_sprint_daily_record");
    if ($date != -1)
    {
        $sql = add_restrict($sql, "date", $date);
    }
    $sql = add_order_by($sql, "id", true);
    $sql = add_limit($sql, $limit);

    // execute
    $array = [];
    if ($result = $conn->query($sql))
    {
        while ($row = $result->fetch(PDO::FETCH_ASSOC))
        {
            $sprintDailyRecord = new SprintDailyRecord();
            $sprintDailyRecord->id = intval($row['id']);
            $sprintDailyRecord->added = intval($row['added']);
            $sprintDailyRecord->date = $row['date'];
            $sprintDailyRecord->aid = intval($row['aid']);
            $sprintDailyRecord->view = intval($row['view']);
            $sprintDailyRecord->viewincr = intval($row['viewincr']);
            $sprintDailyRecord->pday = intval($row['pday']);
            $sprintDailyRecord->lday = intval($row['lday']);

            $array[] = $sprintDailyRecord;
        }
    }

    $array = array_reverse($array);

    return $array;
}

?>