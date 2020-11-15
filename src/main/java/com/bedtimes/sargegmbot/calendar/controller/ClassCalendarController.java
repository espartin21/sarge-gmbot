package com.bedtimes.sargegmbot.calendar.controller;

import com.bedtimes.sargegmbot.calendar.ClassCalendar;
import com.bedtimes.sargegmbot.calendar.Event;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassCalendarController {
    @PostMapping("/import")
    public void importClassCalendar(@RequestBody List<Event> assignments) {
        ClassCalendar classCalendar = new ClassCalendar(assignments);
        System.out.println(classCalendar.printCalendar());
    }
}
