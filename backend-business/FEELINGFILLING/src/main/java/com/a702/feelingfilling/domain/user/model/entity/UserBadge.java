package com.a702.feelingfilling.domain.user.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "user_badge")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserBadge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_badge_id")
	Integer UserBadgeId;
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	@Column(name = "badge_id")
	int badgeId;
	@Column(name = "achieved_date")
	LocalDateTime achievedDate;
	
	
}
