import React, {useEffect, useState} from 'react';

import {Common} from '../components/Common';
import font_logo from '../assets/font_logo.png';
import kakao_logo from '../assets/kakao_logo.png';

import {
  Container,
  FontLogo,
  KakaoLogo,
  BtnText,
  ColorBtn,
  InfoWrapper,
  InfoTitle,
  NameInput,
  TitleContainer,
} from '../styles/LoginStyle';

const SignUp = () => {
  const [nickname, setNickname] = useState<string>('');
  const [nameError, setNameError] = useState<boolean>(false);

  useEffect(() => {
    if (nickname.length > 20) {
      setNameError(true);
    } else {
      setNameError(false);
    }
  }, [nickname]);

  return (
    <Container>
      <FontLogo source={font_logo} />
      <InfoWrapper>
        <TitleContainer>
          <InfoTitle>닉네임</InfoTitle>
          {nameError && (
            <BtnText textColor={Common.colors.emotionColor01}>
              20자 이하로 설정해주세요.
            </BtnText>
          )}
        </TitleContainer>
        <NameInput onChangeText={text => setNickname(text)} />
        <InfoTitle>결제 범위</InfoTitle>
      </InfoWrapper>
      <ColorBtn color={Common.colors.kakao}>
        <KakaoLogo source={kakao_logo} />
        <BtnText textColor={Common.colors.deepGrey}>결제 등록하기</BtnText>
      </ColorBtn>
      <ColorBtn color={Common.colors.subColor02}>
        <BtnText textColor={Common.colors.white01}>설정 완료</BtnText>
      </ColorBtn>
      <BtnText textColor="#000">{nickname}</BtnText>
    </Container>
  );
};

export default SignUp;
