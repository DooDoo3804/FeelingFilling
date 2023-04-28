package com.a702.feelingfilling.domain.request.model.entity;

import com.a702.feelingfilling.domain.user.model.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "request")
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
	@JoinColumn(name = "user_id")
	User user;
	
	String content;
	@Column(name = "request_time")
	LocalDateTime requestTime;
	
	String translation;
	
	String react;
	
	String emotion;
	
	Double intensity;
	
	int amount;
	
	boolean success;
}
