import {useState, useEffect, useCallback} from 'react';
import axios, {AxiosResponse, AxiosError} from 'axios';
import {useSelector, useDispatch} from 'react-redux';
import {toggleProgress, tokenAction} from '../redux';
import type {AppState} from '../redux';

type FetchData<T> = {
  data: T | null;
  error: AxiosError | null;
  refetch: (newUrl?: string) => void;
};

export const useAxiosWithRefreshToken = <T>(
  initialUrl: string,
  method: 'GET' | 'POST' | 'PUT' | 'DELETE' = 'GET',
  request_config: JSON | null = null,
): FetchData<T> => {
  const [url, setUrl] = useState<string>(initialUrl);
  const [data, setData] = useState<T | null>(null);
  const [error, setError] = useState<AxiosError | null>(null);
  const refreshToken = useSelector<AppState, string>(
    state => state.loggedUser.refresh_token,
  );
  const accessToken = useSelector<AppState, string>(
    state => state.loggedUser.access_token,
  );

  const BACKEND_URL = ''; // config로 빼든지 하기

  const dispatch = useDispatch();

  const handleProgress = (progress: boolean) => {
    dispatch(toggleProgress(progress));
  };

  const handleAccessToken = (
    new_refreshtoken: string,
    new_accesstoken: string,
  ) => {
    dispatch(tokenAction(new_refreshtoken, new_accesstoken));
  };

  const fetchData = useCallback(async () => {
    handleProgress(true);
    try {
      const body = {...request_config};
      // console.log('body', body);
      const res: AxiosResponse<T> = await axios.request<T>({
        url,
        method,
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        data: body,
      });
      setData(res.data);
    } catch (err: any) {
      if (err.response?.status === 401) {
        try {
          const refreshConfig = {
            data: {refresh_token: refreshToken},
          };
          const refreshRes: AxiosResponse<{
            refresh_token: string;
            access_token: string;
          }> = await axios.get<{refresh_token: string; access_token: string}>(
            `${BACKEND_URL}/token`,
            refreshConfig,
          );
          const newConfig = {
            headers: {
              Authorization: `Bearer ${refreshRes.data.access_token}`,
            },
            body: request_config,
          };
          const newRes: AxiosResponse<T> = await axios.request<T>({
            url,
            method,
            ...newConfig,
          });
          handleAccessToken(
            refreshRes.data.refresh_token,
            refreshRes.data.access_token,
          );
          setData(newRes.data);
        } catch (refreshErr: any) {
          setError(refreshErr);
          // 로그인 구현되면 로그아웃 시키기
        }
      } else {
        setError(err);
      }
    }
    handleProgress(false);
  }, [url, accessToken, refreshToken]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const refetch = (newUrl?: string) => {
    if (newUrl) {
      setUrl(newUrl);
    } else {
      fetchData();
    }
  };

  return {data, error, refetch};
};

// 다음과 같이 import하여 사용
// import { useAxioswithRfToken } from './useAxioswithRfToken';
// const { data, error } = useFetch(url, method, null);
// redux에서 loggedIn 가져와서 로그인 상태인지 먼저 확인하고 사용해주세요.
