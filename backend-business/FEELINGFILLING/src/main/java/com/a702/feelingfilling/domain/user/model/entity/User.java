package com.a702.feelingfilling.domain.user.model.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer userId;
	
	String nickname;

	@Column(name = "id_oauth2")
	String idOAuth2;
	String role;

	int minimum;
	
	int maximum;
	
	LocalDateTime join_date;
	
}
