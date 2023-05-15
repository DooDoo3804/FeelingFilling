import React, {useState, useEffect} from 'react';
import {TouchableOpacity} from 'react-native-gesture-handler';
import EntypoIcon from 'react-native-vector-icons/Entypo';

import {useSelector, useDispatch} from 'react-redux';
import {logoutAction} from '../redux';
import type {AppState, User} from '../redux';

import {useAxiosWithRefreshToken} from '../hooks/useAxioswithRfToken';

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
  SingleBadgeWrapper,
  NoBadge,
  MenuList,
  SingleMenu,
  PlainText,
} from '../styles/MypageStyle';

import {badgeList} from './Badges';

interface ApiResponse {
  message: string;
  badges: number[];
}

const Mypage = ({navigation}: {navigation: any}) => {
  const dispatch = useDispatch();
  const [badges, setBadges] = useState<number[]>([]);

  const user = useSelector<AppState, User | null>(state => state.loggedUser);

  const {data, error} = useAxiosWithRefreshToken<ApiResponse>(
    'https://feelingfilling.store/api/user/badge',
    'GET',
    null,
  );

  useEffect(() => {
    if (data) {
      setBadges(data.badges);
    } else {
      console.log('error : ', error);
    }
  }, [data, error]);

  const handleLogout = () => {
    dispatch(logoutAction());
  };

  const renderBadges = () => {
    const result = [];
    let len = 3;
    if (len > badges.length) {
      len = badges.length;
    }
    for (let i = 0; i < len; i++) {
      result.push(
        <SingleBadgeWrapper>
          <EmotionImage source={badgeList[i].src} />
        </SingleBadgeWrapper>,
      );
    }

    return result;
  };

  return (
    <Container>
      <Heading>마이페이지</Heading>
      <ProfileContainer onPress={() => navigation.navigate('UserInfo')}>
        <ProfileWrapper>
          <EmotionImage source={emo_happy} />
          <Heading>{user && user.name} 님</Heading>
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
        {badges && badges.length ? (
          renderBadges()
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
