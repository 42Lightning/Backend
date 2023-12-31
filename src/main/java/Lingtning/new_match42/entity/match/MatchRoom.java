package Lingtning.new_match42.entity.match;

import Lingtning.new_match42.enums.MatchStatus;
import Lingtning.new_match42.enums.MatchType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

// 매칭 방 테이블
@Entity
@Table(name = "match_room")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "matchList")
@EntityListeners(AuditingEntityListener.class)
public class MatchRoom {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer size;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MatchType matchType;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MatchStatus matchStatus;

    @Column
    private String firebaseMatchId;

    @OneToMany(mappedBy = "matchRoom", cascade = CascadeType.ALL)
    private List<MatchList> matchList = new ArrayList<>();

    @OneToOne(mappedBy = "matchRoom", cascade = CascadeType.ALL)
    private ChatOption chatOption;

    @OneToOne(mappedBy = "matchRoom", cascade = CascadeType.ALL)
    private SubjectOption subjectOption;

    @OneToOne(mappedBy = "matchRoom", cascade = CascadeType.ALL)
    private MealOption mealOption;

    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public MatchRoom(Integer size, MatchType matchType, MatchStatus matchStatus) {
        this.size = size;
        this.matchType = matchType;
        this.matchStatus = matchStatus;
    }
}
