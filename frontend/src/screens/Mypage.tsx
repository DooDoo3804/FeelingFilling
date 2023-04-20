import React, {useState} from 'react';
import styled from 'styled-components/native';
import EntypoIcon from 'react-native-vector-icons/Entypo';
import {Common} from '../components/Common';

import emo_happy from '../assets/emo_happy.png';
import {TouchableOpacity} from 'react-native-gesture-handler';

const Container = styled.View`
  flex: 1;
  background-color: ${Common.colors.white01};
  padding: 12px;
`;

const Heading = styled.Text`
  font-family: 'NotoSansKR-Bold';
  font-size: 20px;
  margin: 5px 0px;
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
  elevation: 5;
`;

const ProfileWrapper = styled.View`
  flex-direction: row;
`;

const EmotionImage = styled.Image`
  width: 60px;
  height: 60px;
`;

const HeadingWrapper = styled.View`
  flex-direction: row;
  justify-content: space-between;
  margin-top: 20px;
  align-items: center;
`;

const SubHeading = styled.Text`
  font-family: 'NotoSansKR-Bold';
  margin-right: 15px;
  font-size: 15px;
  color: ${Common.colors.selectGrey};
`;

const BadgeContainer = styled.View`
  flex-direction: row;
  height: 80px;
  padding: 0px 10px;
  justify-content: space-between;
  align-items: center;
`;

const NoBadge = styled.Text`
  font-family: 'NotoSansKR-Regular';
  margin-left: 15px;
  font-size: 15px;
  color: ${Common.colors.selectGrey};
`;

const PlainText = styled.Text`
  font-family: 'NotoSansKR-Regular';
  margin-left: 15px;
  font-size: 15px;
  color: ${Common.colors.deepGrey};
`;

const MenuList = styled.View`
  margin: 0px 15px;
  margin-top: 15px;
  padding-top: 25px;
  border-top-width: 1px;
  border-top-color: ${Common.colors.basicGrey};
`;

const SingleMenu = styled.TouchableOpacity`
  padding: 5px 0px;
`;

const Mypage = ({navigation}: {navigation: any}) => {
  const [badges, setBadges] = useState();

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
      <HeadingWrapper>
        <Heading>활동 배지</Heading>
        <TouchableOpacity>
          <SubHeading>더 보기</SubHeading>
        </TouchableOpacity>
      </HeadingWrapper>
      <BadgeContainer>
        {badges ? (
          <NoBadge>뱃지얌</NoBadge>
        ) : (
          <NoBadge>활동 배지가 없습니다.</NoBadge>
        )}
      </BadgeContainer>
      <MenuList>
        <SingleMenu>
          <PlainText>환급 정보 등록하기</PlainText>
        </SingleMenu>
        <SingleMenu>
          <PlainText>로그아웃</PlainText>
        </SingleMenu>
        <SingleMenu>
          <PlainText>회원탈퇴</PlainText>
        </SingleMenu>
      </MenuList>
    </Container>
  );
};

export default Mypage;
