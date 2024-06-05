package com.enexse.intranet.ms.accounting.constants;

public class EesTimesheetConstant {

    // TOOLS
    public static final String EES_APP_NAME_USERS = "EES-MS-USERS";
    public static final String EES_APP_NAME_ACCOUNTING = "EES-MS-ACCOUNTING";
    public static final String EES_SECRET_KEY = "ees-ms-management";
    public static final int EES_EXPIRATION_TOKEN = 1000 * 60 * 60 * 8;
    public static final int EES_EXPIRATION_LINK = 2;
    public static final int EES_EXPIRATION_DAYS_TO = 90;
    public static final String EES_RANDOM_CHARS = "~`!@#$%^&*()-_=+[{]}\\\\|;:\\'\\\",<.>/?\"";
    public static final String EES_RANDOM_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String EES_RANDOM_NUMBERS = "0123456789";
    public static final String EES_AUTHORIZATION = "Authorization";
    public static final String EES_PREFIX_ORGANIZATION = "EES";
    public static final String EES_DEFAULT_ORGANIZATION = "ENEXSE";
    public static final String EES_HEADER = "Bearer ";
    public static final String EES_ROLE_ADMIN = "ROLE_ADMIN";
    public static final String EES_ROLE_USER = "ROLE_USER";
    public static final String EES_ROLE_MODERATOR = "ROLE_MODERATOR";
    public static final String[] EES_ADMIN_ACCESS = {"ROLE_ADMIN", "ROLE_MODERATOR"};
    public static final String[] EES_MODERATOR_ACCESS = {"ROLE_MODERATOR"};
    public static final String EES_VERIFY_TYPE_RESET_PASSWORD = "RESET_PASSWORD";
    public static final String EES_VERIFY_TYPE_EMAIL_VERIFICATION = "EES_EMAIL_VERIFICATION";
    public static final String EES_VERIFY_TYPE_INVITATION_USER = "EES_INVITATION_USER";
    public static final String EES_VERIFY_TYPE_EMAIL_ADMIN_USER = "EMAIL_ADMIN_USER_VERIFICATION";
    public static final String EES_VERIFY_TYPE_FORGOT_PASSWORD = "EES_FORGOT_PASSWORD";
    public static final String EES_VERIFY_TYPE_UPDATE_REQUEST_STATUS = "EES_UPDATE_REQUEST_STATUS";
    public static final String[][] EES_DEFAULT_AVATAR = {
            {"bg-light", "text-inverse-light"}, {"bg-info", "text-inverse-info"},
            {"bg-primary", "text-inverse-primary"}, {"bg-secondary", "text-inverse-secondary"},
            {"bg-success", "text-inverse-success"}, {"bg-warning", "text-inverse-warning"},
            {"bg-danger", "text-inverse-danger"}, {"bg-dark", "text-inverse-dark"},
            {"bg-white", "text-inverse-white"}, {"bg-body", "text-inverse-body"},
            {"bg-light-primary", "text-primary"}, {"bg-light-secondary", "text-secondary"},
            {"bg-light-success", "text-success"}, {"bg-light-warning", "text-warning"},
            {"bg-light-info", "text-info"}, {"bg-light-danger", "text-danger"},
            {"bg-light-dark", "text-dark"},
    };

    // MODELS
    public static final String EES_USER_REQUIRED = "User is required";
    public static final String EES_FIRST_NAME_REQUIRED = "Firstname is required";
    public static final String EES_LASTNAME_REQUIRED = "Lastname is required";
    public static final String EES_GENDER_REQUIRED = "Gender is required";
    public static final String EES_USERNAME_REQUIRED = "Username is required";
    public static final String EES_NAME_REQUIRED = "Name is required";
    public static final String EES_PERSONAL_EMAIL_REQUIRED = "Personal email is required";
    public static final String EES_EMAIL_REQUIRED = "Enexse email is required";
    public static final String EES_EMAIL_FORMAT = "Email should be in this format xxx.yyyy@enexse.com";
    public static final String EES_PEC_EMAIL_REQUIRED = "PEC email is required";
    public static final String EES_PASSWORD_REQUIRED = "Password is required";
    public static final String EES_PHONE_REQUIRED = "Phone is required";
    public static final String EES_PREFIX_PHONE_REQUIRED = "Prefix phone is required";
    public static final String EES_COUNTRY_REQUIRED = "Country is required";
    public static final String EES_STATE_REQUIRED = "State is required";
    public static final String EES_ADDRESS_REQUIRED = "Complementary address is required";
    public static final String EES_CITY_REQUIRED = "City is required";
    public static final String EES_TITLE_REQUIRED = "Title is required";
    public static final String EES_DESCRIPTION_REQUIRED = "Description is required";
    public static final String EES_CODE_REQUIRED = "Code is required";
    public static final String EES_LANGUAGE_REQUIRED = "Language is required";
    public static final String EES_CONTENT_REQUIRED = "Content is required";
    public static final String EES_OBJECT_REQUIRED = "Object is required";
    public static final String EES_WEBSITE_REQUIRED = "Website is required";
    public static final String EES_REFERENT_REQUIRED = "Organization referent is required";

    public static final String EES_USER_PHONE_VALID = "Please enter the valid phone";
    public static final String EES_USER_PASSWORD = "User password is required";

    // PATTERNS
    public static final String EES_PATTERN_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String EES_PATTERN_PHONE = "^([+]\\d{2})?\\d{10}$";
    public static final String EES_PATTERN_PASSWORD = "/^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9!@#$%^&*]{8,30}$/";
    public static final String EES_PATTERN_DATE = "yyyy/MM/dd HH:mm:ss";

    public static final String EES_ROLE_ADMINISTRATOR = "EES-ADMINISTRATOR";
    public static final String EES_ROLE_COLLABORATOR = "EES-COLLABORATOR";
    public static final String EES_ROLE_RESPONSABLE = "EES-RESPONSABLE";
    public static final String EES_DEFAULT_ROLES = "default-roles-ees-ms-authentification";
    public static final String EES_SLOT_TIME_MORNING = "MORNING";
    public static final String EES_SLOT_TIME_EVENING = "EVENING";
    public static final String EES_SLOT_TIME_NIGHT = "NIGHT";

    public static final String EES_CLOUDINARY_TIMESHEET_FOLDER = "Timesheet Collaborators";
    public static final String EES_CLOUDINARY_TIMESHEET_TYPE = "EES_CLOUDINARY_TIMESHEET_TYPE";
}
