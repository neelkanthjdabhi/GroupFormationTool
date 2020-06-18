package com.app.group15.PasswordPolicyManagement;

import com.app.group15.Config.AppConfig;
import com.app.group15.Utility.GroupFormationToolLogger;
import com.app.group15.Utility.ServiceUtility;

import java.util.List;
import java.util.logging.Level;

public class PasswordPolicyMaxLength implements IPasswordPolicyValidator {

    private PasswordPolicyAbstractDao passwordPolicyDao;

    @Override
    public boolean isPasswordValid(String password) {
        if (ServiceUtility.isNotNull(password)) {

            passwordPolicyDao = AppConfig.getInstance().getPasswordPolicyDao();

            List<PasswordPolicy> passwordPolicyList = passwordPolicyDao.getAll();

            int maximumLengthAllowed = Integer.parseInt(passwordPolicyList.get(1).getPolicyValue());

            return password.length() <= maximumLengthAllowed;
        } else {
            GroupFormationToolLogger.log(Level.SEVERE, "Password is null");
        }
        return false;

    }
}