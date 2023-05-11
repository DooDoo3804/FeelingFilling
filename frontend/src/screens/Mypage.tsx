import React, {useState} from 'react';
import {TouchableOpacity} from 'react-native-gesture-handler';
import EntypoIcon from 'react-native-vector-icons/Entypo';

import {useDispatch} from 'react-redux';
import {logoutAction} from '../redux';

import emo_happy from '../assets/emo_happy.png';
import {Common} from '../components/Common';

import {
  Container,
  Heading,
  ProfileContainer,
  ProfileWrapper,
  EmotionImage,
  HeadingWrapper,
  SubHeading,
  BadgeContainer,
  NoBadge,
  MenuList,
  SingleMenu,
  PlainText,
} from '../styles/MypageStyle';

const Mypage = ({navigation}: {navigation: any}) => {
  const dispatch = useDispatch();
  const [badges, setBadges] = useState();

  const handleLogout = () => {
    dispatch(logoutAction());
  };

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
        <TouchableOpacity onPress={() => navigation.navigate('Badges')}>
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
        <SingleMenu onPress={() => handleLogout()}>
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
