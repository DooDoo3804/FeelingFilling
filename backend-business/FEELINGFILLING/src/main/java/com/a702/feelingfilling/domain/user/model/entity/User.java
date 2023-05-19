package com.a702.feelingfilling.domain.user.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class User {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer userId;
	String nickname;

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
