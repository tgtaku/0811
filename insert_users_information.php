<?php
require "conn.php";

$users_name  = $_POST["users_name"];
$password = $_POST["password"];
$position = $_POST["permission"];

$sql = "INSERT INTO users_information VALUES ('', '$users_name', '$password', '$position');";
$result  = mysqli_query($conn, $sql);
if($result){
echo "Data Inserted";
}
else{
echo "Failed";
}
?>