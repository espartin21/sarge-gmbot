package com.bedtimes.sargegmbot.utils.groupme.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PropertySource("classpath:application.properties")
@Service
public class GetMembersServiceImpl implements GetMembersService {
    @Value("${groupme.api-key}")
    private String GROUP_ME_API_KEY;

    @Value("${groupme.group-id}")
    private String GROUP_ID;

    public List<List<String>> getMembers() {
        RestTemplate restTemplate = new RestTemplate();
        String getGroupInfoUrl = "https://api.groupme.com/v3/groups/" + GROUP_ID + "?token=" + GROUP_ME_API_KEY;
        ResponseEntity<String> response = restTemplate.getForEntity(getGroupInfoUrl, String.class);

        List<List<String>> members = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode membersJson = root.path("response").path("members");

            for (JsonNode memberInfo : membersJson) {
                members.add(new ArrayList<>(Arrays.asList(memberInfo.path("nickname").asText(), memberInfo.path("user_id").asText())));
            }
        } catch (Exception e) {
            System.out.println("Failed to get group members");
        }

        return members;
    }
}
