import React from 'react';

import font_logo from '../assets/font_logo.png';
import kakao_logo from '../assets/kakao_logo.png';

import {
  Container,
  FontLogo,
  KakaoLogo,
  BtnText,
  ColorBtn,
} from '../styles/LoginStyle';
import {Common} from '../components/Common';

const SignUp = () => {
  return (
    <Container>
      <FontLogo source={font_logo} />
      <ColorBtn color={Common.colors.kakao}>
        <KakaoLogo source={kakao_logo} />
        <BtnText textColor={Common.colors.deepGrey}>결제 등록하기</BtnText>
      </ColorBtn>
      <ColorBtn color={Common.colors.subColor02}>
        <BtnText textColor={Common.colors.white01}>설정 완료</BtnText>
      </ColorBtn>
    </Container>
  );
};

export default SignUp;
