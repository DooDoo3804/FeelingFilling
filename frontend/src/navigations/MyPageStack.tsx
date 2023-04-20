import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import EntypoIcon from 'react-native-vector-icons/Entypo';
import Mypage from '../screens/Mypage';
import UserInfo from '../screens/UserInfo';

import {Common} from '../components/Common';

type RootStackParamList = {
  Mypage: undefined;
  UserInfo: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const MypageStackNavigation = () => {
  return (
    <Stack.Navigator
      initialRouteName="Mypage"
      screenOptions={{
        headerTitleAlign: 'center',
        headerTitleStyle: {fontSize: 20, fontWeight: 'bold'},
        headerStyle: {height: 70},
        headerBackImage: () => (
          <EntypoIcon
            name="chevron-left"
            color={Common.colors.selectGrey}
            size={40}
          />
        ),
      }}>
      <Stack.Screen
        name="Mypage"
        component={Mypage}
        options={{headerShown: false}}
      />
      <Stack.Screen
        name="UserInfo"
        component={UserInfo}
        options={{title: '내 정보 수정'}}
      />
    </Stack.Navigator>
  );
};

export default MypageStackNavigation;
