package com.cookiee.cookieeserver.category.domain;

import com.cookiee.cookieeserver.global.domain.BaseTimeEntity;
import com.cookiee.cookieeserver.global.domain.EventCategory;
import com.cookiee.cookieeserver.user.domain.User;
import com.cookiee.cookieeserver.category.dto.response.CategoryGetResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseTimeEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto_increment
    private Long categoryId;  // 카테고리 아이디

    @Column(nullable = false, length = 10)  // 최대 10자
    private String categoryName;  // 카테고리명

    @Column(nullable = false, length = 10)  // 최대 10자
    private String categoryColor;  // 카테고리 색 (HEX 코드)

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<EventCategory> eventCategories = new ArrayList<>();

    @Builder
    public Category(User user, String categoryName, String categoryColor){
        this.user = user;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

    public void update(String categoryName, String categoryColor){
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

    public CategoryGetResponseDto toDto(Category category){
        return CategoryGetResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryColor(category.getCategoryColor())
                .build();
    }
}
