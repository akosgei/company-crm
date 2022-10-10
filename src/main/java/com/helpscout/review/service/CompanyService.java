package com.helpscout.review.service;

import com.helpscout.review.entity.Company;
import com.helpscout.review.entity.Conversation;
import com.helpscout.review.entity.Thread;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CompanyService {

    /**
     * Removes any duplicate threads (i.e., those with matching payloads) per conversation within each company.
     */
    public void removeDuplicateThreads(List<Company> companies) {
        for (Company company : companies) {
            Set<Conversation> conversations = company.getConversations();
            for (Conversation c : conversations) {
                Set<Thread> threads = c.getThreads();
                Map<String, List<Thread>> stringThreadMap = new HashMap<>();
                for (Thread t : threads) {
                    stringThreadMap.computeIfAbsent(t.getPayload(), k -> new ArrayList<>());
                    stringThreadMap.get(t.getPayload()).add(t);
                }
                c.setThreads(new LinkedHashSet<>());
                for (Map.Entry<String, List<Thread>> stringThreadEntry : stringThreadMap.entrySet()) {
                    c.getThreads().add(stringThreadEntry.getValue().get(0));
                }
            }
        }
    }
}