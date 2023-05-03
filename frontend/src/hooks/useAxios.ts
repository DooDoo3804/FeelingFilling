import {useState, useEffect, useCallback} from 'react';
import axios, {AxiosResponse, AxiosError} from 'axios';
import {useDispatch} from 'react-redux';
import {toggleProgress} from '../redux';

type FetchData<T> = {
  data: T | null;
  error: AxiosError | null;
  refetch: (newUrl?: string) => void;
};

export const useAxios = <T>(
  initialUrl: string,
  method: 'GET' | 'POST' | 'PUT' | 'DELETE' = 'GET',
  request_config: JSON | null = null,
): FetchData<T> => {
  const [url, setUrl] = useState<string>(initialUrl);
  const [data, setData] = useState<T | null>(null);
  const [error, setError] = useState<AxiosError | null>(null);

  const BACKEND_URL = ''; // config로 빼든지 하기

  const dispatch = useDispatch();

  const handleProgress = (status: boolean) => {
    dispatch(toggleProgress(status));
  };

  const fetchData = useCallback(async () => {
    handleProgress(true);

    try {
      const config = {
        body: request_config,
      };
      const res: AxiosResponse<T> = await axios.request<T>({
        url,
        method,
        ...config,
      });
      setData(res.data);
    } catch (err: any) {
      setError(err);
    }
    handleProgress(false);
  }, [url]);

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

// import {useAxios} from '../hooks/useAxios';
// const {data, error} = useAxios('https://httpbin.org/get', 'GET', null);
