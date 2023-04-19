import React from 'react';
import {Text} from 'react-native';
import styled from 'styled-components/native';
import EntypoIcon from 'react-native-vector-icons/Entypo';
import {Common} from '../components/Common';

const SavingWrapper = styled.View`
  flex: 1;
`;

const BalanceWrapper = styled.View`
  background-color: ${Common.colors.emotionColor02};
  align-items: center;
`;

const MonthWrapper = styled.View`
  flex-direction: row;
  align-items: center;
`;

const PrevNextBtn = styled.TouchableOpacity``;

const MonthText = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 16px;
  color: ${Common.colors.deepGrey};
  padding: 20px 20px;
`;

const BalanceText = styled.Text`
  font-family: 'NotoSansKR-Black';
  font-size: 28px;
  color: ${Common.colors.deepGrey};
  padding: 0px 20px;
  margin-bottom: 30px;
  line-height: 50px;
`;

const Saving = () => {
  // 적립 내역 보여주는 화면입니다.

  return (
    <SavingWrapper>
      <BalanceWrapper>
        <MonthWrapper>
          <PrevNextBtn>
            <EntypoIcon
              name="chevron-left"
              color={Common.colors.deepGrey}
              size={30}
            />
          </PrevNextBtn>
          <MonthText>2023년 4월</MonthText>
          <PrevNextBtn>
            <EntypoIcon
              name="chevron-right"
              color={Common.colors.deepGrey}
              size={30}
            />
          </PrevNextBtn>
        </MonthWrapper>
        <BalanceText>181,818원</BalanceText>
      </BalanceWrapper>
      <Text>Saving</Text>
    </SavingWrapper>
  );
};

export default Saving;
