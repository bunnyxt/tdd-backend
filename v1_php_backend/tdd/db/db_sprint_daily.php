<?php

require_once("conn.php");
require_once("sql_helper.php");
require_once("model/SprintDaily.php");

function sprint_daily_query($date, $limit = 0)
{
    global $conn;

    // concat sql
    $sql = init_select("tdd_sprint_daily");
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
            $sprintDaily = new SprintDaily();
            $sprintDaily->id = intval($row['id']);
            $sprintDaily->added = intval($row['added']);
            $sprintDaily->date = intval($row['date']);
            $sprintDaily->correct = intval($row['correct']);
            $sprintDaily->vidnum = intval($row['vidnum']);
            $sprintDaily->newvids = array();
            $newvids = explode(';', $row['newvids']);
            for ($i = 0; $i < count($newvids) - 1; $i++) {
                $sprintDaily->newvids[] = intval($newvids[$i]);
            }
            $sprintDaily->millvids = array();
            $millvids = explode(';', $row['millvids']);
            for ($i = 0; $i < count($millvids) - 1; $i++) {
                $sprintDaily->millvids[] = intval($millvids[$i]);
            }
            $sprintDaily->viewincr = intval($row['viewincr']);
            $sprintDaily->viewincrincr = intval($row['viewincrincr']);
            $sprintDaily->comment = $row['comment'];

            $array[] = $sprintDaily;
        }
    }

    $array = array_reverse($array);

    return $array;
}

?>