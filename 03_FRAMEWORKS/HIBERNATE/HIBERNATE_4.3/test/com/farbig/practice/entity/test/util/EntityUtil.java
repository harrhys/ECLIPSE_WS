package com.farbig.practice.entity.test.util;

import java.util.Date;
import java.util.Random;

import com.farbig.practice.entity.BaseEntity;

public class EntityUtil {

	public static final int MILLI_SECS_PER_DAY = 24 * 60 * 60 * 1000;

	public String testCaseName = "";

	public void setEntityInfo(BaseEntity info) {
		
//		info.setCreatedBy(this.getClass().getSimpleName() + ":" + testCaseName);
//		info.setStatus("ACTIVE");
//		info.setCreatedDate(new Date());
	}

	public void setUpdatedEntityInfo(BaseEntity info) {
		
//		info.setUpdatedBy(this.getClass().getSimpleName() + ":" + testCaseName);
//		info.setUpdatedDate(new Date());
	}

	public String getVehicleNumber() {
		
		String number = "KA03ME";
		Random r = new Random();
		number += r.nextInt(10);
		number += r.nextInt(10);
		number += r.nextInt(10);
		number += r.nextInt(10);

		return number;
	}

}
