package com.cookiee.cookieeserver.dto.request;

import com.cookiee.cookieeserver.domain.Category;
import com.cookiee.cookieeserver.domain.Event;
import com.cookiee.cookieeserver.domain.EventCategory;
import com.cookiee.cookieeserver.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record EventRegisterRequestDto (
    Long eventId,
    String eventWhat,
    String eventWhere,
    String withWho,
    int eventYear,
    int eventMonth,
    int eventDate,
    List<String> imageUrl,
    List<Long> categoryIds){

    public Event toEntity(User user, List<EventCategory> categoryIds, List<String> imageUrls){
        return Event.builder()
                .eventWhat(eventWhat)
                .eventWhere(eventWhere)
                .withWho(withWho)
                .eventYear(eventYear)
                .eventMonth(eventMonth)
                .eventDate(eventDate)
                .user(user)
                .eventCategories(categoryIds)
                .imageUrl(imageUrls)
                .build();
    }


}