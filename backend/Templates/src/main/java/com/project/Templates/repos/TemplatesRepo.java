package com.project.Templates.repos;

import com.project.Templates.models.Templates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplatesRepo extends JpaRepository<Templates, Long> {
    Templates save(Templates template);
}
