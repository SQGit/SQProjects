<?php
session_start();
$inv_id = $_SESSION['inv_id'];
if(isset($_POST['submit'])){
	
	echo '<link rel="stylesheet" href="assets/css/main/paymnt_loading.css">';
	echo '<div class="wait">PayPal is processing the payment, please wait...</div>';
	echo '<div class="loader">
		<div></div>
		<div></div>
		<div></div>
		<div></div>
		<div></div>
		</div>';
	
	
	$selected_item = array();
	
	if(!empty($_POST['sp'])){

		foreach($_POST['sp'] as $val){
			
			array_push($selected_item, "$val");	
			
		}
		 $selected_item =  implode(', ', $selected_item);	
	} 
	else
	{
		echo "Please select Projects";
	}

	$amount = $_POST['pay_amount'];

	$investor_id = $inv_id;
	
	//echo $selected_item .'<br/>';
	//echo $amount;

$data=array(

'merchant_email'=>'seller@sqindia.net',

'investor_id'=>$investor_id,

'product_name'=> $selected_item,

'amount'=> $amount,

'currency_code'=>'USD',

'thanks_page'=>"http://".$_SERVER['HTTP_HOST'].'/site31/investment.php?msg=success',

'notify_url'=>"http://".$_SERVER['HTTP_HOST'].'/site31/ipn.php',

'cancel_url'=>"http://".$_SERVER['HTTP_HOST'].'/site31/invesment.php?msg=cancel',

'paypal_mode'=>true,

);

define( 'SSL_URL', 'https://www.paypal.com/cgi-bin/webscr' );

define( 'SSL_SAND_URL', 'https://www.sandbox.paypal.com/cgi-bin/webscr' );

$action = '';

//Is this a test transaction?

$action = ($data['paypal_mode']) ? SSL_SAND_URL : SSL_URL;

$form = '';

$form .= '<form name="frm_payment_method" action="' . $action . '" method="post">';
$form .= '<input type="hidden" name="business" value="' . $data['merchant_email'] . '" />';
// Instant Payment Notification & Return Page Details /
$form .= '<input type="hidden" name="notify_url" value="' . $data['notify_url'] . '" />';
$form .= '<input type="hidden" name="cancel_return" value="' . $data['cancel_url'] . '" />';
$form .= '<input type="hidden" name="return" value="' . $data['thanks_page'] . '" />';
$form .= '<input type="hidden" name="rm" value="2" />';
// Configures Basic Checkout Fields -->
$form .= '<input type="hidden" name="lc" value="" />';
$form .= '<input type="hidden" name="no_shipping" value="1" />';
$form .= '<input type="hidden" name="no_note" value="1" />';
$form .= '<input type="hidden" name="custom" value="'.$data['investor_id'].'" />';
$form .= '<input type="hidden" name="currency_code" value="' . $data['currency_code'] . '" />';
$form .= '<input type="hidden" name="page_style" value="paypal" />';
$form .= '<input type="hidden" name="charset" value="utf-8" />';
$form .= '<input type="hidden" name="item_name" value="' . $data['product_name'] . '" />';
$form .= '<input type="hidden" value="_xclick" name="cmd"/>';
$form .= '<input type="hidden" name="amount" value="' . $data['amount'] . '" />';
$form .= '<script>';
$form .= 'setTimeout("document.frm_payment_method.submit()", 2);';
$form .= '</script>';
$form .= '</form>';

echo $form;

}

?>