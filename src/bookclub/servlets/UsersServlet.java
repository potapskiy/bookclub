package bookclub.servlets;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import bookclub.db.dao.UsersDAO;
import bookclub.entities.User;
import bookclub.utils.Messages;

@Path("/user")
public class UsersServlet {

	private UsersDAO usersDAO = new UsersDAO();

	@SuppressWarnings("unchecked")
	@POST
	@Path("register")
	@Produces(MediaType.APPLICATION_JSON)
	public String registerUser(@FormParam("login") String login,
			@FormParam("password") String password,
			@FormParam("name") String name,
			@FormParam("surname") String surname) {

		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {

			if (!usersDAO.isUserRegistred(login)) {
				int id = usersDAO.insertUser(login, password, name, surname);

				
				JSONObject jsonData = new JSONObject();

				jsonData.put("id", id);
				jsonData.put("login", login);
				jsonData.put("password", password);
				jsonData.put("name", name);
				jsonData.put("surname", surname);

				jsonReruest.put("data", jsonData);

				msg = Messages.OK;
			} else {
				msg = Messages.REGISTRED;
			}

		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("check")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkUser(@HeaderParam("login") String login,
			@HeaderParam("password") String password) {

		JSONObject jsonReruest = new JSONObject();

		String msg;

		try {
			
			User user = usersDAO.isUserDataCorrect(login, password);
			
			if (user != null) {
				
				JSONObject jsonData = new JSONObject();

				jsonData.put("id", user.getId());
				jsonData.put("login", login);
				jsonData.put("password", password);
				jsonData.put("name", user.getName());
				jsonData.put("surname", user.getSurname());

				jsonReruest.put("data", jsonData);

				msg = Messages.OK;
			} else {
				msg = Messages.INCORRECT;
			}

		} catch (Exception e) {
			msg = Messages.ERROR;
		}

		jsonReruest.put("msg", msg);
		return jsonReruest.toJSONString();

	}

}
