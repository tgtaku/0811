<?php
require "conn.php";

$projects_name  = $_POST["projects_name"];
$files_name = $_POST["files_name"];
$path = $_POST["path"];
$position = $_POST["position"];
$sql = "INSERT INTO pdf_information(projects_name, files_name, path, position) VALUES ('projects_name', 'files_name', 'path', 'position')";
$result  = mysqli_query($conn, $sql);
if($result){
echo "Data Inserted";
}
else{
echo "Failed";
}
?>