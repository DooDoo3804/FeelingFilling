import React from 'react';
import {Modal, TouchableWithoutFeedback} from 'react-native';
import styled from 'styled-components/native';
import {Common} from './Common';
import Lottie from 'lottie-react-native';

const AnalyzingModalContainer = styled.View`
  background-color: rgba(0, 0, 0, 0.2);
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
`;

const AnalyzingModalView = styled.View`
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

const AnalyzingHeaderText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 17px;
  padding-left: 20px;
  padding-right: 20px;
  padding-bottom: 30px;
  text-align: center;
`;

const AnalyzingModal = (props: any) => {
  return (
    <Modal
      animationType={'fade'}
      transparent={true}
      visible={props.showAnalyzingModal}>
      <TouchableWithoutFeedback>
        <AnalyzingModalContainer>
          <TouchableWithoutFeedback>
            <AnalyzingModalView>
              <LottieContainer>
                <Lottie
                  source={require('../assets/progress-loader.json')}
                  autoPlay
                  loop
                />
              </LottieContainer>
              <AnalyzingHeaderText>음성을 분석중입니다..</AnalyzingHeaderText>
            </AnalyzingModalView>
          </TouchableWithoutFeedback>
        </AnalyzingModalContainer>
      </TouchableWithoutFeedback>
    </Modal>
  );
};

export default AnalyzingModal;
