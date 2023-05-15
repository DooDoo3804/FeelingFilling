import React, {useState, useEffect} from 'react';
import {Alert} from 'react-native';
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

interface DelResponse {
  message: string;
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

  const deleteRequest = useAxiosWithRefreshToken<DelResponse>(
    'https://feelingfilling.store/api/user',
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

  const handleDelete = () => {
    console.log('dd');
    const {delData, delError} = deleteRequest;
    console.log(delData, delError);
  };

  const deleteUser = () => {
    Alert.alert(
      '회원 탈퇴',
      '탈퇴 후 모든 정보는 복구할 수 없습니다.\n정말 탈퇴하시겠어요?',
      [
        {text: '확인', onPress: () => handleDelete()},
        {text: '취소', style: 'cancel'},
      ],
    );
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
        <SingleMenu onPress={() => deleteUser()}>
          <PlainText>회원탈퇴</PlainText>
        </SingleMenu>
      </MenuList>
    </Container>
  );
};

export default Mypage;
