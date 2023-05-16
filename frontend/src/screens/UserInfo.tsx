import React, {useEffect, useState} from 'react';
import {Dimensions, Alert} from 'react-native';
import {ScrollView} from 'react-native-gesture-handler';
import {RangeSlider} from '@sharcoux/slider';

import {useSelector, useDispatch} from 'react-redux';
import {loginAction} from '../redux';
import type {AppState, User} from '../redux';

import {useAxiosWithRefreshToken} from '../hooks/useAxioswithRfToken';

import {Common} from '../components/Common';

import {
  SignupContainer,
  SignupWrapper,
  BtnText,
  ColorBtn,
  InfoWrapper,
  InfoTitle,
  NameInput,
  SliderContainer,
  TitleContainer,
} from '../styles/LoginStyle';

type userType = {
  maximum: number;
  minimum: number;
  nickname: string;
  userId: number;
};

interface ApiResponse {
  message: string;
  user: userType;
}

const UserInfo = () => {
  const user = useSelector<AppState, User | null>(state => state.loggedUser);
  const [nickname, setNickname] = useState<string>(user?.name);
  const [nameError, setNameError] = useState<boolean>(false);
  const [multiSliderValue, setMultiSliderValue] = useState<number[]>([
    user?.min_money,
    user?.max_money,
  ]);

  const dispatch = useDispatch();
  const deviceHeight = Math.ceil(Dimensions.get('window').height);

  const axioswithRftoken = useAxiosWithRefreshToken<ApiResponse>(
    'https://feelingfilling.store/api/user',
    'PUT',
    {
      nickname: nickname,
      minimum: multiSliderValue[0],
      maximum: multiSliderValue[1],
    },
  );

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

  const onClick = async () => {
    console.log(multiSliderValue);

    const {data, error} = axioswithRftoken;

    if (data && data.message === 'SUCCESS' && data.user) {
      console.log(data.user);
      handleLogin(
        data.user.nickname,
        data.user.userId,
        data.user.minimum,
        data.user.maximum,
        user?.access_token,
        user?.refresh_token,
      );
      Alert.alert('알림', '회원정보가 수정되었습니다.', [{text: '확인'}]);
    } else {
      console.log(error);
      Alert.alert('알림', '회원정보 수정에 실패했습니다.', [{text: '확인'}]);
    }
  };

  return (
    <SignupContainer>
      <SignupWrapper height={deviceHeight}>
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
            <NameInput
              onChangeText={text => setNickname(text)}
              value={nickname}
            />
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
        <ColorBtn
          color={Common.colors.emotionColor03}
          onPress={() => onClick()}>
          <BtnText textColor={Common.colors.white01}>저장하기</BtnText>
        </ColorBtn>
      </SignupWrapper>
    </SignupContainer>
  );
};

export default UserInfo;
