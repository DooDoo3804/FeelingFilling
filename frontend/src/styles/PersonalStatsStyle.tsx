import styled from 'styled-components/native';
import {Common} from '../components/Common';

export const Container = styled.View`
  flex: 1;
  background-color: ${Common.colors.white01};
  justify-content: flex-start;
  align-items: center;
`;

export const TitleText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-size: 21px;
  font-family: 'NotoSansKR-Bold';
`;

// 이번달 저금 섹션
export const ThisMonthSavingContainer = styled.View`
  width: 90%;
`;

export const ThisMonthSavingTitleContainer = styled.View`
  margin-top: 20px;
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
  width: 90%;
`;

export const TotalStatisticsTitleContainer = styled.View``;

export const TotalStatisticsBodyContainer = styled.View``;

// 이번 달 감정 최고조 섹션
export const ThisMonthEmotionContainer = styled.View`
  width: 90%;
`;

export const ThisMonthEmotionTitleContainer = styled.View``;

export const ThisMonthEmotionBodyContainer = styled.View``;

// 저금 누적액 섹션
export const CumulativeAmountContainer = styled.View`
  width: 90%;
`;

export const CumulativeAmountTitleContainer = styled.View``;

export const CumulativeAmountBodyContainer = styled.View``;
