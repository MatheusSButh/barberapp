package com.buthdev.demo.dtos.response;

import java.time.LocalDateTime;

import com.buthdev.demo.model.enums.ReservedTimeStatus;

public record ReservedTimeResponseDTO(LocalDateTime date, String service, ReservedTimeStatus status, UserResponseDTO user) {

}
