import React from 'react';
import {Image} from 'react-native';
import styled from 'styled-components/native';
import Swiper from 'react-native-swiper';
import Lottie from 'lottie-react-native';

import {Common} from '../components/Common';
import font_logo from '../assets/font_logo.png';
import kakao_logo from '../assets/kakao_logo.png';
import landing01 from '../assets/landing_01.json';

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

const SwiperConatiner = styled.View`
  height: 220px;
`;

const SwiperView = styled.View`
  height: 150px;
  justify-content: center;
  text-align: center;
`;

const SwiperText = styled.Text`
  font-family: 'NotoSansKR-Medium';
  text-align: center;
`;

const LottieContainer = styled.View`
  height: 120px;
  margin-bottom: 10px;
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
      <SwiperConatiner>
        <Swiper
          showsButtons={false}
          autoplay
          loop
          autoplayTimeout={3}
          activeDotColor={Common.colors.selectGrey}>
          <SwiperView>
            <LottieContainer>
              <Lottie
                source={require('../assets/landing_01.json')}
                autoPlay
                loop
              />
            </LottieContainer>
            <SwiperText>감정을 다스리고, 돈도 모아보세요.</SwiperText>
          </SwiperView>
          <SwiperView>
            <LottieContainer>
              <Lottie
                source={require('../assets/landing_02.json')}
                autoPlay
                loop
              />
            </LottieContainer>
            <SwiperText>나의 감정 점수와 통계를 확인해요.</SwiperText>
          </SwiperView>
          <SwiperView>
            <LottieContainer>
              <Lottie
                source={require('../assets/landing_03.json')}
                autoPlay
                loop
              />
            </LottieContainer>
            <SwiperText>
              한 달이 지나면 모은 돈을 돌려받을 수 있어요.
            </SwiperText>
          </SwiperView>
        </Swiper>
      </SwiperConatiner>
      <LoginBtn>
        <KakaoLogo source={kakao_logo} />
        <LoginText>카카오로 시작하기</LoginText>
      </LoginBtn>
    </Container>
  );
};

export default Landing;
