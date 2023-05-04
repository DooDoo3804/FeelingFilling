package com.a702.feelingfilling.domain.history.model.entity;

import com.a702.feelingfilling.domain.user.model.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer historyId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	

	LocalDateTime requestTime;
	
	String emotion;
	
	
	int amount;
	
	int total;
}
