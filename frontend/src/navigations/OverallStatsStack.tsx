import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import OverallStats from '../screens/OverallStats';

type RootStackParamList = {
  OverallStats: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const OverallStatsStackNavigation = () => {
  return (
    <Stack.Navigator initialRouteName="OverallStats">
      <Stack.Screen
        name="OverallStats"
        component={OverallStats}
        options={{headerShown: false}}
      />
    </Stack.Navigator>
  );
};

export default OverallStatsStackNavigation;
