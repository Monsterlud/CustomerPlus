package utilities;

import main.CustomerManager;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeConversion {



    //local time to UTC conversion
    public static LocalDateTime ldtToUTC(LocalDateTime ldt) {
        ZonedDateTime originZdt  = ldt.atZone(CustomerManager.localZone);
        ZoneId targetZone = ZoneId.of("UTC");
        ZonedDateTime zdtUTC = originZdt.withZoneSameInstant(targetZone);
        return zdtUTC.toLocalDateTime();
    }

    //UTC to local time conversion
    public static LocalDateTime utcToLDT(LocalDateTime utc) {
        ZoneId originZone = ZoneId.of("UTC");
        ZonedDateTime originZdt  = utc.atZone(originZone);
        ZonedDateTime localZdt = originZdt.withZoneSameInstant(CustomerManager.localZone);
        return localZdt.toLocalDateTime();
    }
    //local time to ET zone conversion
    public static LocalDateTime ldtToEST(LocalDateTime ldt) {
        ZonedDateTime originZdt  = ldt.atZone(CustomerManager.localZone);
        ZoneId targetZone = ZoneId.of("US/Eastern");
        ZonedDateTime zdtEST = originZdt.withZoneSameInstant(targetZone);
        return zdtEST.toLocalDateTime();
    }

}
