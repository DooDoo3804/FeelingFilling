import React, {useMemo} from 'react';
import {TouchableOpacity} from 'react-native';
import {
  ParamListBase,
  RouteProp,
  getFocusedRouteNameFromRoute,
  useNavigation,
} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import EntypoIcon from 'react-native-vector-icons/Entypo';
import FontawesomeIcon from 'react-native-vector-icons/FontAwesome';

import styled from 'styled-components/native';

import HomeStackNavigation from './HomeStack';
import PersonalStatsStackNavigation from './PersonalStatsStack';
import OverallStatsStackNavigation from './OverallStatsStack';
import MypageStackNavigation from './MyPageStack';

import Chat from '../screens/Chat';
import {Common} from '../components/Common';

import SaveBtn from '../components/SaveBtn';
import {clickSave} from '../screens/Chat';

const Stack = createStackNavigator();

const Tab = createBottomTabNavigator();

const CenterBtn = styled.View`
  width: 80px;
  height: 80px;
  margin: 0px 10px;
  margin-top: -40px;
  background-color: ${Common.colors.emotionColor01};
  border-radius: 70px;
  border: 5px solid ${Common.colors.white01};
  justify-content: center;
  align-items: center;
`;

function TabBarButton() {
  const navigation = useNavigation();

  const handlePress = () => {
    navigation.navigate('Chat');
  };

  return (
    <TouchableOpacity onPress={handlePress}>
      <CenterBtn>
        <EntypoIcon name="message" color={Common.colors.white01} size={30} />
      </CenterBtn>
    </TouchableOpacity>
  );
}

const MainTab = (): JSX.Element => {
  const hideOnScreens = useMemo(() => ['Saving', 'UserInfo'], []);

  const getTabBarVisibility = (
    route: RouteProp<ParamListBase, 'HomeStack'>,
  ) => {
    const routeName = getFocusedRouteNameFromRoute(route || 'None');

    if (hideOnScreens.includes(routeName || 'None')) {
      return 'none';
    } else {
      return 'flex';
    }
  };

  return (
    <Tab.Navigator
      screenOptions={{
        tabBarActiveTintColor: Common.colors.selectGrey,
        tabBarInactiveTintColor: Common.colors.basicGrey,
        tabBarShowLabel: false,
      }}>
      <Tab.Screen
        name="HomeStack"
        component={HomeStackNavigation}
        options={({route}) => ({
          tabBarStyle: {display: getTabBarVisibility(route)},
          title: 'Home',
          headerShown: false,
          tabBarIcon: ({color, size}) => (
            <EntypoIcon name="home" color={color} size={size} />
          ),
        })}
      />
      <Tab.Screen
        name="PersonalStack"
        component={PersonalStatsStackNavigation}
        options={{
          title: 'Personal',
          headerShown: false,
          tabBarIcon: ({color, size}) => (
            <EntypoIcon name="wallet" color={color} size={size} />
          ),
        }}
      />
      <Tab.Screen
        name="ChatBtn"
        component={TabBarButton}
        options={{
          tabBarButton: () => <TabBarButton />,
        }}
      />
      <Tab.Screen
        name="OverallStack"
        component={OverallStatsStackNavigation}
        options={{
          title: 'Overall',
          headerShown: false,
          tabBarIcon: ({color, size}) => (
            <EntypoIcon name="bar-graph" color={color} size={size} />
          ),
        }}
      />
      <Tab.Screen
        name="MypageStack"
        component={MypageStackNavigation}
        options={{
          title: 'Mypage',
          headerShown: false,
          tabBarIcon: ({color, size}) => (
            <FontawesomeIcon name="user-circle" color={color} size={size} />
          ),
        }}
      />
    </Tab.Navigator>
  );
};
const Main = (): JSX.Element => {
  return (
    <Stack.Navigator initialRouteName="Home">
      <Stack.Screen
        name="MainTab"
        component={MainTab}
        options={{headerShown: false}}
      />
      <Stack.Screen
        name="Chat"
        component={Chat}
        options={{
          title: '',
          headerStyle: {
            backgroundColor: 'rgba(249, 246, 242, 0.8)',
            height: 60,
          },
          headerRight: () => <SaveBtn clickFunc={clickSave} />,
        }}
      />
    </Stack.Navigator>
  );
};

export default Main;
