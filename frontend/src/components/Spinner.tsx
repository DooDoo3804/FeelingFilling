import React from 'react';
import {ActivityIndicator} from 'react-native';
import styled from 'styled-components/native';
import {Common} from './Common';

const SpinnerContainer = styled.View`
  position: absolute;
  z-index: 2;
  opacity: 0.3;
  width: 100%;
  height: 100%;
  justify-content: center;
  background-color: ${Common.colors.black01};
`;

const Spinner = (): JSX.Element => {
  return (
    <SpinnerContainer>
      <ActivityIndicator size={'large'} color={Common.colors.white01} />
    </SpinnerContainer>
  );
};

export default Spinner;

// 추후 상태관리로 Spinner 사용하기
