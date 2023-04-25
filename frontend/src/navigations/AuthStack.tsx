import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import Landing from '../screens/Landing';
import Login from '../screens/Login';
import SignUp from '../screens/SignUp';

type RootStackParamList = {
  Login: undefined;
  Landing: undefined;
  SignUp: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const AuthStackNavigation = () => {
  return (
    <Stack.Navigator initialRouteName="Landing">
      {/* 여기 컴포넌트를 하나씩 추가해서 사용합니다. */}
      <Stack.Screen
        name="Landing"
        component={Landing}
        options={{headerShown: false}}
      />
      <Stack.Screen
        name="Login"
        component={Login}
        options={{headerShown: false}}
      />
      <Stack.Screen
        name="SignUp"
        component={SignUp}
        options={{headerShown: false}}
      />
    </Stack.Navigator>
  );
};

export default AuthStackNavigation;
