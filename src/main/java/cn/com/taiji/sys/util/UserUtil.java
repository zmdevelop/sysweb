package cn.com.taiji.sys.util;

import org.springframework.security.core.context.SecurityContextHolder;

import cn.com.taiji.security.SecurityUser;

public class UserUtil {

	public static SecurityUser getCurrentUserDto() {
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            return null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SecurityUser) {
            return (SecurityUser) principal;
        }
        return null;
    }
}
