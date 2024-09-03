package org.forkingaround.adventuretime.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscribeDto {
    private Long id;
    private String username;
}
