import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import PersonalStats from '../screens/PersonalStats';

type RootStackParamList = {
  PersonalStats: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const PersonalStatsStackNavigation = () => {
  return (
    <Stack.Navigator initialRouteName="PersonalStats">
      <Stack.Screen
        name="PersonalStats"
        component={PersonalStats}
        options={{headerShown: false}}
      />
    </Stack.Navigator>
  );
};

export default PersonalStatsStackNavigation;
