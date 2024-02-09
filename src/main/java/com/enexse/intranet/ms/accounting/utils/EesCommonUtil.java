package com.enexse.intranet.ms.accounting.utils;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetConstant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class EesCommonUtil {

    public static String generateCurrentDateUtil() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(EesTimesheetConstant.EES_PATTERN_DATE);
        return dtf.format(LocalDateTime.now());
    }

    public static String generateCurrentMonthUtil() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(EesTimesheetConstant.EES_PATTERN_DATE);
        String dateString = dtf.format(LocalDateTime.now());
        LocalDateTime dateTime = LocalDateTime.parse(dateString, dtf);
        String monthName = dateTime.getMonth().name();
        return monthName;
    }

    public static String generateCurrentYearUtil() {

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);
        return yearInString;
    }

    public static String generateExpirationLink() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(EesTimesheetConstant.EES_PATTERN_DATE);
        return dtf.format(LocalDateTime.now().plusMinutes(EesTimesheetConstant.EES_EXPIRATION_LINK));
    }

    /*public static String generateTemporalPassword() {
        return RandomStringUtils.random(1, EesUserConstants.EES_RANDOM_LETTERS).toUpperCase()
                + RandomStringUtils.random(6, EesUserConstants.EES_RANDOM_LETTERS).toLowerCase()
                + RandomStringUtils.random(5, EesUserConstants.EES_RANDOM_NUMBERS)
                + RandomStringUtils.random(1, EesUserConstants.EES_RANDOM_CHARS);
    }*/

    public static String generateFirstPassword(String lastname) {

        String firstPassword = lastname.split(" ")[0].substring(0, 1).toUpperCase(Locale.ROOT)
                + lastname.split(" ")[0].substring(1).toLowerCase(Locale.ROOT)
                + "@1234";
        return firstPassword;
    }

    public static String[] generateDefaultAvatar() {
        int rnd = new Random().nextInt(EesTimesheetConstant.EES_DEFAULT_AVATAR.length);
        return EesTimesheetConstant.EES_DEFAULT_AVATAR[rnd];
    }
}
