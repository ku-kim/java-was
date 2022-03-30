package webserver.controller;

import db.SessionDataBase;
import java.io.IOException;
import java.util.Optional;
import webserver.Request;
import webserver.Response;
import webserver.StatusCode;

public class UserListController implements Controller {

	private static final UserListController instance = new UserListController();

	private UserListController() {}

	public static UserListController getInstance() {
		return instance;
	}

	@Override
	public void process(Request request, Response response) throws IOException {
		//쿠키 매칭
		Optional<String> sessionId = request.getCookieValue("SessionId");
		String cookie = sessionId.orElse("");
		Optional<String> userIdByCookie = SessionDataBase.findUserIdByCookie(cookie);
		String userId = userIdByCookie.orElse("");

		if(cookie != "" && userId != "") {
			//로그인 시 user/list.html을 동적 생성해서 보내줌.
			response.setRedirect(StatusCode.REDIRECTION_302,
				"http://localhost:8080/user/list.html");
			return;
		}

		response.setRedirect(StatusCode.REDIRECTION_302,
			"http://localhost:8080/user/login.html");
	}
}
