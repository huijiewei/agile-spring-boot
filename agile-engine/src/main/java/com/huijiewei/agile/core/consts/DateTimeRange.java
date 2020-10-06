package com.huijiewei.agile.core.consts;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author huijiewei
 */

@Getter
@Setter
public class DateTimeRange {
    private final static int DATE_RANGE_SPLIT_LENGTH = 2;

    private LocalDateTime begin;
    private LocalDateTime end;

    public static DateTimeRange parse(String[] range) {
        if (range == null) {
            return null;
        }

        if (range.length != DATE_RANGE_SPLIT_LENGTH) {
            return null;
        }

        if (StringUtils.isEmpty(range[0]) || StringUtils.isEmpty(range[1])) {
            return null;
        }

        try {
            LocalDateTime startDate = LocalDateTime.parse(
                    StringUtils.contains(range[0], " ") ? range[0] : (range[0] + " 00:00:00"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime endDate = LocalDateTime.parse(
                    StringUtils.contains(range[1], " ") ? range[1] : (range[1] + " 23:59:59"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            DateTimeRange dateTimeRange = new DateTimeRange();
            dateTimeRange.begin = startDate;
            dateTimeRange.end = endDate;

            return dateTimeRange;
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}
