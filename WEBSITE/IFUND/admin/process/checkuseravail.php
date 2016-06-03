 <?php
//include db config file
include '../includes/db_config.php';

if(isset($_POST['action']) && $_POST['action'] == 'availability')
    {
        $username       = mysqli_real_escape_string($conn,$_POST['username']); // Get the username values
            $query  = "select user_id from reg_user where user_id='".$username."'";
            $res    = mysqli_query($conn,$query);
            $count  = mysqli_num_rows($res);
            echo $count;
    }

mysqli_close($conn);
?> 

