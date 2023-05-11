import React from 'react';
import styled from 'styled-components/native';
import {Common} from './Common';
import MoneyPng from '../assets/chat_money_2.png';

const SaveImage = styled.Image`
  width: 30px;
  height: 30px;
`;

const SaveButton = styled.View`
  background-color: ${Common.colors.white01};
  width: 120px;
  height: 40px;
  border-radius: 10px;
  margin-right: 20px;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding-left: 20px;
  padding-right: 15px;
`;

const SaveText = styled.Text`
  font-size: 19px;
  font-weight: 800;
  line-height: 40px;
  color: ${Common.colors.selectGrey};
`;

const SaveBtn = (props: any) => {
  return (
    <SaveButton
      onTouchStart={() => {
        props.clickFunc();
      }}>
      <SaveText>저금하기</SaveText>
      <SaveImage source={MoneyPng} />
    </SaveButton>
  );
};

export default SaveBtn;
