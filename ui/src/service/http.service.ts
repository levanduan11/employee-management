import { isDev } from '@/lib';
import { ApiResponse } from '@/types/api';
import axios, {
  AxiosInstance,
  CreateAxiosDefaults,
  AxiosRequestConfig,
} from 'axios';
import { toast } from 'react-toastify';
import {
  ERROR_400,
  ERROR_401,
  ERROR_403,
  ERROR_404,
  ERROR_500,
} from '@/config';
class HttpClient {
  public instance(config?: CreateAxiosDefaults): AxiosInstance {
    const url = process.env.NEXT_PUBLIC_API_URL;
    if (!url) {
      toast.error('Api url not found');
      throw new Error('Api url not found');
    }
    const instance = axios.create({
      ...config,
      baseURL: url,
    });

    instance.interceptors.response.use(
      (response) => {
        if (isDev()) {
          console.log('response', response);
        }
        return response;
      },
      (error) => this.handleApiError(error),
    );
    return instance;
  }
  private handleApiError(error: unknown) {
    if (isDev()) {
      console.log('error', error);
    }
    let message = ERROR_500;
    if (axios.isAxiosError(error)) {
      const response: ApiResponse = error.response?.data;
      const messageServer = response?.message;
      const status = error.response?.status;
      switch (status) {
        case 400:
          message = messageServer ?? ERROR_400;
          break;
        case 401:
          message = messageServer ?? ERROR_401;
          break;
        case 403:
          message = messageServer ?? ERROR_403;
          break;
        case 404:
          message = messageServer ?? ERROR_404;
          break;
        default:
          break;
      }
    }
    toast.error(message);
    return Promise.reject(error);
  }
  async get<R>(path: string, config?: AxiosRequestConfig): Promise<R> {
    const instance = this.instance();
    const response = await instance.get<R>(path, config);
    return response.data;
  }
  async post<R>(
    path: string,
    data: unknown,
    config?: AxiosRequestConfig,
  ): Promise<R> {
    const instance = this.instance();
    const response = await instance.post<R>(path, data, config);
    return response.data;
  }
  async put<R>(
    path: string,
    data: unknown,
    config?: AxiosRequestConfig,
  ): Promise<R> {
    const instance = this.instance();
    const response = await instance.put<R>(path, data, config);
    return response.data;
  }
  async delete<R>(path: string, config?: AxiosRequestConfig): Promise<R> {
    const instance = this.instance();
    const response = await instance.delete<R>(path, config);
    return response.data;
  }
  async patch<R>(
    path: string,
    data: unknown,
    config?: AxiosRequestConfig,
  ): Promise<R> {
    const instance = this.instance();
    const response = await instance.patch<R>(path, data, config);
    return response.data;
  }
}
const http = new HttpClient();
export default http;
