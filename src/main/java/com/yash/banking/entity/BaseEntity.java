package com.yash.banking.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEntity {
    UUID id;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
