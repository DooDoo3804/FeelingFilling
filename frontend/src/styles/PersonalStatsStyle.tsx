import styled from 'styled-components/native';
import {Common} from '../components/Common';

export const Container = styled.ScrollView`
  flex: 1;
  background-color: ${Common.colors.white01};
`;

export const TitleText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-size: 21px;
  font-family: 'NotoSansKR-Bold';
`;

export const LottieContainer1 = styled.View`
  margin-top: 13px;
  width: 35px;
  height: 35px;
`;

export const LottieContainer2 = styled.View`
  margin-left: 7px;
  margin-top: 11px;
  width: 35px;
  height: 35px;
`;

// 이번달 저금 섹션
export const ThisMonthSavingContainer = styled.View`
  margin-left: 5%;
  margin-right: 5%;
  width: 90%;
`;

export const ThisMonthSavingTitleContainer = styled.View`
  margin-top: 20px;
  flex-direction: row;
`;

export const ThisMonthSavingBodyContainer = styled.View`
  background-color: ${Common.colors.backgroundColor01};
  border-radius: 10px;
  height: 150px;
`;

export const ThisMonthSavingEmotion1 = styled.Image`
  width: 30px;
  height: 30px;
  position: absolute;
  top: 14px;
  right: 10px;
`;

export const ThisMonthSavingEmotion2 = styled.Image`
  width: 30px;
  height: 30px;
  position: absolute;
  top: 60px;
  right: 10px;
`;

export const ThisMonthSavingEmotion3 = styled.Image`
  width: 30px;
  height: 30px;
  position: absolute;
  top: 105px;
  right: 10px;
`;

export const ThisMonthSavingPrice1 = styled.Text`
  min-width: 70px;
  height: 30px;
  position: absolute;
  top: 17px;
  line-height: 30px;
  right: 50px;
  font-family: 'NotoSansKR-Bold';
  font-size: 15px;
  color: ${Common.colors.deepGrey};
`;
export const ThisMonthSavingPrice2 = styled.Text`
  min-width: 70px;
  height: 30px;
  position: absolute;
  top: 63px;
  line-height: 30px;
  right: 50px;
  font-family: 'NotoSansKR-Bold';
  font-size: 15px;
  color: ${Common.colors.deepGrey};
`;
export const ThisMonthSavingPrice3 = styled.Text`
  min-width: 70px;
  height: 30px;
  position: absolute;
  top: 108px;
  line-height: 30px;
  right: 50px;
  font-family: 'NotoSansKR-Bold';
  font-size: 15px;
  color: ${Common.colors.deepGrey};
`;

// 월별 추이 섹션
export const TotalStatisticsContainer = styled.View`
  margin-left: 5%;
  margin-right: 5%;
  width: 90%;
`;

export const TotalStatisticsTitleContainer = styled.View`
  margin-top: 20px;
  flex-direction: row;
`;

export const TotalStatisticsBodyContainer = styled.View`
  margin-bottom: 10px;
`;

// 이번 달 감정 최고조 섹션
export const ThisMonthEmotionContainer = styled.View`
  margin-left: 5%;
  margin-right: 5%;
  width: 90%;
`;

export const ThisMonthEmotionTitleContainer = styled.View``;

export const ThisMonthEmotionBodyContainer = styled.View`
  flex-direction: row;
`;

export const BestDateContainer = styled.View`
  background-color: ${Common.colors.backgroundColor01};
  width: 56%;
  height: 150px;
  border-radius: 15px;
  margin-right: 2%;
  justify-content: center;
  align-items: center;
  flex-direction: row;
`;

export const BestDateSmileContainer = styled.View`
  margin-right: 5%;
`;

export const BestDateText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 22px;
`;

export const BestSamllContainer = styled.View`
  width: 40%;
  height: 150px;
  margin-left: 2%;
`;

export const BestHourContainer = styled.View`
  background-color: ${Common.colors.subColor03};
  height: 48%;
  margin-bottom: 2%;
  border-radius: 15px;
  flex-direction: row;
  justify-content: center;
  align-items: center;
`;

export const BestHourLottie = styled.View`
  width: 60px;
  height: 60px;
`;
export const BestHourText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 15px;
`;

export const BestDayContainer = styled.View`
  background-color: ${Common.colors.chatColor03};
  height: 48%;
  margin-top: 2%;
  border-radius: 15px;
  flex-direction: row;
  justify-content: center;
  align-items: center;
`;

export const BestDayLottie = styled.View`
  width: 45px;
  height: 45px;
  margin-right: 10px;
`;
export const BestDayText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 15px;
`;

// 저금 누적액 섹션
export const CumulativeAmountContainer = styled.View`
  margin-left: 5%;
  margin-right: 5%;
  width: 90%;
`;

export const CumulativeAmountTitleContainer = styled.View`
  flex-direction: row;
`;

export const CumulativeAmountBodyContainer = styled.View`
  background-color: ${Common.colors.chatColor02};
  width: 100%;
  border-radius: 20px;
  margin-bottom: 30px;
  justify-content: center;
  align-items: center;
  padding-top: 20px;
  padding-bottom: 20px;
`;

export const AmountHeadingContainer = styled.View`
  flex-direction: row;
  width: 85%;
  justify-content: space-between;
  align-items: flex-end;
`;

export const AmountHeadingLottieContainer = styled.View`
  width: 120px;
  height: 120px;
  background-color: ${Common.colors.white01};
  border-radius: 10px;
`;
export const AmountHeadingTextContainer = styled.View`
  align-items: flex-end;
`;

export const AmountHeadingText1 = styled.Text`
  top: 40px;
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 18px;
`;

export const AmountHeadingText2 = styled.Text`
  top: 15px;
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 23px;
`;

export const AmountCoffeeContainer = styled.View`
  flex-direction: row;
  width: 85%;
  justify-content: space-between;
  align-items: flex-end;
`;

export const AmountCoffeeLottieContainer = styled.View`
  width: 80px;
  height: 80px;
  background-color: #257b57;
  border-radius: 10px;
`;

export const AmountCoffeeTextContainer = styled.View`
  align-items: flex-end;
`;

export const AmountBurgerContainer = styled.View`
  flex-direction: row;
  width: 85%;
  justify-content: space-between;
  align-items: flex-end;
`;

export const AmountBurgerLottieContainer = styled.View`
  width: 80px;
  height: 80px;
  background-color: #efc77a;
  border-radius: 10px;
`;

export const AmountBurgerTextContainer = styled.View`
  align-items: flex-end;
`;

export const AmountText1 = styled.Text`
  top: 35px;
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 14px;
`;

export const AmountText2 = styled.Text`
  top: 10px;
  color: ${Common.colors.deepGrey};
  font-family: 'NotoSansKR-Bold';
  font-size: 23px;
`;
