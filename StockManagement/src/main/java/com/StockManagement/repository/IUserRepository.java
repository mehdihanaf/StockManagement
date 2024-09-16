package com.StockManagement.repository;

import com.StockManagement.models.Usern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<Usern,Long> {
}
