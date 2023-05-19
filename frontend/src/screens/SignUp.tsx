import React, {useEffect, useState} from 'react';
import {Dimensions, Alert} from 'react-native';
import axios, {AxiosResponse} from 'axios';
import {ScrollView} from 'react-native-gesture-handler';
import {RangeSlider} from '@sharcoux/slider';

import {useSelector, useDispatch} from 'react-redux';
import {tokenAction, loginAction} from '../redux';

import {Common} from '../components/Common';
import font_logo from '../assets/font_logo.png';

import {
  SignupWrapper,
  SignupContainer,
  FontLogo,
  BtnText,
  ColorBtn,
  InfoWrapper,
  InfoTitle,
  NameInput,
  SliderContainer,
  TitleContainer,
} from '../styles/LoginStyle';

const SignUp = ({navigation, route}: {navigation: any; route: any}) => {
  const [nickname, setNickname] = useState<string>('');
  const [nameError, setNameError] = useState<boolean>(false);
  const [multiSliderValue, setMultiSliderValue] = useState<number[]>([
    0, 10000,
  ]);

  const kakaoId = route.params.kakaoId.toString();
  const deviceHeight = Math.ceil(Dimensions.get('window').height);
  const dispatch = useDispatch();

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

  const handleAccessToken = (
    new_refreshtoken: string,
    new_accesstoken: string,
  ) => {
    dispatch(tokenAction(new_refreshtoken, new_accesstoken));
  };

  const handleSignup = async (): Promise<void> => {
    if (nameError) {
      Alert.alert('알림', '닉네임은 20자까지 설정 가능합니다.', [
        {text: '확인'},
      ]);
    } else if (nickname.length < 1) {
      Alert.alert('알림', '닉네임은 필수 입력 정보입니다.', [{text: '확인'}]);
    } else if (multiSliderValue[0] < 0 || multiSliderValue[1] > 10000) {
      Alert.alert('알림', '결제 범위에 오류가 발생했습니다.', [{text: '확인'}]);
    } else {
      try {
        const res: AxiosResponse = await axios.post(
          'https://feelingfilling.store/api/user',
          {
            kakaoId: kakaoId,
            nickname: nickname,
            minimum: multiSliderValue[0],
            maximum: multiSliderValue[1],
          },
        );
        const userRes: AxiosResponse = await axios.get(
          'https://feelingfilling.store/api/user',
          {
            headers: {
              Authorization: `Bearer ${res.data['access-token']}`,
            },
          },
        );
        if (userRes.data.user.billed) {
          handleLogin(
            userRes.data.user.nickname,
            userRes.data.user.userId,
            userRes.data.user.minimum,
            userRes.data.user.maximum,
            res.data['access-token'],
            res.data['refresh-token'],
          );
        } else {
          handleAccessToken(
            res.data['refresh-token'],
            res.data['access-token'],
          );
          navigation.navigate('Payment', {kakaoId: kakaoId});
        }
      } catch (err) {
        console.log(err);
      }
    }
  };

  const handleLogin = (
    name: string,
    id: number,
    min_money: number,
    max_money: number,
    access_token: string,
    refresh_token: string,
  ) => {
    dispatch(
      loginAction({
        name,
        id,
        min_money,
        max_money,
        access_token,
        refresh_token,
      }),
    );
  };

  return (
    <SignupContainer>
      <SignupWrapper height={deviceHeight}>
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

        <ColorBtn color={Common.colors.emotionColor03} onPress={handleSignup}>
          <BtnText textColor={Common.colors.white01}>
            {/* onPress={() => navigation.navigate('Payment')}> */}
            가입하기
          </BtnText>
        </ColorBtn>
      </SignupWrapper>
    </SignupContainer>
  );
};

export default SignUp;
