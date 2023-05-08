import React, {useState} from 'react';
import {NativeModules, Button} from 'react-native';
import axios, {AxiosResponse, AxiosError} from 'axios';
import Swiper from 'react-native-swiper';
import Lottie from 'lottie-react-native';
import {
  KakaoLoginModuleInterface,
  KakaoOAuthToken,
} from '@react-native-seoul/kakao-login';

import {useAxios} from '../hooks/useAxios';

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

type FetchData<T> = {
  data: T | null;
  error: AxiosError | null;
};

const {RNKakaoLogins} = NativeModules;

const NativeKakaoLogins: KakaoLoginModuleInterface = {
  login() {
    return RNKakaoLogins.login();
  },
  loginWithKakaoAccount() {
    return RNKakaoLogins.loginWithKakaoAccount();
  },
  logout() {
    return RNKakaoLogins.logout();
  },
  unlink() {
    return RNKakaoLogins.unlink();
  },
  getProfile() {
    return RNKakaoLogins.getProfile();
  },
  getAccessToken() {
    return RNKakaoLogins.getAccessToken();
  },
};

const KakaoLoginButton = ({navigation}: {navigation: any}) => {
  // const [data, setData] = useState(null);
  const [userId, setUserId] = useState(null);

  const signInWithKakao = async (): Promise<void> => {
    try {
      const token: KakaoOAuthToken = await NativeKakaoLogins.login();
      console.log(token);
      // setData(token);
      const profile = await NativeKakaoLogins.getProfile();
      // console.log('User profile:', profile);
      setUserId(profile.id);

      const res: AxiosResponse<T> = await axios.post(
        'http://k8a702.p.ssafy.io:8080/api/user/kakao',
        {
          headers: {
            'Content-Type': 'application/json',
          },
          body: {
            kakaoId: profile.id,
          },
        },
      );
      if (res.data.newJoin) {
        navigation.navigate('SignUp');
      }
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <LoginBtn onPress={signInWithKakao}>
      <KakaoLogo source={kakao_logo} />
      <BtnText textColor={Common.colors.deepGrey}>카카오로 시작하기</BtnText>
    </LoginBtn>
  );
};

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
      <KakaoLoginButton navigation={navigation} />
      <Button title="move" onPress={() => navigation.navigate('SignUp')} />
    </Container>
  );
};

export default Landing;
