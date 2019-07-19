<?php

require_once("conn.php");
require_once("model/SprintVideo.php");

function sprint_video_query($aid, $status, $limit)
{
    global $conn;

    // concat sql
    $sql = "";
    if ($aid == -1)
    {
        $sql .= 'select * from tdd_sprint_video where status="'.$status.'"';
    }
    else
    {
        $sql .= 'select * from tdd_sprint_video where aid='.$aid.' && status="'.$status.'"';
    }
    if ($limit > 0)
    {
        $sql .= "limit ".$limit;
    }

    // execute
    $array = [];
    if ($result = $conn->query($sql)) 
    {
        while ($row = $result->fetch(PDO::FETCH_ASSOC)) 
        {
            $sprintVideo = new SprintVideo();
            $sprintVideo->id = intval($row['id']);
            $sprintVideo->added = intval($row['added']);
            $sprintVideo->mid = intval($row['mid']);
            $sprintVideo->aid = intval($row['aid']);
            $sprintVideo->tid = intval($row['tid']);
            $sprintVideo->cid = intval($row['cid']);
            $sprintVideo->typename = $row['typename'];
            $sprintVideo->arctype = $row['arctype'];
            $sprintVideo->title = $row['title'];
            $sprintVideo->pic = $row['pic'];
            $sprintVideo->pages = intval($row['pages']);
            $sprintVideo->created = intval($row['created']);
            $sprintVideo->copyright = intval($row['copyright']);
            $singer = explode(',', $row['singer']);
            for ($i = 0; $i < count($singer) - 1; $i++) {
                $sprintVideo->singer[] = $singer[$i];
            }
            $sprintVideo->solo = intval($row['solo']);
            $sprintVideo->original = intval($row['original']);
            $sprintVideo->status = $row['status'];
            
            $array[] = $sprintVideo;
        }
    }

    return $array;
}

?>