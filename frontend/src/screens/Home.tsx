import React, {useState} from 'react';
import {Text, Button} from 'react-native';
import styled from 'styled-components/native';

import {useSelector, useDispatch} from 'react-redux';
import {toggleProgress} from '../redux';
import type {AppState} from '../redux';
import {Common} from '../components/Common';

const Container = styled.View`
  flex: 1;
  background-color: ${Common.colors.white01};
  padding: 10px;
`;

const Heading = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 20px;
  margin-left: 15px;
  color: ${Common.colors.deepGrey};
`;

const MoneyWrapper = styled.View`
  background-color: ${Common.colors.subColor02};
  border-radius: 20px;
  padding: 15px;
`;

const BalanceHeading = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 20px;
  margin: 0px;
  margin-left: 15px;
  color: ${Common.colors.white01};
`;

const BalanceText = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 30px;
  line-height: 50px;
  margin: 0px;
  margin-left: 15px;
  margin-bottom: 15px;
  color: ${Common.colors.white01};
`;

const HideText = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 30px;
  line-height: 50px;
  margin: 0px;
  margin-left: 15px;
  margin-bottom: 15px;
  color: ${Common.colors.basicGrey};
`;

const BtnWrapper = styled.View`
  flex-direction: row;
  justify-content: space-around;
`;

const BalanceBtn = styled.TouchableOpacity`
  padding: 7px 40px;
  background-color: ${Common.colors.subColor01};
  border-radius: 10px;
`;

const BtnText = styled.Text`
  font-family: 'NotoSansKR-Bold';
  color: ${Common.colors.white01};
`;

const Home = ({navigation}: {navigation: any}) => {
  const [balanceView, setBalanceView] = useState(false);
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
