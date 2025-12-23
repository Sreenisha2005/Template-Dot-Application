package com.project.Certificate.feign;

import com.project.Certificate.models.TextSlotDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient("templates-service")
public interface TemplatesInterface {

    @GetMapping("/textSlots/allTextSlots")
    public ResponseEntity<List<TextSlotDTO>> allTextSlots(@RequestParam Long templateId);

    @GetMapping("/templates/getTemplate/{id}")
    public ResponseEntity<Resource> getTemplate(@PathVariable Long id);

}
