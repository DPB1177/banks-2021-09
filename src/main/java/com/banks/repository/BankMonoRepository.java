package com.banks.repository;

import com.banks.entity.BankMonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankMonoRepository extends JpaRepository<BankMonoEntity, UUID> {
}
