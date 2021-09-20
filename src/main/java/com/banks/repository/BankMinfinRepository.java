package com.banks.repository;

import com.banks.entity.BankMinfinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankMinfinRepository extends JpaRepository<BankMinfinEntity, UUID> {
}
