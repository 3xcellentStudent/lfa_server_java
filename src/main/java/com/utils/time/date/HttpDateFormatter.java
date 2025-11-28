package com.utils.time.date;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class HttpDateFormatter {
  
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

  private HttpDateFormatter(){}

  public static String formatLastModified(long timestamp){
    Instant instant = Instant.ofEpochMilli(timestamp);

    return DATE_TIME_FORMATTER.format(instant);
  }
}
