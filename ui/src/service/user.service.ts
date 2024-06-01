import { ApiResponse } from '@/types/api';
import http from './http.service';
import { apiRoutes } from '@/routes';
import { TUpdateProfile } from '@/schema';
import { toast } from 'react-toastify';
import {
  UPDATE_PROFILE_PENDING,
  UPDATE_PROFILE_SUCCESS,
} from '@/config';

class UserServcie {
  activateRegistration(token: string) {
    return http.get<ApiResponse<boolean>>(
      `${apiRoutes.activate_registration}/${token}`,
    );
  }
  getUserProfile(): Promise<ApiResponse<UserProfile>> {
    return http.get(apiRoutes.profile);
  }
  async updateProfile(params: TUpdateProfile): Promise<ApiResponse<boolean>> {
    const promise = http.put<ApiResponse<boolean>>(apiRoutes.profile, params);
    toast.promise(
      promise,
      {
        pending: UPDATE_PROFILE_PENDING,
        success: UPDATE_PROFILE_SUCCESS
      },
      {
        type: 'default',
      },
    );
    const data = await promise;
    return data;
  }
}
const userService = new UserServcie();
export default userService;
