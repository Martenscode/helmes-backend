package com.marten.helmesbackend.common.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDataDTO {

    private UUID uuid;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 100)
    @NotNull
    private List<Integer> sectorIds;

    @NotNull
    @AssertTrue
    private boolean terms;

}
