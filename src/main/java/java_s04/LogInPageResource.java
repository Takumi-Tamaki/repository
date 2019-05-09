package java_s04;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.LogIn;
import dao.LogInDAO;

@Path("logIn")
public class LogInPageResource {
	private final LogInDAO dao = new LogInDAO();

	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML })
    @Produces(MediaType.APPLICATION_JSON)
	public boolean findAll(@FormParam("id")int id, @FormParam("pass") String pass, @Context HttpServletRequest request ) throws WebApplicationException {
//		System.out.println(id+":"+pass);
		List<LogIn> logInList = dao.findAll();

		//ログイン済みか判定
		if(request.getSession(false) != null){
			return true;
		}

		for(int i = 0; i < logInList.size(); i ++){
			System.out.println(logInList.get(i).getId()+":"+logInList.get(i).getPass());
			if(logInList.get(i).getId() == id && logInList.get(i).getPass().equals(pass)){
				HttpSession session = request.getSession();
				session.setAttribute("id",id);
				return true;
			}
		}
		return false;
	}

}
