package org.forkingaround.adventuretime.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantDto {
    private Long id;
    private String username;
}
