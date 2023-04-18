import {useState, useEffect} from 'react';

type FetchData = {
  data: any;
  error: Error | null;
  inProgress: boolean;
};

export const useFetch = (url: string): FetchData => {
  const [data, setData] = useState<any>(null);
  const [error, setError] = useState<Error | null>(null);
  const [inProgress, setInProgress] = useState<boolean>(false);

  useEffect(() => {
    async function fetchData() {
      try {
        setInProgress(true);
        const res = await fetch(url);
        const result: unknown = await res.json();
        if (res.ok) {
          setData(result);
          setError(null);
        } else {
          throw result;
        }
      } catch (err) {
        if (err instanceof Error) {
          // Error 객체인 경우에만 setError 호출
          setError(err);
        } else {
          setError(new Error('Unknown error'));
        }
      } finally {
        setInProgress(false);
      }
    }
    fetchData();
  }, [url]);

  return {data, error, inProgress};
};

// 다음과 같이 import하여 사용
// import { useFetch } from './useFetch';

// const { data, error, inProgress } = useFetch(URL);
