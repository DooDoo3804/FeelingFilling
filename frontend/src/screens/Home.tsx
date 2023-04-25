import React, {useState} from 'react';

// import {useSelector, useDispatch} from 'react-redux';
// import {toggleProgress} from '../redux';
// import type {AppState} from '../redux';

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
    </Container>
  );
};

export default Home;
