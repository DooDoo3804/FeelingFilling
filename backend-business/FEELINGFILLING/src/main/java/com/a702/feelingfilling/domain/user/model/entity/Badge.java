package com.a702.feelingfilling.domain.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Badge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer UserBadgeId;
	@ManyToOne
	User user;
	
	int badgeId;
	
	LocalDateTime achievedDate;
	
	
}
