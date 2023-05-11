import React, {useState} from 'react';

// import {useSelector, useDispatch} from 'react-redux';
// import {toggleProgress} from '../redux';
// import type {AppState} from '../redux';

import Swiper from 'react-native-swiper';

import {Common} from '../components/Common';
import {
  Container,
  Heading,
  MoneyWrapper,
  BalanceHeading,
  HideText,
  BtnWrapper,
  BalanceBtn,
  BalanceText,
  BtnText,
} from '../styles/HomeStyle';

import {
  SwiperConatiner,
  SwiperView,
  SwiperText,
  LottieContainer,
} from '../styles/LoginStyle';

const Home = ({navigation}: {navigation: any}) => {
  const [balanceView, setBalanceView] = useState(true);
  // 로딩중 화면 설정하는 함수
  // const inProgress = useSelector<AppState, boolean>(state => state.inProgress);
  // const dispatch = useDispatch();

  // const handleProgress = () => {
  //   dispatch(toggleProgress(!inProgress));
  // };

  return (
    <Container>
      <Heading>나의 감정 적금</Heading>
      <MoneyWrapper>
        <BalanceHeading>사용자님의 4월 적금</BalanceHeading>
        {balanceView ? (
          <BalanceText>181,818 원</BalanceText>
        ) : (
          <HideText>잔액 숨김 중</HideText>
        )}
        <BtnWrapper>
          <BalanceBtn onPress={() => navigation.navigate('Saving')}>
            <BtnText>적립 내역</BtnText>
          </BalanceBtn>
          <BalanceBtn onPress={() => setBalanceView(!balanceView)}>
            {balanceView ? (
              <BtnText>잔액 숨기기</BtnText>
            ) : (
              <BtnText>잔액 보이기</BtnText>
            )}
          </BalanceBtn>
        </BtnWrapper>
      </MoneyWrapper>
      <SwiperConatiner>
        <Swiper
          showsButtons={false}
          autoplay
          loop
          autoplayTimeout={3}
          activeDotColor={Common.colors.selectGrey}>
          <SwiperView>
            <LottieContainer></LottieContainer>
            <SwiperText>감정을 다스리고, 돈도 모아보세요.</SwiperText>
          </SwiperView>
          <SwiperView>
            <LottieContainer></LottieContainer>
            <SwiperText>나의 감정 점수와 통계를 확인해요.</SwiperText>
          </SwiperView>
          <SwiperView>
            <LottieContainer></LottieContainer>
            <SwiperText>
              한 달이 지나면 모은 돈을 돌려받을 수 있어요.
            </SwiperText>
          </SwiperView>
        </Swiper>
      </SwiperConatiner>
    </Container>
  );
};

export default Home;
