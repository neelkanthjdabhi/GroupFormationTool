package com.app.group15.services;

import com.app.group15.persistence.dao.CourseDao;
import com.app.group15.persistence.dao.CourseInstructorMapperDao;
import com.app.group15.persistence.dao.UserDao;
import com.app.group15.persistence.dao.UserRoleDao;
import com.app.group15.persistence.entity.UserEntity;
import com.app.group15.persistence.entity.UserRoleMapperEntity;
import com.app.group15.persistence.injectors.CourseDaoInjectorService;
import com.app.group15.persistence.injectors.CourseInstructorMapperDaoInjectorService;
import com.app.group15.persistence.injectors.UserDaoInjectorService;
import com.app.group15.persistence.injectors.UserRoleDaoInjectorService;

import java.util.Set;

public class AssignTAService {

    private UserDao userDao = new UserDaoInjectorService().getUserDao();
    private CourseDao courseDao = new CourseDaoInjectorService().getCourseDao();
    private UserRoleDao userRoleDao = new UserRoleDaoInjectorService().getUserRoleDao();
    private CourseInstructorMapperDao courseInstructorMapperDao = new CourseInstructorMapperDaoInjectorService().getCourseInstructorMapperDao();


    public boolean performTAUpdate(String bannerId, int courseId)
    {
        System.out.println(bannerId + courseId);
        if (validateBannerID(bannerId) && validateCourseID(courseId)) {
            UserEntity userEntity= userDao.getUserByBannerId(bannerId);
            courseInstructorMapperDao.addTaToACourse(courseId,userEntity.getId());
            return true;
        }else {
            return false;
        }
    }

    public  boolean validateBannerID(String bannerId) {
        Set roleSet = userRoleDao.getRolesByBannerId(bannerId);
        if (roleSet.contains("STUDENT")) {
            return true;
        }else {
            return false; }
    }

    public boolean validateCourseID(int courseId) {
        if (courseDao.get(courseId) != null) {
            return true;
        }else {
            return false;
        }
    }

}
