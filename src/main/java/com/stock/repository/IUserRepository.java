package com.stock.repository;

import com.stock.models.Usern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<Usern,Long> {
}
