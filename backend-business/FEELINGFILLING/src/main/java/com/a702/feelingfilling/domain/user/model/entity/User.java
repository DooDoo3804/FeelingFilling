package com.a702.feelingfilling.domain.user.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Entity(name = "user")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "user_id")
	Integer userId;
	String nickname;
	@Column(name = "id_oauth2")
	String idOAuth2;
	String role;
	@NotNull
	@ColumnDefault("0")
	int minimum;
	@NotNull
	@ColumnDefault("0")
	int maximum;
	@NotNull
	LocalDateTime join_date;

	public void updateRange(int max, int min){
		this.maximum = max;
		this.minimum = min;
	}

	public void updateJoinDate(){
		this.join_date = LocalDateTime.now();
	}
	public void updateRole(String role){
		this.role = role;
	}

}
