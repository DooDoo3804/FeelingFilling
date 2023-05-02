import React, {useState, useEffect} from 'react';
import EntypoIcon from 'react-native-vector-icons/Entypo';
import {Common} from '../components/Common';
import {
  SavingWrapper,
  PrevNextBtn,
  BalanceText,
  BalanceWrapper,
  MonthText,
  MonthWrapper,
  SavingListContainer,
  SavingItemContainer,
  SavingItemFront,
  SavingItemTail,
  SavingDateText,
  SavingMoneyText,
  WithdrawMoneyText,
  SavingMoneySumText,
  EmotionPngContainer,
} from '../styles/SavingStyle';
import FontawesomeIcon5 from 'react-native-vector-icons/FontAwesome5';
/////// png ///////
import EmoAngry from '../assets/emo_angry.png';
import EmoSad from '../assets/emo_sad.png';
import EmoHappy from '../assets/emo_happy.png';

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

  const SavingListRander = () => {
    const result = [];
    return result;
  };

  useEffect(() => {
    const data = {};
  }, []);

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
      <SavingListContainer>
        <SavingItemContainer>
          <SavingItemFront>
            <SavingDateText>2023.04.14 13:48</SavingDateText>
            <FontawesomeIcon5
              name="money-check-alt"
              color={'#1bac80'}
              size={35}
            />
          </SavingItemFront>
          <SavingItemTail>
            <WithdrawMoneyText>-1,800</WithdrawMoneyText>
            <SavingMoneySumText>잔액 180,000원</SavingMoneySumText>
          </SavingItemTail>
        </SavingItemContainer>
        <SavingItemContainer>
          <SavingItemFront>
            <SavingDateText>2023.04.14 13:48</SavingDateText>
            <EmotionPngContainer source={EmoHappy} />
          </SavingItemFront>
          <SavingItemTail>
            <SavingMoneyText>1,800</SavingMoneyText>
            <SavingMoneySumText>잔액 181,800원</SavingMoneySumText>
          </SavingItemTail>
        </SavingItemContainer>
      </SavingListContainer>
    </SavingWrapper>
  );
};

export default Saving;
