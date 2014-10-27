package bookclub.servlets;

import java.util.List;

import javax.ws.rs.GET;
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
	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBookcomments(@HeaderParam("id") int bookId) {

		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			List<Comment> comments = commentsDAO.getBookComments(bookId);
			jsonReruest.put("bookId", bookId);
			JSONArray jsonComments = new JSONArray();

			for (Comment comment : comments) {

				JSONObject jsonComment = new JSONObject();
				jsonComment.put("id", comment.getCommentId());
				jsonComment.put("name", comment.getUserName());
				jsonComment.put("surname", comment.getUserSurname());
				jsonComment.put("comment", comment.getCommentText());
				

				jsonComments.add(jsonComment);

			}

		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		msg = Messages.OK;
		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}
	
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addComment(@HeaderParam("book-id") int bookId, 
			@HeaderParam("user-id") int userId,
			@HeaderParam("comment") String comment) {

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
	@GET
	@Path("delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String addComment(@HeaderParam("comment-id") int commentId) {

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

}
