package com.artem.mezh.stock_analyzer.repository;

import com.artem.mezh.stock_analyzer.repository.entity.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<UserEntity, String>