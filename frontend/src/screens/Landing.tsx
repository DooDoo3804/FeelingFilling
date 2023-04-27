import React from 'react';

import Swiper from 'react-native-swiper';
import Lottie from 'lottie-react-native';

import {Common} from '../components/Common';
import font_logo from '../assets/font_logo.png';
import kakao_logo from '../assets/kakao_logo.png';

import {
  Container,
  FontLogo,
  SwiperConatiner,
  SwiperView,
  SwiperText,
  LottieContainer,
  LoginBtn,
  KakaoLogo,
  BtnText,
} from '../styles/LoginStyle';

const Landing = ({navigation}: {navigation: any}) => {
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
      <LoginBtn onPress={() => navigation.navigate('Login')}>
        <KakaoLogo source={kakao_logo} />
        <BtnText textColor={Common.colors.deepGrey}>카카오로 시작하기</BtnText>
      </LoginBtn>
    </Container>
  );
};

export default Landing;
