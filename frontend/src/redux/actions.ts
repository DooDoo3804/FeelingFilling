import {AnyAction} from 'redux';
import {User} from './AppState';

interface LogoutAction extends AnyAction {
  type: 'logout';
}

interface LoginAction extends AnyAction {
  type: 'login';
  loggedUser: User;
}

export type LoginActions = LogoutAction | LoginAction;

export interface ToggleProgressAction extends AnyAction {
  type: 'set_progress';
  inProgress: boolean;
}

export interface TokenAction extends AnyAction {
  type: 'set_accesstoken';
  refresh_token: string;
  access_token: string;
}

export const loginAction = (loggedUser: User): LoginAction => ({
  type: 'login',
  loggedUser: loggedUser,
});

export const logoutAction = (): LogoutAction => ({
  type: 'logout',
});

export const toggleProgress = (inProgress: boolean): ToggleProgressAction => ({
  type: 'set_progress',
  inProgress: inProgress,
});

export const tokenAction = (
  refresh_token: string,
  access_token: string,
): TokenAction => ({
  type: 'set_accesstoken',
  refresh_token: refresh_token,
  access_token: access_token,
});
