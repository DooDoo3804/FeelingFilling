import React, {useEffect} from 'react';
import {NativeModules} from 'react-native';

import {Provider, useSelector} from 'react-redux';
import {makeStore} from './src/redux';
import type {AppState} from './src/redux';

import {GestureHandlerRootView} from 'react-native-gesture-handler';
import {NavigationContainer} from '@react-navigation/native';

import AuthStackNavigation from './src/navigations/AuthStack';
import Main from './src/navigations/MainTab';
import Spinner from './src/components/Spinner';
import Config from 'react-native-config';

const store = makeStore();

const AppWrapper = () => {
  const inProgress = useSelector<AppState, boolean>(state => state.inProgress);
  const loggedIn = useSelector<AppState, boolean>(state => state.loggedIn);

  return (
    <GestureHandlerRootView style={{flex: 1}}>
      <NavigationContainer>
        {loggedIn ? <Main /> : <AuthStackNavigation />}
        {/* inProgress도 상태관리로 구분 */}
        {inProgress && <Spinner />}
      </NavigationContainer>
    </GestureHandlerRootView>
  );
};

export default function App() {
  useEffect(() => {
    if (NativeModules.KakaoDynamicHost) {
      NativeModules.KakaoDynamicHost.registerDynamicHost(Config.KAKAO_APP_KEY);
    } else {
      console.error('KakaoDynamicHost module not found');
    }
  }, []);

  return (
    <Provider store={store}>
      <AppWrapper />
    </Provider>
  );
}
