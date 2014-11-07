<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<fmt:requestEncoding value="UTF-8" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>BookClub</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>

<script>
$( document ).ready(function() {

console.log('aaaaaaaaa');
$("#addAuthor").submit(function(e)
		{
			var postData = $(this).serializeArray();
			var formURL = $(this).attr("action");
			$.ajax(
			{
				url : formURL,
				type: "POST",
				dataType : "json",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					var name = $("authorName").text();
					alert(name);
					$("authorName").val("");
				},
				error: function(jqXHR, textStatus, errorThrown) 
				{
					 alert('it didnt work'); 
				}
			});
		    e.preventDefault();	//STOP default action
		});
			
		$("#addAuthor").submit(); //SUBMIT FORM
		
});
</script>


</head>
<body>
<center>
	<h1>Welcome to BookClub!</h1>
</center>
<left>
<a href="./">Exit</a>
</left>

<table>
		<table border="1" width="90%">
			<tbody>
				<tr>
					<th>Authors</th>
					<th>Books</th>
					<th>Comments</th>
				</tr>


				<tr>
					<td>
						<form name="addAuthor" id="addAuthor" action="./rest/authors/add" method="post"
					accept-charset="UTF-8">
							<p>Author:<p/> 
							<p><input type="text" name="authorName" id="authorName"><p/> 
							<p><textarea rows="10" cols="45" name="authorInfo" id="authorInfo"></textarea><p/>
							<p><input type="submit" value="Add"><p/>
						</form>
					</td>
					<td>1</td>
					<td>1</td>
				</tr>

			</tbody>
		</table>
	</table>


</body>
</html>
