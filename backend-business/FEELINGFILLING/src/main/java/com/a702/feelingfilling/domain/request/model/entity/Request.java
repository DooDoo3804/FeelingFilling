package com.a702.feelingfilling.domain.request.model.entity;

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
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer requestId;
	
	@ManyToOne
	User user;
	
	String content;
	
	LocalDateTime requestTime;
	
	String translation;
	
	String react;
	
	String emotion;
	
	Double intensity;
	
	int amount;
	
	boolean success;
}
