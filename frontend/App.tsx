import React from 'react';

import {Provider, useSelector} from 'react-redux';
import {makeStore} from './src/redux';
import type {AppState} from './src/redux';

import {GestureHandlerRootView} from 'react-native-gesture-handler';
import {NavigationContainer} from '@react-navigation/native';

// import AuthStackNavigation from './src/navigations/AuthStack';
import Main from './src/navigations/MainTab';
import Spinner from './src/components/Spinner';

const store = makeStore();

const AppWrapper = () => {
  const inProgress = useSelector<AppState, boolean>(state => state.inProgress);

  return (
    <GestureHandlerRootView style={{flex: 1}}>
      <NavigationContainer>
        {/* 나중에 상태관리로 AuthStack / MainTab 구분 */}
        {/* <AuthStackNavigation /> */}
        <Main />
        {/* inProgress도 상태관리로 구분 */}
        {inProgress && <Spinner />}
      </NavigationContainer>
    </GestureHandlerRootView>
  );
};

export default function App() {
  return (
    <Provider store={store}>
      <AppWrapper />
    </Provider>
  );
}
