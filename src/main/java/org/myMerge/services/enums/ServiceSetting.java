package org.myMerge.services.enums;


public enum ServiceSetting {
    TYPE_INT("i"),
    TYPE_STRING("s"),
    ORDER_ASC("a"),
    ORDER_DEC("d"),
    OPEN("o"),
    CLOSE("c");

    private final String str;

    ServiceSetting(String s) {
        this.str = s;
    }

    public String toString() {
        return this.str;
    }

    public boolean equals(String s) {
        return this.str.equals(s);
    }

    public static ServiceSetting fromValue(String v) {
        for (ServiceSetting set : ServiceSetting.values()){
            if (set.str.equals(v)) {
                return set;
            }
        }

        return null;
    }
}