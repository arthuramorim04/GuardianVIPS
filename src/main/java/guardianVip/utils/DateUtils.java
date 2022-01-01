package guardianVip.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static Long getDiffDays(LocalDateTime firstDate, LocalDateTime secondDate) {
        return ChronoUnit.DAYS.between(firstDate, secondDate);
    }

    public static String getTimeLeft(LocalDateTime firstDate, LocalDateTime secondDate) {
        return "";
    }
}
