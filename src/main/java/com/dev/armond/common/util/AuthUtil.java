package com.dev.armond.common.util;

public class AuthUtil {
    public static String memberIdWithPrefix(Long memberId) {
        return "UID:" + memberId;
    }

    public static Long memberIdWithoutPrefix(String uidMemberId) {
        return Long.parseLong(uidMemberId.substring(4));
    }
}
