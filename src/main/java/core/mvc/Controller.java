package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
