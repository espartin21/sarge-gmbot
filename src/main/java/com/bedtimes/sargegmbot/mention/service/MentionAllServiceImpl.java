package com.bedtimes.sargegmbot.mention.service;

import com.bedtimes.sargegmbot.callback.CallbackData;
import com.bedtimes.sargegmbot.utils.groupme.service.GetMembersService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MentionAllServiceImpl implements MentionAllService {
    final GetMembersService getMembersService;

    public MentionAllServiceImpl(GetMembersService getMembersService) {
        this.getMembersService = getMembersService;
    }

    public String mentionAll(CallbackData callbackData) {
        List<List<String>> members = getMembersService.getMembers();
        List<List<String>> membersToMention = members.stream().filter(memberInfo -> !memberInfo.get(0).equals(callbackData.getName())).collect(Collectors.toList());

        return createMentions(membersToMention, "ATTENTION");
    }

    private String createMentions(List<List<String>> membersToMention) {
        return createMentions(membersToMention, "");
    }

    private String createMentions(List<List<String>> membersToMention, String msgStart) {
        List<String> memberNames = membersToMention.stream().map(memberInfo -> "@" + memberInfo.get(0)).collect(Collectors.toList());
        List<String> memberIDs = membersToMention.stream().map(memberInfo -> memberInfo.get(1)).collect(Collectors.toList());

        String mentionString = String.join(" ", memberNames);
        List<List<Integer>> loci = new ArrayList<>();
        int start = msgStart.length() + 1;

        for (String name : memberNames) {
            int length = name.length();
            loci.add(new ArrayList<>(Arrays.asList(start, length)));
            // +1 for space separator
            start += length + 1;
        }

        return "[{\"loci\": " + loci + ", \"type\": \"mentions\", \"user_ids\":" + memberIDs + "}]";
    }
}
