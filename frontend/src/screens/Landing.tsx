import React from 'react';
import {View, Text} from 'react-native';
import styled from 'styled-components/native';
import Swiper from 'react-native-swiper';

import {Common} from '../components/Common';
import font_logo from '../assets/font_logo.png';

const Container = styled.View`
  flex: 1;
  flex-direction: column;
  background-color: ${Common.colors.white01};
  justify-content: space-evenly;
  align-items: center;
  padding: 10px;
`;

const FontLogo = styled.Image`
  width: 200px;
  height: 200px;
`;

const StyledSwiper = styled(Swiper)``;

const SwiperView = styled.View`
  justify-content: center;
  text-align: center;
`;

const SwiperText = styled.Text`
  text-align: center;
`;

const LoginBtn = styled.TouchableOpacity`
  background-color: ${Common.colors.emotionColor03};
  padding: 20px 40px;
  border-radius: 30px;
`;

const LoginText = styled.Text``;

const Landing = () => {
  return (
    <Container>
      <FontLogo source={font_logo} />
      <StyledSwiper showsButtons={false}>
        <SwiperView>
          <SwiperText>Hello Swiper</SwiperText>
        </SwiperView>
        <SwiperView>
          <SwiperText>Beautiful</SwiperText>
        </SwiperView>
        <SwiperView>
          <SwiperText>And simple</SwiperText>
        </SwiperView>
      </StyledSwiper>
      <LoginBtn>
        <LoginText>카카오톡으로 시작하기</LoginText>
      </LoginBtn>
    </Container>
  );
};

export default Landing;
