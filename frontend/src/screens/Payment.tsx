import React, {useEffect, useState} from 'react';
import {Alert} from 'react-native';
import {WebView, WebViewNavigation} from 'react-native-webview';

import {useAxiosWithRefreshToken} from '../hooks/useAxioswithRfToken';

interface ApiResponse {
  message: string;
  url: string;
}

const Payment = ({navigation, route}: {navigation: any; route: any}) => {
  const kakaoId = route.params.kakaoId.toString();
  const [webView, setWebView] = useState<boolean>(false);

  const {data, error} = useAxiosWithRefreshToken<ApiResponse>(
    'https://feelingfilling.store/api/user/register',
    'GET',
    null,
  );

  const getCode = (target: WebViewNavigation) => {
    const elements = target.url.split('/');
    const resState = elements[elements.length - 1];

    if (resState === 'success') {
      console.log(resState, '성공');
    } else if (resState === 'info') {
      console.log(resState, '처리 진행중');
    } else {
      console.log(resState, '에러');
      setWebView(false);
      Alert.alert('결제 등록', '결제 등록 중 오류가 발생했습니다.', [
        {text: '확인', onPress: () => navigation.popToTop()},
      ]);
    }
  };

  useEffect(() => {
    if (data && data.message === 'SUCCESS') {
      console.log('데이터 받아옴', data);
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
