package lineworks.bizdev.intern.homework.jwtlibrary;

import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

	public static Date expireDateForOneMonth() {
		// 토큰 만료시간을 30일로 설정
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 30);
		return c.getTime();
	}

	public static Date expireDateForOneHour() {
		// 토큰 만료시간을 1시간으로 설정
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 1);
		return c.getTime();
	}

}
