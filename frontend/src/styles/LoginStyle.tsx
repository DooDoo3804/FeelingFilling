import styled from 'styled-components/native';
import {TouchableWithoutFeedback} from 'react-native-gesture-handler';
import {Common} from '../components/Common';

export const Container = styled.View`
  width: 100%;
  flex: 1;
  flex-direction: column;
  background-color: ${Common.colors.white01};
  justify-content: space-evenly;
  align-items: center;
  padding: 10px;
`;

export const SignupWrapper = styled.View<{height: number}>`
  width: 100%;
  min-height: ${(props: any) => props.height};
  flex-direction: column;
  background-color: ${Common.colors.white01};
  justify-content: space-evenly;
  align-items: center;
  padding: 10px;
`;

export const SignupContainer = styled.ScrollView`
  background-color: ${Common.colors.white01};
  width: 100%;
`;

export const KeyboardContainer = styled(TouchableWithoutFeedback)`
  flex: 1;
  width: 100%;
  height: 100%;
`;

export const FontLogo = styled.Image`
  width: 200px;
  height: 200px;
`;

export const SwiperConatiner = styled.View`
  height: 220px;
`;

export const SwiperView = styled.View`
  height: 150px;
  justify-content: center;
  text-align: center;
`;

export const SwiperText = styled.Text`
  font-family: 'NotoSansKR-Medium';
  text-align: center;
`;

export const LottieContainer = styled.View`
  height: 120px;
  margin-bottom: 10px;
`;

export const LoginBtn = styled.TouchableOpacity`
  flex-direction: row;
  background-color: ${Common.colors.kakao};
  padding: 10px 30px;
  border-radius: 30px;
  align-items: center;
`;

export const ColorBtn = styled.TouchableOpacity<{color: string}>`
  flex-direction: row;
  width: 200px;
  background-color: ${(props: any) => props.color};
  padding: 10px 30px;
  border-radius: 30px;
  align-items: center;
  justify-content: center;
`;

export const KakaoLogo = styled.Image`
  width: 40px;
  height: 40px;
  margin-right: 10px;
`;

export const BtnText = styled.Text<{textColor: string}>`
  font-family: 'NotoSansKR-Bold';
  color: ${(props: any) => props.textColor};
`;

export const InfoWrapper = styled.View`
  width: 100%;
  padding: 0px 30px;
`;

export const InfoTitle = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 16px;
  color: ${Common.colors.deepGrey};
`;

export const TitleContainer = styled.View`
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

export const NameInput = styled.TextInput`
  padding: 10px 20px;
  border-radius: 20px;
  background-color: ${Common.colors.backgroundColor01};
  margin-bottom: 20px;
`;

export const SliderContainer = styled.View`
  width: 100%;
  justify-content: center;
  align-items: center;
`;
