import React from 'react';
import {Image} from 'react-native';
import styled from 'styled-components/native';
import Swiper from 'react-native-swiper';

import {Common} from '../components/Common';
import font_logo from '../assets/font_logo.png';
import kakao_logo from '../assets/kakao_logo.png';

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
  flex-direction: row;
  background-color: ${Common.colors.kakao};
  padding: 10px 30px;
  border-radius: 30px;
  align-items: center;
`;

const KakaoLogo = styled.Image`
  width: 40px;
  height: 40px;
  margin-right: 10px;
`;

const LoginText = styled.Text`
  font-family: 'NotoSansKR-Bold';
`;

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
        <KakaoLogo source={kakao_logo} />
        <LoginText>카카오로 시작하기</LoginText>
      </LoginBtn>
    </Container>
  );
};

export default Landing;
