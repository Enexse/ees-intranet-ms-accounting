package com.enexse.intranet.ms.accounting.constants;

public class EesTimesheetEndpoint {

    // ROOT
    public static final String EES_ROOT_ENDPOINT = "/accounting-service/api/v1";
    public static final String EES_USERS_ENDPOINT = "/user-service/api/v1";

    // USERS
    public static final String EES_GET_USER_BY_ID = "/user/getByCode/{userId}";
    public static final String EES_INSERT_USER = "/user/insert";
    public static final String EES_ATTACHMENT_FILES_USER = "/file/attachments/{userId}";
    public static final String EES_ATTACHMENT_FILE_BY_ID_USERID = "/file/get/{id}/{userId}";
    public static final String EES_ATTACHMENT_FILE_DELETE_BY_ID = "/file/deleteById";

    // CUSTOMERS
    public static final String EES_GET_CUSTOMER_BY_ID = "/customer/getById/{customerId}";

    // ORDER NUMBER
    public static final String EES_GET_ORDER_NUMBER_BY_CODE = "/orderNumber/getByCode/{code}";

    // REQUESTS
    public static final String EES_GET_REQUEST_BY_ID = "/request/getById/{requestId}";

    // SUB-REQUEST
    public static final String EES_GET_SUB_REQUEST_BY_ID = "/subrequest/getById/{subRequestId}";

    // TIMESHEET WORKPLACE
    public static final String EES_UPDATE_BY_CODE_TIMESHEET_WORKPLACE = "/workplace/updateByCode/{workPlaceCode}";
    public static final String EES_INSERT_TIMESHEET_WORKPLACE = "/workplace/insert";
    public static final String EES_GET_All_TIMESHEET_WORKPLACES = "/workplace/getAllWorkPlaces";
    public static final String EES_GET_TIMESHEET_WORKPLACE_BY_CODE = "/workplace/getWorkPlaceByCode/{workPlaceCode}";
    public static final String EES_DELETE_BY_CODE_TIMESHEET_WORKPLACE = "/workplace/deleteByCode/{workPlaceCode}";

    public static final String EES_GET_WORKPLACE_TITLE = "workplace/getTitle";

    // TIMESHEET ACTIVITY
    public static final String EES_UPDATE_BY_CODE_TIMESHEET_ACTIVITY = "/activity/updateByCode/{activityCode}";
    public static final String EES_INSERT_TIMESHEET_ACTIVITY = "/activity/insert";
    public static final String EES_GET_All_TIMESHEET_ACTIVITIES = "/activity/getAllActivities";
    public static final String EES_GET_TIMESHEET_ACTIVITY_BY_CODE = "/activity/getActivityByCode/{activityCode}";
    public static final String EES_DELETE_BY_CODE_TIMESHEET_ACTIVITY = "/activity/deleteByCode/{activityCode}";

    // TIMESHEET CONTRACT HOURS
    public static final String EES_UPDATE_BY_CODE_TIMESHEET_CONTRACTHOURS = "/contractHours/updateByCode/{contractHoursCode}";
    public static final String EES_INSERT_TIMESHEET_CONTRACTHOURS = "/contractHours/insert";
    public static final String EES_GET_All_TIMESHEET_CONTRACTHOURS = "/contractHours/getAllContractHours";
    public static final String EES_GET_TIMESHEET_CONTRACTHOURS_BY_CODE = "/contractHours/getByCode/{contractHoursCode}";
    public static final String EES_DELETE_BY_CODE_TIMESHEET_CONTRACTHOURS = "/contractHours/deleteByCode/{contractHoursCode}";

    // TIMESHEET WORKTIME
    public static final String EES_UPDATE_BY_CODE_TIMESHEET_WORKTIME = "/worktime/updateByCode/{worktimeCode}";
    public static final String EES_INSERT_TIMESHEET_WORKTIME = "/worktime/insert";
    public static final String EES_GET_All_TIMESHEET_WORKTIMS = "/worktime/getAllWorktimes";
    public static final String EES_GET_TIMESHEET_WORKTIME_BY_CODE = "/worktime/getWorkTimeByCode/{worktimeCode}";
    public static final String EES_DELETE_BY_CODE_TIMESHEET_WORKTIME = "/worktime/deleteByCode/{worktimeCode}";

    // APPOINTMENT
    public static final String EES_INSERT_TIMESHEET_APPOINTMENT = "/appointment/insertAppointment";
    public static final String EES_INSERT_MASSIVE_TIMESHEET_APPOINTMENT = "/appointment/insertMassiveAppointment";
    public static final String EES_GET_TIMESHEET_APPOINTMENT_BY_USERID = "/appointment/getByUserId";
    public static final String EES_DELETE_TIMESHEET_APPOINTMENT = "/appointment/deleteAppointment/{userId}";
    public static final String EES_UPDATE_TIMESHEET_APPOINTMENT = "/appointment/updateAppointment";

    // USER TIMESHEET
    public static final String EES_UPDATE_USER = "/user/update/{userId}";
    public static final String EES_USER_TIMESHEET = "/user/get/timesheet";

    // SUMMARY TIMESHEET
    public static final String EES_GET_SUMMARY_TIMESHEET = "/summary_timesheet/{userId}";
    public static final String EES_GET_ALL_SUMMARY_TIMESHEET = "/summary_timesheet/getAll";
    public static final String EES_GET_SUMMARY_TIMESHEET_BY_USERID_YEAR = "/summary_timesheet/getById/{userId}/{year}";
    public static final String EES_GET_SUMMARY_TIMESHEET_RESPONSE_BY_USERID_YEAR = "/summary_timesheet/response/getById/{userId}/{year}";
    public static final String EES_GET_SUMMARY_TIMESHEET_BY_USERID = "/summary_timesheet/getById/{userId}";
    public static final String EES_SUBMIT_SUMMARY_TIMESHEET_BY_USERID = "/summary_timesheet/submit/{userId}";
    public static final String EES_GET_SUMMARY_TIMESHEET_BY_USERID_MONTH_YEAR = "/summary_timesheet/getByUserIdAndMonthAndYear/{userId}";
    public static final String EES_GET_PREPARE_SUMMARY_TIMESHEET = "/summary_timesheet/prepare";
    public static final String EES_UPDATE_SUBMIT_SUMMARY_TIMESHEET_BY_USERID = "/summary_timesheet/updateByMonthAndYear/{userId}";
    public static final String EES_ATTACHMENT_FILES_SUMMARY_TIMESHEET_BY_USERID = "/summary_timesheet/attachmentFilesByMonthAndYear/{userId}";
    public static final String EES_GET_SUMMARY_TIMESHEET_BY_MONTH_YEAR = "/summary_timesheet/getByMonthAndYear";
    public static final String EES_DELETE_SUMMARY_TIMESHEET_ATTACHMENT = "/summary_timesheet/delete/attachment/{id}/{userId}";

}
