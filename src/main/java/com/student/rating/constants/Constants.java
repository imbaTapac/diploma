package com.student.rating.constants;

public class Constants {
    private Constants(){
        throw new IllegalStateException("Constant class");
    }

    /* Attributes ID form LDAP */
    public static final String FACULTY_ATTRIBUTE = "o";
    public static final String ID_ATTRIBUTE = "uidNumber";
    public static final String EMAIL_ATTRIBUTE = "mail";
    public static final String USERNAME_ATTRIBUTE = "uid";
    public static final String OKR_ATTRIBUTE = "title";
    public static final String STUDENT_FULL_NAME_ATTRIBUTE = "cn";
    public static final String ROLE_ATTRIBUTE = "objectClass";

    /* Admin constant */
    public static final String ORGANIZATIONAL_PERSON = "organizationalPerson";

    /* Faculty names */
    public static final String IT_FACULTY = "Інформаційних технологій";

    /* Approve stages */
	public static final Integer DECLINED = -1;
	public static final Integer UNAPPROVED = 0;
	public static final Integer APPROVED_BY_HEAD_OF_GROUP = 1;
	public static final Integer APPROVED_BY_HEAD_OF_SO = 2;
}
