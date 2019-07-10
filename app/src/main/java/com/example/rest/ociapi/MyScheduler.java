package com.example.rest.ociapi;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.example.util.Config;
import com.example.util.SchedulerConfig;

@Service
public class MyScheduler {

	private final Map<String, ScheduledFuture<?>> map = new ConcurrentHashMap<>();
	public static final String ACTION_START = "START";
	public static final String ACTION_STOP = "STOP";

	@Autowired
	private TaskScheduler taskScheduler;

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("poolScheduler");
		scheduler.setPoolSize(Integer.parseInt(Config.getValue("scheduler.poolSize")));
		return scheduler;
	}

	public void schedule(String dbID, String cron, String action) {
		ScheduledFuture<?> future = map.get(dbID);
		if (future != null) {
			future.cancel(true);
		}
		if (!"".equals(cron)) {
			future = taskScheduler.schedule(new RunnableTask(dbID, cron, action), new CronTrigger(cron));
			map.put(dbID, future);
		}
		SchedulerConfig.setValue(dbID + "-" + action, cron);
	}

	public void scheduleFromFile() {
		System.out.println("scheduling from file...");
		Properties pps = SchedulerConfig.copy();
		Enumeration<String> enums = (Enumeration<String>) pps.propertyNames();
		while (enums.hasMoreElements()) {
			String key = enums.nextElement();
			String value = pps.getProperty(key);
			System.out.println(key + "=" + value);
			String id = "";
			String action = "";
			if (key.endsWith(ACTION_START)) {
				action = ACTION_START;
				id = key.substring(0, key.length() - ACTION_START.length() - 1);
			} else if (key.endsWith(ACTION_STOP)) {
				action = ACTION_STOP;
				id = key.substring(0, key.length() - ACTION_STOP.length() - 1);
			}
			this.schedule(id, value, action);
		}
	}

}
