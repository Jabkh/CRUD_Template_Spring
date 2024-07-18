package org.example.crud_template.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {
    private Long id;
    private String name;

}