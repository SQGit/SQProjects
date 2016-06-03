<?php

include ("../../includes/db_config.php");


	
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
				echo '<div class="panel"> <div class="panel-heading"> <a href="#'.$row['faq_id'].'" class="font-black collapsed accordian-toggle-chevron-left" data-toggle="collapse" data-parent="#accordion5"><span class="inlineedit" data-name="faq_question" data-type="text" data-pk="'. $row['faq_id'] .'" data-url="pjtlstupdate.php">'. $row['faq_question'].'</span></a></div><div id="'.$row['faq_id'] .'" class="panel-collapse collapse in active"><div class="panel-body"> <span class="inlineedit" data-name="faq_question" data-type="wysihtml5" data-pk="'. $row['faq_id'] .'" data-url="pjtlstupdate.php">'.$row['faq_ans'].'</span></div></div></div>';
			}
			else
			{
				echo '<div class="panel"> <div class="panel-heading"> <a href="#'.$row["faq_id"].'" class="font-black collapsed accordian-toggle-chevron-left" data-toggle="collapse" data-parent="#accordion5"><span class="inlineedit" data-name="faq_question" data-type="text" data-pk="'.$row['faq_id'] .'" data-url="pjtlstupdate.php">'.$row["faq_question"].'</span></a></div><div id="'.$row["faq_id"].'" class="panel-collapse collapse"><div class="panel-body"><span class="inlineedit" data-name="faq_question" data-type="wysihtml5" data-pk="'.$row['faq_id'] .'" data-url="pjtlstupdate.php">'.$row["faq_ans"].'</span></div></div></div>';
			}
			$i++;
				
			}
			
		}
		else
		{
			echo "<span>No result found</span>";
			
		}
		
	}
	echo '<script type="text/javascript">		
		$.fn.editable.defaults.mode = "popup";                       
        $(".inlineedit").editable();          
    </script>';

?>