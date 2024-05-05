import { TLogin } from '@/schema';
import http from './http.service';
import { apiRoutes, routes } from '@/routes';
import { ApiResponse } from '@/types/api';
import { Jwt } from '@/types/auth';
import { toast } from 'react-toastify';
import {
  LOGIN_PENDING,
  LOGIN_SUCCESS,
  LOGOUT_FAIL,
  LOGOUT_SUCCESS,
} from '@/config';
import Cookie from 'js-cookie';
import { ACCESS_TOKEN } from '@/constants';
import { navigate } from '@/server';

class AuthService {
  async login(params: TLogin): Promise<ApiResponse<Jwt>> {
    const promise = http.post<ApiResponse<Jwt>>(apiRoutes.login, params);
    toast.promise(
      promise,
      {
        pending: LOGIN_PENDING,
        success: LOGIN_SUCCESS,
      },
      {
        type: 'default',
      },
    );
    const data = await promise;
    return data;
  }
  handleLoginSuccess(data: Jwt | undefined) {
    this.cleanCookieStorage();
    if (!data) return;
    const { access_token } = data;
    Cookie.set(ACCESS_TOKEN, access_token);
    navigate(routes.dashboard);
  }

  handleLogout() {
    try {
      this.cleanCookieStorage();
      navigate(routes.login);
      toast(LOGOUT_SUCCESS);
    } catch (e) {
      toast.error(LOGOUT_FAIL);
    }
  }
  cleanCookieStorage() {
    Cookie.remove(ACCESS_TOKEN);
  }
  isLoggedIn() {
    return !!Cookie.get(ACCESS_TOKEN);
  }
}
const authService = new AuthService();
export default authService;
