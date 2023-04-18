import 'react-native-gesture-handler';

import React from 'react';
// import {SafeAreaView} from 'react-native';
import {GestureHandlerRootView} from 'react-native-gesture-handler';
import {NavigationContainer} from '@react-navigation/native';

// import AuthStackNavigation from './src/navigations/AuthStack';
import MainTab from './src/navigations/MainTab';

// import Spinner from 'src/components/Spinner';

export default function App() {
  return (
    <GestureHandlerRootView style={{flex: 1}}>
      <NavigationContainer>
        {/* 나중에 상태관리로 AuthStack / MainTab 구분 */}
        {/* <AuthStackNavigation /> */}
        <MainTab />

        {/* inProgress도 상태관리로 구분 */}
        {/* {inProgress && <Spinner />} */}
      </NavigationContainer>
    </GestureHandlerRootView>
  );
}
