package com.a702.feelingfilling.domain.user.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserBadge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	Integer UserBadgeId;
	@ManyToOne
	@JoinColumn(name = "userId")
	User user;

	int badgeId;

	LocalDateTime achievedDate;
	
	
}
