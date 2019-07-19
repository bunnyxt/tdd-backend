<?php

function init_select($table_name)
{
    return "select * from ".$table_name." where 1=1 ";
}

function add_restrict($sql, $key, $value, $op = '=')
{
    if (is_string($value))
    {
        $value = '"'.$value.'"';
    }
    return $sql.'and `'.$key.'` '.$op.' '.$value.' ';
}

function add_limit($sql, $limit)
{
    if ($limit > 0)
    {
        $sql .= 'limit '.$limit;
    }
    return $sql;
}