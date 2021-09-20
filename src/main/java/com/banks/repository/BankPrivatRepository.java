package com.banks.repository;

import com.banks.entity.BankPrivatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankPrivatRepository extends JpaRepository<BankPrivatEntity, UUID> {
}
