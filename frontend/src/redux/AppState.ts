export type User = {
  // 유저의 이름, id, 돈의 범위
  name: string;
  id: number;
  min_money: number;
  max_money: number;
  refresh_token: string;
  access_token: string;
};

export type AppState = {
  loggedIn: boolean;
  loggedUser: User;
  inProgress: boolean;
};
