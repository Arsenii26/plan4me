package ca.arsenii.plan4me.web;

import ca.arsenii.plan4me.model.AbstractBaseEntity;

public class SecurityUtil {
    private static int id = AbstractBaseEntity.START_SEQ;

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }


}