package com.project.Templates.repos;

import com.project.Templates.DTOs.TextSlotDTO;
import com.project.Templates.models.TextSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextSlotsRepo extends JpaRepository<TextSlot, Long> {

    List<TextSlot> findAllByTemplateId(Long templateId);
}
