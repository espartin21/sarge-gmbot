package com.bedtimes.sargegmbot.calendar.controller;

import com.bedtimes.sargegmbot.calendar.ClassCalendar;
import com.bedtimes.sargegmbot.calendar.Assignment;
import com.bedtimes.sargegmbot.messenger.service.MessageSenderService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassCalendarController {
    final MessageSenderService messageSenderService;
    ClassCalendar classCalendar;

    public ClassCalendarController(MessageSenderService messageSenderService) {
        this.messageSenderService = messageSenderService;
    }

    @PostMapping("/import")
    public void importClassCalendar(@RequestBody List<Assignment> assignments) {
        classCalendar = new ClassCalendar(assignments, messageSenderService);
        System.out.println(classCalendar.printCalendar());
    }

    @DeleteMapping("/clear")
    public void clearClassCalendar() {
        classCalendar = new ClassCalendar(null, messageSenderService);
    }
}
