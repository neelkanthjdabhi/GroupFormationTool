package com.app.group15.services;

import com.app.group15.persistence.dao.UserDao;
import com.app.group15.persistence.dao.UserRoleDao;
import com.app.group15.persistence.entity.UserEntity;
import com.app.group15.persistence.injectors.UserDaoInjectorService;
import com.app.group15.persistence.injectors.UserRoleDaoInjectorService;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class SessionService {
	public static boolean isUserSignedIn(HttpServletRequest request) {
		String sessionBannerId = (String) request.getSession().getAttribute("BANNER_ID_SESSION");
		return sessionBannerId != null;
	}

	public static UserEntity getSessionUser(HttpServletRequest request) {
		UserDao userDao = new UserDaoInjectorService().getUserDao();
		String sessionBannerId = (String) request.getSession().getAttribute("BANNER_ID_SESSION");
		return userDao.getUserByBannerId(sessionBannerId);
	}

	public static void destroySession(HttpServletRequest request){
		request.getSession().invalidate();
	}
	public static void setSession(HttpServletRequest request, String name, String value){
		request.getSession().setAttribute(name, value);
	}

	public static String getUserHome(HttpServletRequest request){
		String sessionBannerId = (String) request.getSession().getAttribute("BANNER_ID_SESSION");
		UserRoleDao userRoleDao = new UserRoleDaoInjectorService().getUserRoleDao();
		Set<String> roles = userRoleDao.getRolesByBannerId(sessionBannerId);
		String redirect;
		if (roles.contains("ADMIN")){
			redirect= "redirect:/admin/home";
		} else if (roles.contains("STUDENT")|| roles.contains("TA")){
			redirect= "redirect:/student/home";
		} else {
			redirect= "redirect:/user/home";
		}
		return redirect;
	}
}
