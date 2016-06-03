<?php

include ("../includes/db_config.php");


	
	if(isset($_POST['query'])){
		
		$query = $_POST['query'];
		
		$sql = "SELECT * FROM faq where faq_question LIKE '%$query%' OR faq_ans LIKE '%$query%'";
		
		$result = mysqli_query($conn, $sql);
		$numrows = mysqli_num_rows($result);
		if($numrows >0){
			$i=0;
			while($row = mysqli_fetch_assoc($result))
			{
			if($i==0){
				echo '<div class="panel"> <div class="panel-heading"> <a href="#'.$row['faq_id'].'" class="font-black collapsed accordian-toggle-chevron-left" data-toggle="collapse" data-parent="#accordion5">'.$row['faq_question'].'</a></div><div id="'.$row['faq_id'] .'" class="panel-collapse collapse in active"><div class="panel-body">'.$row['faq_ans'].'</div></div></div>';
			}
			else{
				echo '<div class="panel"> <div class="panel-heading"> <a href="#'.$row["faq_id"].'" class="font-black collapsed accordian-toggle-chevron-left" data-toggle="collapse" data-parent="#accordion5">'.$row["faq_question"].'</a></div><div id="'.$row["faq_id"].'" class="panel-collapse collapse"><div class="panel-body">'.$row["faq_ans"].'</div></div></div>';
				
			}
			$i++;
				
			}
			
		}
		else
		{
			echo "<span>No result found</span>";
			
		}
		
	}
	
	if((isset($_POST['action'])) && ($_POST['action']=='randomfaq')){
		
		$sql = "SELECT * FROM faq";
		
		$result = mysqli_query($conn, $sql);
		$numrows = mysqli_num_rows($result);
		if($numrows >0){
			$i=0;
			while($row = mysqli_fetch_assoc($result))
			{
			if($i==0){
				echo '<div class="panel"> <div class="panel-heading"> <a href="#'.$row['faq_id'].'" class="font-black collapsed accordian-toggle-chevron-left" data-toggle="collapse" data-parent="#accordion5">'.$row['faq_question'].'</a></div><div id="'.$row['faq_id'] .'" class="panel-collapse collapse in active"><div class="panel-body">'.$row['faq_ans'].'</div></div></div>';
			}
			else{
				echo '<div class="panel"> <div class="panel-heading"> <a href="#'.$row["faq_id"].'" class="font-black collapsed accordian-toggle-chevron-left" data-toggle="collapse" data-parent="#accordion5">'.$row["faq_question"].'</a></div><div id="'.$row["faq_id"].'" class="panel-collapse collapse"><div class="panel-body">'.$row["faq_ans"].'</div></div></div>';
				
			}
			$i++;
				
			}
			
		}
		else
		{
			echo "<span>No result found</span>";
			
		}
		
	}

?>