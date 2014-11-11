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

<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />

<script>
$( document ).ready(function() {
	
	
	$(function() {
		$("#bookAuthor").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : "./get_author_name/",
					type : "POST",
					data : {
						term : request.term
					},

					dataType : "json",

					success : function(data) {
						response(data);
					}
				});
			}
		});
	});
	

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
					
					
					$("#authorName").val('');
					$("#authorInfo").val('');
				
					if (data.msg =='ok'){
						alert("Author added");
					}else{
						alert('Error');
					}
				},
				error: function(jqXHR, textStatus, errorThrown) 
				{
					 alert('Error');
					 
					 $("#authorName").val('');
					 $("#authorInfo").val('');
					 
				}
			});
		    e.preventDefault();	//STOP default action
		});
			
			
			
	$("#addBook").submit(function(e)
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
					
					
					$("#bookAuthor").val('');
					$("#bookTitle").val('');
					$("#bookInfo").val('');
					$("#bookGenre :first").attr("selected", "selected");
				
					if (data.msg =='ok'){
						alert("Book added");
					}else{
						alert('Error');
					}
				},
				error: function(jqXHR, textStatus, errorThrown) 
				{
					 alert('Error');
					 
					 $("#bookAuthor").val('');
					 $("#bookTitle").val('');
					 $("#bookInfo").val('');
					 $("#bookGenre :first").attr("selected", "selected");
					 
				}
			});
		    e.preventDefault();	//STOP default action
		});
		
		
});
</script>


</head>
<body>
<center>
	<h1>Welcome to BookClub!</h1>
</center>
<p align="left">
<a href="./">Exit</a>
</p>

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
					<div id="addAuthorBlock">
						<form name="addAuthor" id="addAuthor" action="./rest/authors/add" method="post"
					accept-charset="UTF-8">
						<fieldset>
								<legend>Add Author</legend>
								<table>
									<tr>
										<th align="left"><label for="authorName">Name </label></th>
										<td align="left"><input id="authorName" name="authorName" /></td>
									</tr>
									<tr>
										<th align="left"><label for="authorInfo">Info </label></th>
										<td align="left"><textarea rows="10" cols="45" name="authorInfo" id="authorInfo"></textarea></td>
									</tr>
									<tr>
										<td align="left"><input type="submit" value="Add"></td>
									</tr>
									
								</table>
							</fieldset>
								</form>
						</div>
					</td>
					<td>
					<div id="addBookBlock">
						<form name="addBook" id="addBook" action="./rest/catalog/add" method="post"
					accept-charset="UTF-8">
						<fieldset>
								<legend>Add Book</legend>
								<table>
									<tr>
										<th align="left"><label for="bookTitle">Title </label></th>
										<td align="left"><input id="bookTitle" name="bookTitle" /></td>
									</tr>
									<tr>
										<th align="left"><label for="bookAuthor">Author </label></th>
										<td align="left"><input id="bookAuthor" name="bookAuthor" /></td>
									</tr>
									
									<tr>
										<th align="left"><label for="bookGenre">Genre </label></th>
										<td align="left"><select id="bookGenre"
											name="bookGenre">
												<option selected value="Adventure">Adventure</option>
												<option value="Business and finance">Business and finance</option>
												<option value="Classics">Classics</option>
												<option value="Fantasy">Fantasy</option>
												<option value="Philosophy">Philosophy</option>
												<option value="Poetry">Poetry</option>
												<option value="Romance">Romance</option>
										</select></td>
									</tr>
									<tr>
										<th align="left"><label for="bookInfo">Info </label></th>
										<td align="left"><textarea rows="10" cols="45" name="bookInfo" id="bookInfo"></textarea></td>
									</tr>
									<tr>
										<td align="left"><input type="submit" value="Add"></td>
									</tr>
									
								</table>
							</fieldset>
								</form>
					</div>
						


					</td>
					<td>1</td>
				</tr>

			</tbody>
		</table>
	</table>


</body>
</html>
