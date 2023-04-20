import React, {useState} from 'react';
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
  const now = new Date();
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth());

  const prevMonth = () => {
    if (month === 0) {
      setYear(year - 1);
      setMonth(11);
    } else {
      setMonth(month - 1);
    }
  };

  const nextMonth = () => {
    if (year < now.getFullYear()) {
      if (month === 11) {
        setYear(year + 1);
        setMonth(0);
      } else {
        setMonth(month + 1);
      }
    } else if (year === now.getFullYear() && month < now.getMonth()) {
      if (month === 12) {
        setYear(year + 1);
        setMonth(0);
      } else {
        setMonth(month + 1);
      }
    }
  };

  return (
    <SavingWrapper>
      <BalanceWrapper>
        <MonthWrapper>
          <PrevNextBtn onPress={() => prevMonth()}>
            <EntypoIcon
              name="chevron-left"
              color={Common.colors.deepGrey}
              size={30}
            />
          </PrevNextBtn>
          <MonthText>
            {year}년 {month + 1}월
          </MonthText>
          <PrevNextBtn onPress={() => nextMonth()}>
            <EntypoIcon
              name="chevron-right"
              color={
                year === now.getFullYear() && month === now.getMonth()
                  ? Common.colors.basicGrey
                  : Common.colors.deepGrey
              }
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
