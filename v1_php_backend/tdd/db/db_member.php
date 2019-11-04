<?php

require_once("conn.php");
require_once("sql_helper.php");
require_once("model/Member.php");

function member_query($mid, $limit = 0)
{
    global $conn;

    //concat sql
    $sql = init_select("tdd_member");
    if ($mid != -1)
    {
        $sql = add_restrict($sql, "mid", $mid);
    }
    $sql = add_limit($sql, $limit);

    // execute
    $array = [];
    if ($result = $conn->query($sql))
    {
        while ($row = $result->fetch(PDO::FETCH_ASSOC))
        {
            $member = new Member();
            $member->id = intval($row['id']);
            $member->added = intval($row['added']);
            $member->mid = intval($row['mid']);
            $member->sex = $row['sex'];
            $member->name = $row['name'];
            $member->face = $row['face'];

            $array[] = $member;
        }
    }

    return $array;
}

?>