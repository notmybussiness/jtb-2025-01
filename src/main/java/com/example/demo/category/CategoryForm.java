package com.example.demo.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class CategoryForm {
    @NotEmpty(message="게시판명은 필수항목입니다.")
    @Size(max=30)
    private String category;
}
