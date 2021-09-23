package com.example.cccoffeebackendfromatoz.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeUtils {
	public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
		return timestamp != null ? timestamp.toLocalDateTime() : null;
	}
}
