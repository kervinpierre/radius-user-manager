package com.sludev.propsystem.radiususermanager.util;

/**
 * Created by kervin on 2016-07-05.
 */
public final class RUMConstants
{
    public static final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&?+]).{8,32})";
    public static final String PASSWORD_HASH_DEFAULT = "SSHA-Password";
}
