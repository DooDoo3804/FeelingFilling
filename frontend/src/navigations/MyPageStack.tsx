import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import Mypage from '../screens/Mypage';

type RootStackParamList = {
  Mypage: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const MypageStackNavigation = () => {
  return (
    <Stack.Navigator initialRouteName="Mypage">
      <Stack.Screen
        name="Mypage"
        component={Mypage}
        options={{headerShown: false}}
      />
    </Stack.Navigator>
  );
};

export default MypageStackNavigation;
