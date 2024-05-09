package com.omair.Payloads;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategDto {
    private Long id;

    @NotBlank(message = "Can't be blanked")
    @Size(min=5, message="Write minimum 5 words")
    private String description;
    @NotBlank(message = "Can't be blanked")
    private String type;
    @NotBlank(message = "Can't be blanked")
    private String title;
}
