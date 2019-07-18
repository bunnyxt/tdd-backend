<?php

try {
    $conn = new PDO('mysql:host=localhost;dbname=tdd;charset=utf8', 'td', 'td', array(PDO::ATTR_PERSISTENT => true));
} catch (PDOException $e) {
    echo ('<script>alert("' . $e->getMessage() . '")</script>');
    die;
}

?>