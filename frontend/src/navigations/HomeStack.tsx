import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import Home from '../screens/Home';
import Saving from '../screens/Saving';

type RootStackParamList = {
  Home: undefined;
  Saving: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const HomeStackNavigation = () => {
  return (
    <Stack.Navigator initialRouteName="Home">
      <Stack.Screen
        name="Home"
        component={Home}
        options={{headerShown: false}}
      />
      <Stack.Screen name="Saving" component={Saving} />
    </Stack.Navigator>
  );
};

export default HomeStackNavigation;
