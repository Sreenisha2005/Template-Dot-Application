package com.project.Certificate.services;

import com.project.Certificate.models.CertificateDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ContentClass {
    public Map<String, String> getContentForCompletionCertificate(CertificateDTO certificateDTO){
        String paragraph = "has successfully completed his role as " +
                certificateDTO.getRole() + " at NeuroStack from "+
                certificateDTO.getStartDate() + " to " + certificateDTO.getEndDate() +
                ", during which he demonstrated strong technical skills and consistently delivered quality work. " +
                "His performance met our expectations and we wish him continued success in his future endeavors.";

        Map<String, String> map = new HashMap<>();
        map.put("firstLine", "This is to certify that");
        map.put("CandidateName", certificateDTO.getName());
        map.put("paragraph", paragraph);

        return map;
    }
}
