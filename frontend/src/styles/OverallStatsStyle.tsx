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
