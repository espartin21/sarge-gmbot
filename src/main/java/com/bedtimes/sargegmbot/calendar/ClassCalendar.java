package com.bedtimes.sargegmbot.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

@Getter
@Setter
@Component
public class ClassCalendar {
    private static List<Assignment> assignments;

    public ClassCalendar(List<Assignment> a) {
        assignments = a;
    }

    @Scheduled(fixedRate = 10000, initialDelay = 10000) // Run this every 0.5 minutes
    public void findUpcomingAssignments() {
        Calendar currDate = Calendar.getInstance();
        currDate.clear(Calendar.HOUR_OF_DAY);
        currDate.clear(Calendar.MINUTE);
        currDate.clear(Calendar.SECOND);
        System.out.println(currDate.getTime());

        StringBuilder out = new StringBuilder("Upcoming Assignments: \n");
        System.out.println(out.toString().length());

        for (Assignment a : assignments) {
            String[] date = a.getDueDate().split("/");
            int month = Integer.parseInt(date[0]) - 1;
            int day = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);

            Calendar dueDate = new GregorianCalendar(year, month, day);
            System.out.println(dueDate.getTime());

            if (currDate.compareTo(dueDate) < 0) {
                long daysBetween = ChronoUnit.DAYS.between(currDate.toInstant(), dueDate.toInstant());

                if (daysBetween <= 7) {
                    out.append(a.getAssignmentName()).append(" - ").append(a.getDueDate()).append("\n");
                }
            }
        }

        String msg = out.toString();

        if (msg.length() == 23) {
            msg = "Yay! No upcoming assignments this week";
        }

        System.out.println(msg);
    }

    public String printCalendar() {
        StringBuilder out = new StringBuilder("\n");

        for (Assignment a : assignments) {
            out.append(a.getAssignmentName()).append(" - ").append(a.getDueDate()).append("\n");
        }

        return out.toString();
    }
}
