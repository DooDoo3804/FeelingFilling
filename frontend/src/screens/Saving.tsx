import React, {useState, useEffect} from 'react';
import {useAxiosWithRefreshToken} from '../hooks/useAxioswithRfToken';
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

const Saving = () => {
  const uri = 'https://feelingfilling.store/api/log';

  const now = new Date();
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const {data, error, refetch} = useAxiosWithRefreshToken<responseDataType>(
    `${uri}/${year}/${month + 1}`,
    'GET',
    null,
  );

  const priceConverter = (price: string) => {
    return price.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  const [savingListData, setSavingListData] = useState<savingListDataType[]>(
    [],
  );

  useEffect(() => {
    const savingList = data?.logs as savingListDataType[];
    console.log(data?.logs);
    setSavingListData(savingList);
    if (error) {
      console.log(error);
    }
  }, [data?.logs, error]);

  const prevMonth = () => {
    if (month === 0) {
      refetch(`${uri}/${year - 1}/${11}`);
      setYear(year - 1);
      setMonth(11);
    } else {
      refetch(`${uri}/${year}/${month}`);
      setMonth(month - 1);
    }
    const savingList = data?.logs as savingListDataType[];
    setSavingListData(savingList);
  };

  const nextMonth = () => {
    if (year < now.getFullYear()) {
      if (month === 11) {
        refetch(`${uri}/${year + 1}/${1}`);
        setYear(year + 1);
        setMonth(0);
      } else {
        refetch(`${uri}/${year - 1}/${month + 2}`);
        setMonth(month + 1);
      }
    } else if (year === now.getFullYear() && month < now.getMonth()) {
      if (month === 12) {
        refetch(`${uri}/${year + 1}/${1}`);
        setYear(year + 1);
        setMonth(0);
      } else {
        refetch(`${uri}/${year}/${month + 2}`);
        setMonth(month + 1);
      }
    }
    const savingList = data?.logs as savingListDataType[];
    setSavingListData(savingList);
  };

  const SavingListRander = () => {
    const result: any[] = [];
    let idx = 0;
    savingListData.forEach(e => {
      result.push(
        <SavingItemContainer key={idx++}>
          <SavingItemFront>
            <SavingDateText>{e.logTime.substring(0, 16)}</SavingDateText>
            {e.emotion === 'angry' ? (
              <EmotionPngContainer source={EmoAngry} />
            ) : e.emotion === 'happy' ? (
              <EmotionPngContainer source={EmoHappy} />
            ) : e.emotion === 'sad' ? (
              <EmotionPngContainer source={EmoSad} />
            ) : (
              <FontawesomeIcon5
                name="money-check-alt"
                color={'#1bac80'}
                size={35}
              />
            )}
          </SavingItemFront>
          <SavingItemTail>
            {e.emotion === 'deposit' ? (
              <WithdrawMoneyText>
                -{priceConverter(e.amount + '')}
              </WithdrawMoneyText>
            ) : (
              <SavingMoneyText>{priceConverter(e.amount + '')}</SavingMoneyText>
            )}
            <SavingMoneySumText>
              잔액 {priceConverter(e.total + '')}원
            </SavingMoneySumText>
          </SavingItemTail>
        </SavingItemContainer>,
      );
    });
    return result;
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
        <BalanceText>
          {savingListData && savingListData.length > 0
            ? priceConverter(savingListData[0].total + '')
            : '0'}
          원
        </BalanceText>
      </BalanceWrapper>
      <SavingListContainer>
        {savingListData ? SavingListRander() : ''}
      </SavingListContainer>
    </SavingWrapper>
  );
};

export default Saving;
