package bookclub.servlets;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bookclub.db.dao.CommentsDAO;
import bookclub.entities.Comment;
import bookclub.utils.Messages;

@Path("/comments")
public class CommentsServlet {

	private CommentsDAO commentsDAO = new CommentsDAO();

	@SuppressWarnings("unchecked")
	@POST
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookcomments(@FormParam("book-id") int bookId,
			@DefaultValue("10")@FormParam("limit") int limit,
			@DefaultValue("0")@FormParam("offset") int offset) {

		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			int commentsCount = commentsDAO.getBookCommentsCount(bookId);
			
			jsonReruest.put("bookId", bookId);
			jsonReruest.put("total_comments", commentsCount);
			
			List<Comment> comments = commentsDAO.getBookComments(bookId,limit,offset);
			JSONArray jsonComments = new JSONArray();

			for (Comment comment : comments) {

				JSONObject jsonComment = new JSONObject();
				jsonComment.put("id", comment.getCommentId());
				jsonComment.put("name", comment.getUserName());
				jsonComment.put("surname", comment.getUserSurname());
				jsonComment.put("comment", comment.getCommentText());
				

				jsonComments.add(jsonComment);

			}
			
			msg = Messages.OK;
			
			jsonReruest.put("rows", jsonComments);

		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addComment(@FormParam("book-id") int bookId, 
			@FormParam("user-id") int userId,
			@FormParam("comment") String comment) {

		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			commentsDAO.insertComment(userId, bookId, comment);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String addComment(@FormParam("comment-id") int commentId) {

		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			commentsDAO.deleteComment(commentId);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateComment(@FormParam("comment-id") int commentId, 
			@FormParam("comment") String comment) {

		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			commentsDAO.updateComment(commentId, comment);
			msg = Messages.OK;
			
		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}

}
