package com.app.testLabs.application.util.dateUtil;

import jakarta.validation.ValidationException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    private DateUtil() {
    }

    public static Date stringToDate(String dateString) {
        try {
            LocalDate localDate = LocalDate.parse(dateString);
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch(Exception e) {
            throw new ValidationException("Falha ao realizar parse na data");
        }
    }
}
