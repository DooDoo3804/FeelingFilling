import React, {useEffect, useState} from 'react';
import {ScrollView} from 'react-native-gesture-handler';
import {RangeSlider} from '@sharcoux/slider';

import {Common} from '../components/Common';
import kakao_logo from '../assets/kakao_logo.png';

import {
  Container,
  KakaoLogo,
  BtnText,
  ColorBtn,
  InfoWrapper,
  InfoTitle,
  NameInput,
  SliderContainer,
  TitleContainer,
} from '../styles/LoginStyle';

const UserInfo = () => {
  const [nickname, setNickname] = useState<string>('');
  const [nameError, setNameError] = useState<boolean>(false);
  const [multiSliderValue, setMultiSliderValue] = useState<number[]>([
    0, 10000,
  ]);

  useEffect(() => {
    if (nickname.length > 20) {
      setNameError(true);
    } else {
      setNameError(false);
    }
  }, [nickname]);

  const onChange = (range: [number, number]) => {
    setMultiSliderValue(range);
  };

  return (
    <Container>
      <InfoWrapper>
        <TitleContainer>
          <InfoTitle>닉네임</InfoTitle>
          {nameError && (
            <BtnText textColor={Common.colors.emotionColor01}>
              20자 이하로 설정해주세요.
            </BtnText>
          )}
        </TitleContainer>
        <ScrollView scrollEnabled={false}>
          <NameInput onChangeText={text => setNickname(text)} />
        </ScrollView>
        <InfoTitle>결제 범위</InfoTitle>
        <SliderContainer>
          <RangeSlider
            range={[multiSliderValue[0], multiSliderValue[1]]} // set the current slider's value
            minimumValue={0} // Minimum value
            maximumValue={10000} // Maximum value
            step={500} // The step for the slider (0 means that the slider will handle any decimal value within the range [min, max])
            minimumRange={0} // Minimum range between the two thumbs (defaults as "step")
            crossingAllowed={false} // If true, the user can make one thumb cross over the second thumb
            outboundColor={Common.colors.basicGrey} // The track color outside the current range value
            inboundColor={Common.colors.emotionColor03} // The track color inside the current range value
            thumbTintColor={Common.colors.emotionColor03} // The color of the slider's thumb
            enabled={true} // If false, the slider won't respond to touches anymore
            trackHeight={5} // The track's height in pixel
            thumbSize={18} // The thumb's size in pixel
            slideOnTap={true} // If true, touching the slider will update it's value. No need to slide the thumb.
            onValueChange={onChange} // Called each time the value changed. The type is (range: [number, number]) => void
            // ref={rangeSliderRef}
          />
          <TitleContainer>
            <BtnText textColor={Common.colors.deepGrey}>
              {multiSliderValue[0]}원 ~ {multiSliderValue[1]}원
            </BtnText>
          </TitleContainer>
        </SliderContainer>
      </InfoWrapper>

      <ColorBtn color={Common.colors.kakao}>
        <KakaoLogo source={kakao_logo} />
        <BtnText textColor={Common.colors.deepGrey}>결제 정보 수정</BtnText>
      </ColorBtn>
    </Container>
  );
};

export default UserInfo;
