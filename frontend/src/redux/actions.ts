import {AnyAction} from 'redux';
import {User} from './AppState';

interface LogoutAction extends AnyAction {
  type: 'logout';
}

interface LoginAction extends AnyAction {
  type: 'login';
  loggedUser: User;
}

export interface ToggleProgressAction extends AnyAction {
  type: 'set_progress';
  inProgress: boolean;
}

export type LoginActions = LogoutAction | LoginAction;

export const loginAction = (loggedUser: User): LoginAction => ({
  type: 'login',
  loggedUser,
});

export const logoutAction = (): LogoutAction => ({
  type: 'logout',
});

export const toggleProgress = (inProgress: boolean): ToggleProgressAction => ({
  type: 'set_progress',
  inProgress: inProgress,
});
