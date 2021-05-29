package xyz.ycode.plugin.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");

    public static String stringBuilder(String[] args, int liczOdArgumentu) {
        String msg = "";

        for(int i = liczOdArgumentu; i < args.length; ++i) {
            msg = msg + args[i];
            if (i <= args.length - 2) {
                msg = msg + " ";
            }
        }

        return msg;
    }

    public static long getTimeWithString(String s) {
        Pattern pattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[, \\s]*)?(?:([0-9]+)\\s*mo[a-z]*[, \\s]*)?(?:([0-9]+)\\s*d[a-z]*[, \\s]*)?(?:([0-9]+)\\s*h[a-z]*[, \\s]*)?(?:([0-9]+)\\s*m[a-z]*[, \\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
        Matcher matcher = pattern.matcher(s);
        long czas = 0L;
        boolean found = false;

        while(true) {
            do {
                do {
                    if (!matcher.find()) {
                        if (!found) {
                            return -1L;
                        }

                        return czas * 1000L;
                    }
                } while(matcher.group() == null);
            } while(matcher.group().isEmpty());

            for(int i = 0; i < matcher.groupCount(); ++i) {
                if (matcher.group(i) != null && !matcher.group(i).isEmpty()) {
                    found = true;
                    break;
                }
            }

            if (matcher.group(1) != null && !matcher.group(1).isEmpty()) {
                czas += (long)(31536000 * Integer.valueOf(matcher.group(1)));
            }

            if (matcher.group(2) != null && !matcher.group(2).isEmpty()) {
                czas += (long)(2592000 * Integer.valueOf(matcher.group(2)));
            }

            if (matcher.group(3) != null && !matcher.group(3).isEmpty()) {
                czas += (long)(86400 * Integer.valueOf(matcher.group(3)));
            }

            if (matcher.group(4) != null && !matcher.group(4).isEmpty()) {
                czas += (long)(3600 * Integer.valueOf(matcher.group(4)));
            }

            if (matcher.group(5) != null && !matcher.group(5).isEmpty()) {
                czas += (long)(60 * Integer.valueOf(matcher.group(5)));
            }

            if (matcher.group(6) != null && !matcher.group(6).isEmpty()) {
                czas += (long)Integer.valueOf(matcher.group(6));
            }
        }
    }

    public static String getDate(long czas) {
        return sdf.format(new Date(czas));
    }

    public static String getDurationBreakdownShort(long millis) {
        if (millis == 0L) {
            return "0";
        } else {
            long days = TimeUnit.MILLISECONDS.toDays(millis);
            if (days > 0L) {
                millis -= TimeUnit.DAYS.toMillis(days);
            }

            long hours = TimeUnit.MILLISECONDS.toHours(millis);
            if (hours > 0L) {
                millis -= TimeUnit.HOURS.toMillis(hours);
            }

            long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
            if (minutes > 0L) {
                millis -= TimeUnit.MINUTES.toMillis(minutes);
            }

            long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
            if (seconds > 0L) {
                long var10000 = millis - TimeUnit.SECONDS.toMillis(seconds);
            }

            StringBuilder sb = new StringBuilder();
            if (days > 0L) {
                sb.append(days);
                sb.append("d ");
            }

            if (hours > 0L) {
                sb.append(hours);
                sb.append("h ");
            }

            if (minutes > 0L) {
                sb.append(minutes);
                sb.append("m ");
            }

            if (seconds > 0L) {
                sb.append(seconds);
                sb.append("s ");
            }

            return sb.toString();
        }
    }

    public static String getDurationBreakdown(long millis) {
        if (millis == 0L) {
            return "0";
        } else {
            long days = TimeUnit.MILLISECONDS.toDays(millis);
            if (days > 0L) {
                millis -= TimeUnit.DAYS.toMillis(days);
            }

            long hours = TimeUnit.MILLISECONDS.toHours(millis);
            if (hours > 0L) {
                millis -= TimeUnit.HOURS.toMillis(hours);
            }

            long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
            if (minutes > 0L) {
                millis -= TimeUnit.MINUTES.toMillis(minutes);
            }

            long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
            if (seconds > 0L) {
                long var10000 = millis - TimeUnit.SECONDS.toMillis(seconds);
            }

            StringBuilder sb = new StringBuilder();
            long i;
            if (days > 0L) {
                sb.append(days);
                i = days % 10L;
                if (i == 1L) {
                    sb.append(" dzien ");
                } else {
                    sb.append(" dni ");
                }
            }

            if (hours > 0L) {
                sb.append(hours);
                i = hours % 10L;
                if (i == 1L) {
                    sb.append(" godzine ");
                } else if (i < 5L) {
                    sb.append(" godziny ");
                } else {
                    sb.append(" godzin ");
                }
            }

            if (minutes > 0L) {
                sb.append(minutes);
                i = minutes % 10L;
                if (i == 1L) {
                    sb.append(" minute ");
                } else if (i < 5L) {
                    sb.append(" minuty ");
                } else {
                    sb.append(" minut ");
                }
            }

            if (seconds > 0L) {
                sb.append(seconds);
                i = seconds % 10L;
                if (i == 1L) {
                    sb.append(" sekunde ");
                } else if (i < 5L) {
                    sb.append(" sekundy ");
                } else {
                    sb.append(" sekund ");
                }
            }

            return sb.toString();
        }
    }

    public static long addTime(int seconds) {
        return getTime() + (long)seconds * 1000L;
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }
}