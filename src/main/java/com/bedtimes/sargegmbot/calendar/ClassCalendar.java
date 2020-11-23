package com.bedtimes.sargegmbot.calendar;

import com.bedtimes.sargegmbot.messenger.service.MessageSenderService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Getter
@Setter
@Component
public class ClassCalendar {
    private static List<Assignment> assignments = null;

    private final MessageSenderService messageSenderService;

    public ClassCalendar(List<Assignment> a, MessageSenderService messageSenderService) {
        assignments = a;
        this.messageSenderService = messageSenderService;
    }

    @Scheduled(fixedRate = 10000, initialDelay = 60000) // Delay of 1 minute, runs every 10 seconds
    public void findUpcomingAssignments() {
        if (assignments == null) {
            return;
        }

        Calendar currDate = Calendar.getInstance();
        currDate.clear(Calendar.HOUR);
        currDate.clear(Calendar.MINUTE);
        currDate.clear(Calendar.SECOND);

        StringBuilder out = new StringBuilder("Upcoming Assignments: \\n");

        for (Assignment a : assignments) {
            String[] date = a.getDueDate().split("/");
            int month = Integer.parseInt(date[0]) - 1;
            int day = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);

            Calendar dueDate = new GregorianCalendar(year, month, day);

            if (currDate.compareTo(dueDate) < 0) {
                long daysBetween = ChronoUnit.DAYS.between(currDate.toInstant(), dueDate.toInstant());

                if (daysBetween <= 7) {
                    out.append(a.getAssignmentName()).append(" - ").append(a.getDueDate()).append("\\n");
                }
            }
        }

        String msg = out.toString();

        if (msg.length() == 23) {
            msg = "Yay! No upcoming assignments this week";
        }

        messageSenderService.sendTextMessage(msg);

//        System.out.println(msg);
    }

    public String printCalendar() {
        StringBuilder out = new StringBuilder("\n");

        for (Assignment a : assignments) {
            out.append(a.getAssignmentName()).append(" - ").append(a.getDueDate()).append("\n");
        }

        return out.toString();
    }
}
