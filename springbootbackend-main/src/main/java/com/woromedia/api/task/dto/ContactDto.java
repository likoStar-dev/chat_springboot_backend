package com.woromedia.api.task.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ContactDto {
    private Long userId; // ID of the user to be added to contacts
    private Set<Long> contacts; // Set of contact IDs
}
