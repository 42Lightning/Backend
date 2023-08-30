package Lingtning.new_match42.entity;

import Lingtning.new_match42.enums.MatchOption;
import Lingtning.new_match42.enums.MatchStatus;
import Lingtning.new_match42.enums.MatchType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

// 매칭 방 테이블
@Entity
@Table(name = "match_room")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "matchList")
public class MatchRoom {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private MatchOption selectOption;

    @Column(nullable = false)
    private Integer size;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private MatchType matchType;

    @Column(nullable = false)
    private MatchStatus matchStatus;

    @OneToMany(mappedBy = "matchRoom")
    private List<MatchList> matchList;
}
