import styled from 'styled-components/native';
import {Common} from '../components/Common';

export const Container = styled.View`
  flex: 1;
  background-color: ${Common.colors.white01};
  padding: 20px;
`;

export const TitleText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-size: 21px;
  font-family: 'NotoSansKR-Bold';
`;

export const ScreenScroll = styled.ScrollView`
  flex: 1;
`;

export const PageContainer = styled.View`
  flex: 1;
`;

export const EmoKingContainer = styled.View`
  background-color: ${Common.colors.chatColor02};
  border-radius: 20px;
  width: 100%;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding: 20px 20px;
`;

export const EmoKingText = styled.Text`
  color: ${Common.colors.deepGrey};
  font-size: 18px;
  font-family: 'NotoSansKR-Bold';
  line-height: 28px;
`;

export const TextWrapper = styled.View`
  padding: 10px 0px;
`;

export const EmoLottieWrapper = styled.View`
  background-color: ${Common.colors.white01};
  width: 110px;
  height: 110px;
  border-radius: 100px;
`;
