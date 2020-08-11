<html>
<head><title>PHP TEST</title></head>
<body>

<?php
//mysqlとの接続
$link = mysqli_connect('localhost', 'testuser', 'testuser');
if (!$link) {
    die('Failed connecting'.mysqli_error());
}
//print('<p>Successed connecting</p>');

//DBの選択
$db_selected = mysqli_select_db($link , 'test_db');
if (!$db_selected){
    die('Failed Selecting table'.mysql_error());
}

$date= $_POST["date"];

//文字列をutf8に設定
mysqli_set_charset($link , 'utf8');

//pdfテーブルの取得
$result_report_information  = mysqli_query($link ,"SELECT * FROM pic WHERE date =  ");
if (!$result_report_information) {
    die('Failed query'.mysql_error());
}

//データ格納用の配列の取得
$row_array_pic = array();
$i = 0;
while ($row = mysqli_fetch_assoc ($result_report_information)) {
    $row_array_pic[$i] = $row;
    $i++;
}


/* データの格納された配列を、JSON形式にして吐き出す */
header('Content-Type: application/json; charset=utf-8');
echo json_encode($row_array_pic, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES); //JSON形式にして返す

// MySQLに対する処理

$close_flag = mysqli_close($link);


?>
</body>
</html>