import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import EntypoIcon from 'react-native-vector-icons/Entypo';
import Home from '../screens/Home';
import Saving from '../screens/Saving';
import {Common} from '../components/Common';

type RootStackParamList = {
  Home: undefined;
  Saving: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const HomeStackNavigation = () => {
  return (
    <Stack.Navigator
      initialRouteName="Home"
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
        name="Home"
        component={Home}
        options={{headerShown: false}}
      />
      <Stack.Screen
        name="Saving"
        component={Saving}
        options={{title: '내역 조회'}}
      />
    </Stack.Navigator>
  );
};

export default HomeStackNavigation;
