package com.cookiee.cookieeserver.event.domain;

import com.cookiee.cookieeserver.global.domain.BaseTimeEntity;
import com.cookiee.cookieeserver.global.domain.EventCategory;
import com.cookiee.cookieeserver.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;  // 이벤트 PK

    //@ApiParam(value="이벤트 내용", required=true, example="모같코를 했다.")
    @Column(nullable = true, length = 100)
    private String eventWhat; // 이벤트 내용

    //@ApiParam(value="이벤트 장소", required=true, example="합정역")
    @Column(nullable = true, length = 100)
    private String eventWhere; // 이벤트 장소

    //@ApiParam(value="함께 한 사람", required=true, example="민서, 수연, 지수")
    @Column(nullable = true, length = 100)
    private String withWho; // 함께 한 사람

    //@ApiParam(value="이벤트 내용", required=true, example="모같코를 했다.")
    @Column(nullable = false)
    private int eventYear;

    //@ApiParam(value="이벤트 달", required=true, example="1월")
    @Column(nullable = false)
    private int eventMonth;

    //@ApiParam(value="이벤트 날", required=true, example="20일")
    @Column(nullable = false)
    private int eventDate;

    @Column
    private String startTime;

    @Column
    private String endTime;

    //@ApiParam(value="이벤트 사진", required=true, example="https:블라블라,amazom.블라블라")
    @ElementCollection
    @Column(nullable = true)
    private List<String> imageUrl; //이미지 사진

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)  // 다대일 단방향 관계, user 삭제되면 이벤트도 삭제
    @JoinColumn(name = "userId")
    private User user;  // 유저 pk (FK)

    //event : category = 1 : 다 -> 수연이쪽에 매핑해주어야 함
    @JsonIgnore
    @OneToMany(mappedBy = "event")
    private List<EventCategory> eventCategories = new ArrayList<>();

    @Builder
    public Event(String eventWhat, String eventWhere, String withWho, int eventYear, int eventMonth, int eventDate,
                 String startTime, String endTime, List<String> imageUrl, User user, List<EventCategory> eventCategoryList){
        this.eventWhat = eventWhat;
        this.eventWhere = eventWhere;
        this.withWho = withWho;
        this.eventYear = eventYear;
        this.eventMonth = eventMonth;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageUrl = imageUrl;
        this.user = user;
        this.eventCategories = eventCategoryList;
    }

    public void update(String eventWhat, String eventWhere, String withWho, String startTime, String endTime, List<String> imageUrl, List<EventCategory> eventCategoryList){
        this.eventWhat = eventWhat;
        this.eventWhere = eventWhere;
        this.withWho = withWho;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageUrl = imageUrl;
        this.eventCategories = eventCategoryList;
    }

}
