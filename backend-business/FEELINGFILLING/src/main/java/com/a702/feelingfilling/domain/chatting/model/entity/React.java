//package com.a702.feelingfilling.domain.chatting.model.entity;
//
//import com.sun.istack.NotNull;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Getter
//@ToString
//@Builder
//public class React {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "react_id")
//  int reactId;
//  @OneToOne
//  @JoinColumn(name = "chatting_id")
//  @NotNull
//  Chatting chatting;
//  @NotNull
//  String content;
//  @NotNull
//  String emotion;
//  @NotNull
//  int amount;
//}
