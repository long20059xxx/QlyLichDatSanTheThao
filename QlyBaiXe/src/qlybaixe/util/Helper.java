package qlybaixe.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Helper {

    private static final Locale VIETNAM = new Locale("vi", "VN");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private Helper() {
    }

    public static String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "0";
        }
        return NumberFormat.getCurrencyInstance(VIETNAM).format(amount);
    }

    public static String formatVnd(BigDecimal amount) {
        if (amount == null) {
            return "0 đ";
        }
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(VIETNAM);
        decimalFormat.applyPattern("#,##0");
        return decimalFormat.format(amount).replace(',', '.') + " đ";
    }

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DATE_FORMAT);
    }

    public static String formatTime(LocalTime time) {
        if (time == null) {
            return "";
        }
        return time.format(TIME_FORMAT);
    }
}
