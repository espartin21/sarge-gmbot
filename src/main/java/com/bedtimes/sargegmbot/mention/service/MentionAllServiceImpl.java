package com.bedtimes.sargegmbot.mention.service;

import com.bedtimes.sargegmbot.callback.CallbackData;
import com.bedtimes.sargegmbot.utils.groupme.service.GetMembersService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MentionAllServiceImpl implements MentionAllService {
    final GetMembersService getMembersService;

    public MentionAllServiceImpl(GetMembersService getMembersService) {
        this.getMembersService = getMembersService;
    }

    public void createMentions(CallbackData callbackData) {
        List<String> members = getMembersService.getMembers();
        List<String> membersToMention = members.stream().filter(m -> m.equals(callbackData.getName())).collect(Collectors.toList());
    }
}
