package com.bedtimes.sargegmbot.mention.service;

import com.bedtimes.sargegmbot.utils.groupme.service.GetMembersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentionAllServiceImpl implements MentionAllService {
    final GetMembersService getMembersService;

    public MentionAllServiceImpl(GetMembersService getMembersService) {
        this.getMembersService = getMembersService;
    }

    public void createMentions() {
        List<String> members = getMembersService.getMembers();

    }
}
