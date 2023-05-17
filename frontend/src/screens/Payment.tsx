import React, {useEffect, useState} from 'react';
import {Alert} from 'react-native';
import {WebView, WebViewNavigation} from 'react-native-webview';
import axios, {AxiosResponse} from 'axios';

import {useSelector, useDispatch} from 'react-redux';
import {loginAction} from '../redux';
import type {AppState} from '../redux';

import {useAxiosWithRefreshToken} from '../hooks/useAxioswithRfToken';

type user = {
  nickname: string;
  userId: number;
  minimum: number;
  maximum: number;
};

interface ApiResponse {
  message: string;
  url: string;
}

const Payment = ({navigation, route}: {navigation: any; route: any}) => {
  const kakaoId = route.params.kakaoId.toString();
  const [webView, setWebView] = useState<boolean>(false);

  const refreshToken = useSelector<AppState, string | undefined>(
    state => state.loggedUser?.refresh_token,
  );
  const accessToken = useSelector<AppState, string | undefined>(
    state => state.loggedUser?.access_token,
  );

  const dispatch = useDispatch();

  const {data, error} = useAxiosWithRefreshToken<ApiResponse>(
    'https://feelingfilling.store/api/user/register',
    'GET',
    null,
  );

  const handleLogin = (
    name: string,
    id: number,
    min_money: number,
    max_money: number,
    access_token: string,
    refresh_token: string,
  ) => {
    dispatch(
      loginAction({
        name,
        id,
        min_money,
        max_money,
        access_token,
        refresh_token,
      }),
    );
  };

  const getCode = async (target: WebViewNavigation) => {
    const elements = target.url.split('/').filter(element => element !== '');
    const resState = elements[elements.length - 1];

    if (resState === 'success') {
      console.log(resState, '성공');
      try {
        const userRes: AxiosResponse = await axios.get(
          'https://feelingfilling.store/api/user',
          {
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          },
        );
        console.log(userRes.data);
        handleLogin(
          userRes.data.user.nickname,
          userRes.data.user.userId,
          userRes.data.user.minimum,
          userRes.data.user.maximum,
          accessToken,
          refreshToken,
        );
      } catch (err) {
        console.log(err);
      }
    } else if (resState === 'info') {
      console.log(resState, '처리 진행중');
    } else {
      console.log(resState, '정의되지 않은 주소');
      setWebView(false);
      Alert.alert('결제 등록', '결제 등록 중 오류가 발생했습니다.', [
        {text: '확인', onPress: () => navigation.popToTop()},
      ]);
    }
  };

  useEffect(() => {
    if (data && data.message === 'SUCCESS') {
      // console.log('데이터 받아옴', data);
      setWebView(true);
    } else {
      console.log(error);
      setWebView(false);
    }
  }, [data, error]);

  return (
    <>
      {webView && data && (
        <WebView
          originWhitelist={['*']}
          source={{uri: data.url}}
          onNavigationStateChange={getCode}
        />
      )}
    </>
  );
};

export default Payment;
