import axios from 'axios';
import router from "../router"
import {Message} from "element-ui";
const BASE_URL = 'http://localhost:8080'

export function request(options){
  const instance = axios.create({
    baseURL: BASE_URL,
    withCredentials: true
  });

//响应拦截器 在接口响应后统一处理结果
  instance.interceptors.response.use(res => {
    if (res.data.code === '401'){
      router.push('/login');
    }
    return res.data;
  }, err => {
    // 504、404
    Message.error('系统错误')
  });

  return instance(options);
}