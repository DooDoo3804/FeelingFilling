import styled from 'styled-components/native';
import {Common} from '../components/Common';

export const Container = styled.View`
  flex: 1;
  background-color: ${Common.colors.white01};
  padding: 10px;
`;

export const Heading = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 20px;
  margin-left: 15px;
  color: ${Common.colors.deepGrey};
`;

export const MoneyWrapper = styled.View`
  background-color: ${Common.colors.subColor02};
  border-radius: 20px;
  padding: 15px;
`;

export const BalanceHeading = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 20px;
  margin: 0px;
  margin-left: 15px;
  color: ${Common.colors.white01};
`;

export const BalanceText = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 30px;
  line-height: 50px;
  margin: 0px;
  margin-left: 15px;
  margin-bottom: 15px;
  color: ${Common.colors.white01};
`;

export const HideText = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 30px;
  line-height: 50px;
  margin: 0px;
  margin-left: 15px;
  margin-bottom: 15px;
  color: ${Common.colors.basicGrey};
`;

export const BtnWrapper = styled.View`
  flex-direction: row;
  justify-content: space-around;
`;

export const BalanceBtn = styled.TouchableOpacity`
  padding: 7px 40px;
  background-color: ${Common.colors.subColor01};
  border-radius: 10px;
`;

export const BtnText = styled.Text`
  font-family: 'NotoSansKR-Bold';
  color: ${Common.colors.white01};
`;

export const AdContainer = styled.View`
  width: 100%;
  justify-content: center;
  align-items: center;
  margin-top: 100px;
  padding: 70px 0px;
  overflow: hidden;
`;

export const AdImage = styled.Image`
  width: 350px;
  height: 200px;
  border-radius: 20px;
  object-fit: cover;
`;
