import React from 'react';
import {Modal} from 'react-native';
import styled from 'styled-components/native';
import {Common} from './Common';
import Lottie from 'lottie-react-native';

const OkModalContainer = styled.View`
  background-color: rgba(0, 0, 0, 0.2);
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
`;

const OkModalView = styled.View`
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

const OkHeaderText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 20px;
`;

const OkTextView = styled.View`
  width: 100%;
  height: 65px;
  background-color: #90ee90;
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

const OkModal = (props: any) => {
  const modalClose = () => {
    props.setShowOkModal(!props.showOkModal);
  };

  return (
    <Modal
      animationType={'fade'}
      transparent={true}
      visible={props.showOkModal}>
      <OkModalContainer>
        <OkModalView>
          <LottieContainer>
            <Lottie source={require('../assets/ok.json')} autoPlay loop />
          </LottieContainer>
          <OkHeaderText>{props.msg}</OkHeaderText>
          <OkTextView
            onTouchStart={() => {
              modalClose();
            }}>
            <CloseText>close</CloseText>
          </OkTextView>
        </OkModalView>
      </OkModalContainer>
    </Modal>
  );
};

export default OkModal;
