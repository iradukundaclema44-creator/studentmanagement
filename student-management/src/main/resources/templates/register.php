<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="" method="post">
        <div class="container">
            <h6 style="margin-bottom: 20px;">INSERT STUDENT RECORDS</h6>
            <label><b>FIRSTNAME</b></label>
            <input type="text" name="firstname" placeholder="enter firstname"><br>
               <label><b>LASTNAME</b></label>
            <input type="text" name="lastname" placeholder="enter lastname"><br>
               <label><b>Email</b></label>
            <input type="text" name="email" placeholder="enter email"><br>
               <label><b>AGE</b></label>
            <input type="number" name="age" placeholder="enter age"><br>
            <input type="submit" value="INSERTSTUDENT" name="add">


        </div>
<?php
$conn=mysqli_connect("localhost","root","","students-management");
if(isset($_POST['add'])){
    $a=$_POST['firstname'];
        $b=$_POST['lastname'];
            $c=$_POST['email'];
                $d=$_POST['age'];
                $insert="INSERT INTO students VALUES('$a','$b','$c','$d')";
                $result=mysqli-query($conn,$insert);
                if($result){
                    echo"data are inserted";
                }
                else{
                    echo"data are not inserted";
                }
}

?>

    </form>
</body>
</html>