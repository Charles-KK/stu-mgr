import axios from 'axios';
import router from "../router"
import {Message} from "element-ui";
const BASE_URL = 'http://localhost:8080'
export function fileRequest(options){
  const instance = axios.create({
      baseURL: 'http://localhost:8080/',
      headers: {"content-type": "multipart/form-data"}
  });

  instance.interceptors.response.use(res => {
    // HTTP401错误代表用户没有访问权限,需要进行身份认证
      if (res.data.code === '401'){
          router.push('/login');
      }
      return res.data;
  }, err => {
    Message.error('系统错误')
  })
  return instance(options)
}