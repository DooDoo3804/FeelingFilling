import React, {useState} from 'react';
import Swiper from 'react-native-swiper';
import {useSelector} from 'react-redux';
import type {AppState, User} from '../redux';

import {useAxios} from '../hooks/useAxios';

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

interface responseDataType {
  message: string;
  logs: savingListDataType[];
}

interface savingListDataType {
  logTime: string;
  emotion: string;
  amount: number;
  total: number;
}

const Home = ({navigation}: {navigation: any}) => {
  const [balanceView, setBalanceView] = useState(true);
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth());

  const user = useSelector<AppState, User | null>(state => state.loggedUser);

  const {data, refetch} = useAxios<responseDataType>(
    `http://3.38.191.128:8080/api/log/${user?.id}/${year}/${month}`,
    'GET',
    null,
  );
  // 로딩중 화면 설정하는 함수
  // const inProgress = useSelector<AppState, boolean>(state => state.inProgress);
  // const dispatch = useDispatch();

  // const handleProgress = () => {
  //   dispatch(toggleProgress(!inProgress));
  // };

  const priceConverter = (price: string) => {
    return price.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  return (
    <Container>
      <Heading>나의 감정 적금</Heading>
      <MoneyWrapper>
        <BalanceHeading>
          {user && user.name}님의 {month + 1}월 적금
        </BalanceHeading>
        {balanceView ? (
          <BalanceText>
            {data && data.logs.length > 0
              ? priceConverter(data.logs[0].total + '')
              : '0'}
            원
          </BalanceText>
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
