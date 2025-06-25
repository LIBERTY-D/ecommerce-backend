package com.daniel.apps.ecommerce.app.util;

import java.util.UUID;

public class TokenUtil {
    public static String confirmationToken() {
        return UUID.randomUUID().toString();
    }


}
