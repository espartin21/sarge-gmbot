package com.bedtimes.sargegmbot.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassCalendar {
    private List<Event> calendar;

    public String printCalendar() {
        StringBuilder out = new StringBuilder();

        for (Event e : calendar) {
            out.append(e.getAssignmentName()).append(" - ").append(e.getDueDate()).append("\n");
        }

        return out.toString();
    }
}
