package com.bedtimes.sargegmbot.callback;

import lombok.*;

import java.util.List;
// asdf
@Getter
@Setter
@NoArgsConstructor
public class CallbackData {
    private List<Object> attachments;
    private String avatar_url;
    private int created_at;
    private String group_id;
    private String id;
    private String name;
    private String sender_id;
    private String sender_type;
    private String source_guid;
    private boolean system;
    private String text;
    private String user_id;

    public CallbackData(List<Object> attachments, String avatar_url, int created_at, String group_id, String id, String name,
                        String sender_id, String sender_type, String source_guid, boolean system, String text, String user_id) {
        this.attachments = attachments;
        this.avatar_url = avatar_url;
        this.created_at = created_at;
        this.group_id = group_id;
        this.id = id;
        this.name = name;
        this.sender_id = sender_id;
        this.sender_type = sender_type;
        this.source_guid = source_guid;
        this.system = system;
        this.text = text;
        this.user_id = user_id;
    }
}

