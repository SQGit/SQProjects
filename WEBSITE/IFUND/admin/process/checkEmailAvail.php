 <?php
//include db config file
include '../includes/db_config.php';

$email = $_POST['email']; // get the email
if ($email == "") {
    // if the post value is empty it will not do anything
} else {
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        echo '<span class="error">Please Enter Valid Email</span>';
    } else {
        // if it's valid email, it will  query the database
        $query    = "SELECT * FROM pic_table WHERE email ='$email'";
        $result   = mysqli_query($conn, $query);
        $num_rows = mysqli_num_rows($result);
        if ($num_rows > 0) {
            echo '<span class="error">Email Already Registered</span>';
        } else {
            echo '<span class="success">Email Available for You</span>';
        }
    }
}
mysqli_close($conn);
?> 