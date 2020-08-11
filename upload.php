<?php
$uploaddir = 'http://192.168.3.4/sample/uploads/';
$upload = $uploaddir . basename($_FILES['userfile']['name']);
move_uploaded_files($_FILES['userfile'], $upload)
?>