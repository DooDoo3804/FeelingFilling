import React from 'react';
import styled, {css} from 'styled-components/native';
import {Common} from './Common';
import MoneyPng from '../assets/chat_money_2.png';
import {Platform} from 'react-native';

const SaveImage = styled.Image`
  width: 30px;
  height: 30px;
`;

const SaveButton = styled.TouchableOpacity`
  background-color: ${Common.colors.white01};
  width: 120px;
  height: 40px;
  border-radius: 10px;
  margin-right: 10px;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding-left: 20px;
  padding-right: 15px;
  ${Platform.select({
    android: css`
      elevation: 1;
    `,
  })}
`;

const SaveText = styled.Text`
  font-size: 19px;
  font-weight: 800;
  line-height: 40px;
  color: ${Common.colors.deepGrey};
`;

const SaveBtn = (props: any) => {
  return (
    <SaveButton
      onPress={() => {
        props.clickFunc();
      }}>
      <SaveText>적금하기</SaveText>
      <SaveImage source={MoneyPng} />
    </SaveButton>
  );
};

export default SaveBtn;
