package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.customAnnotation.GenderPattern;
import com.lcwd.electronic.store.customAnnotation.ImagePattern;
import com.lcwd.electronic.store.entities.Roles;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDtos {


    private String userId;
    @Size(min = 3,max=25,message = "Invalid Name")
    private String name;
   // @Email(message = "Invalid User Email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",message = "Enter a valid type email id")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    //@Size(min = 3,max=10)
    @GenderPattern
    private String gender;
    @NotBlank(message="Fill about section")
    private String about;

    @ImagePattern
    private String imageName;

    private List<RolesDto> roles;
}
