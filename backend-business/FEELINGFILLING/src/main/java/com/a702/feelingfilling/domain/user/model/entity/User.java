package com.a702.feelingfilling.domain.user.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	String userId;
	
	String nickname;
	
	String role;

	int minimum;
	
	int maximum;
	
	LocalDateTime join_date;
	
}
