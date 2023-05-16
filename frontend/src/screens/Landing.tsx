import React, {useState} from 'react';
import {NativeModules} from 'react-native';
import axios, {AxiosResponse, AxiosError} from 'axios';
import Swiper from 'react-native-swiper';
import Lottie from 'lottie-react-native';
import {
  KakaoLoginModuleInterface,
  KakaoOAuthToken,
} from '@react-native-seoul/kakao-login';

import {useSelector, useDispatch} from 'react-redux';
import {tokenAction, loginAction} from '../redux';

// import {useAxios} from '../hooks/useAxios';

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
  // const [userId, setUserId] = useState(null);
  // const refreshToken = useSelector<AppState, string>(
  //   state => state.loggedUser.refresh_token,
  // );
  // const accessToken = useSelector<AppState, string>(
  //   state => state.loggedUser.access_token,
  // );

  const dispatch = useDispatch();

  const handleAccessToken = (
    new_refreshtoken: string,
    new_accesstoken: string,
  ) => {
    dispatch(tokenAction(new_refreshtoken, new_accesstoken));
  };

  const handleLogin = (
    name: string,
    id: number,
    min_money: number,
    max_money: number,
    access_token: string,
    refresh_token: string,
  ) => {
    dispatch(
      loginAction({
        name,
        id,
        min_money,
        max_money,
        access_token,
        refresh_token,
      }),
    );
  };

  const signInWithKakao = async (): Promise<void> => {
    try {
      const token: KakaoOAuthToken = await NativeKakaoLogins.login();
      // console.log(token);
      const profile = await NativeKakaoLogins.getProfile();
      // console.log('User profile:', profile);
      console.log(profile.id);

      const res: AxiosResponse = await axios.post(
        'https://feelingfilling.store/api/user/kakao',
        {
          id: profile.id,
        },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        },
      );
      // console.log(res.data);
      if (res.data.newJoin) {
        navigation.navigate('SignUp', {kakaoId: profile.id});
      } else {
        handleAccessToken(res.data.refreshToken, res.data.accessToken);
        const userRes: AxiosResponse = await axios.get(
          'https://feelingfilling.store/api/user',
          {
            headers: {
              Authorization: `Bearer ${res.data.accessToken}`,
            },
          },
        );
        if (userRes.data.user.billed) {
          handleLogin(
            userRes.data.user.nickname,
            userRes.data.user.userId,
            userRes.data.user.minimum,
            userRes.data.user.maximum,
            res.data.accessToken,
            res.data.refreshToken,
          );
        } else {
          navigation.navigate('Payment', {kakaoId: profile.id});
        }
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
    </Container>
  );
};

export default Landing;
