import styled from 'styled-components/native';
import {Common} from '../components/Common';

export const SavingWrapper = styled.View`
  flex: 1;
`;

export const BalanceWrapper = styled.View`
  background-color: ${Common.colors.emotionColor02};
  align-items: center;
`;

export const MonthWrapper = styled.View`
  flex-direction: row;
  align-items: center;
`;

export const PrevNextBtn = styled.TouchableOpacity``;

export const MonthText = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 16px;
  color: ${Common.colors.deepGrey};
  padding: 20px 20px;
`;

export const BalanceText = styled.Text`
  font-family: 'NotoSansKR-Black';
  font-size: 28px;
  color: ${Common.colors.deepGrey};
  padding: 0px 20px;
  margin-bottom: 30px;
  line-height: 50px;
`;

export const SavingListContainer = styled.ScrollView`
  background-color: #fffbf4;
  width: 100%;
`;

export const SavingItemContainer = styled.View`
  flex-direction: row;
  justify-content: space-between;
  padding: 20px;
  border-bottom-width: 1px;
  border-bottom-style: solid;
  border-bottom-color: ${Common.colors.lightGrey};
`;

export const SavingItemFront = styled.View``;
export const SavingItemTail = styled.View`
  align-items: flex-end;
  justify-content: flex-end;
`;

export const SavingDateText = styled.Text`
  color: ${Common.colors.deepGrey};
`;
export const WithdrawMoneyText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-weight: bold;
  font-size: 22px;
`;

export const SavingMoneyText = styled.Text`
  color: #5a86f8;
  font-weight: bold;
  font-size: 22px;
`;

export const SavingMoneySumText = styled.Text`
  color: ${Common.colors.selectGrey};
`;
export const EmotionPngContainer = styled.Image`
  width: 50px;
  height: 50px;
`;
