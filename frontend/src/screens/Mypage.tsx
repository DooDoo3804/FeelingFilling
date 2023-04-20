import React from 'react';
import styled from 'styled-components/native';
import EntypoIcon from 'react-native-vector-icons/Entypo';
import {Common} from '../components/Common';

import emo_happy from '../assets/emo_happy.png';

const Container = styled.View`
  flex: 1;
  background-color: ${Common.colors.white01};
  padding: 10px;
`;

const Heading = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 20px;
  margin-left: 15px;
  color: ${Common.colors.deepGrey};
`;

const ProfileContainer = styled.TouchableOpacity`
  flex-direction: row;
  background-color: ${Common.colors.subColor04};
  border-radius: 20px;
  padding: 20px;
  justify-content: space-between;
  align-items: center;
`;

const ProfileWrapper = styled.View`
  flex-direction: row;
`;

const EmotionImage = styled.Image`
  width: 60px;
  height: 60px;
`;

const Mypage = ({navigation}: {navigation: any}) => {
  // 마이페이지 보여주는 화면입니다.
  return (
    <Container>
      <Heading>마이페이지</Heading>
      <ProfileContainer onPress={() => navigation.navigate('UserInfo')}>
        <ProfileWrapper>
          <EmotionImage source={emo_happy} />
          <Heading>사용자 님</Heading>
        </ProfileWrapper>
        <ProfileWrapper>
          <EntypoIcon
            name="chevron-right"
            color={Common.colors.deepGrey}
            size={40}
          />
        </ProfileWrapper>
      </ProfileContainer>
    </Container>
  );
};

export default Mypage;
