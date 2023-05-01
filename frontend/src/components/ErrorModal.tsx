import React from 'react';
import {Modal} from 'react-native';
import styled from 'styled-components/native';
import {Common} from './Common';
import Lottie from 'lottie-react-native';

const ErrorModalContainer = styled.View`
  background-color: rgba(0, 0, 0, 0.2);
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
`;

const ErrorModalView = styled.View`
  width: 300px;
  height: 300px;
  background-color: ${Common.colors.white01};
  border-radius: 15px;
  justify-content: space-between;
  align-items: center;
`;

const LottieContainer = styled.View`
  margin-top: 20px;
  width: 180px;
  height: 180px;
`;

const ErrorHeaderText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 20px;
`;

const ErrorTextView = styled.View`
  width: 100%;
  height: 65px;
  background-color: ${Common.colors.emotionColor01};
  overflow: hidden;
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
  justify-content: center;
  align-items: center;
`;

const CloseText = styled.Text`
  font-size: 15px;
  font-family: 'NotoSansKR-Medium';
  color: ${Common.colors.white01};
`;

const ErrorModal = (props: any) => {
  const modalClose = () => {
    props.setShowErrorModal(!props.showErrorModal);
  };

  return (
    <Modal
      animationType={'fade'}
      transparent={true}
      visible={props.showErrorModal}>
      <ErrorModalContainer>
        <ErrorModalView>
          <LottieContainer>
            <Lottie source={require('../assets/error.json')} autoPlay loop />
          </LottieContainer>
          <ErrorHeaderText>Error</ErrorHeaderText>
          <ErrorTextView
            onTouchStart={() => {
              modalClose();
            }}>
            <CloseText>close</CloseText>
          </ErrorTextView>
        </ErrorModalView>
      </ErrorModalContainer>
    </Modal>
  );
};

export default ErrorModal;
