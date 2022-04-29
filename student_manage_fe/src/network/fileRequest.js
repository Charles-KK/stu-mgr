import axios from 'axios';
import router from "../router"
import {Message} from "element-ui";
const BASE_URL = 'http://localhost:8080'

// TODO ... 
export function fileRequest(options){
  const instance = axios.create({
      baseURL: 'http://localhost:8080/',
      headers: {"content-type": "multipart/form-data"}

  });

  instance.interceptors.response.use(res => {
      if (res.data.code === '010'){
          router.push('/login');
          
      }
      return res.data;
  }, err => {
    Message.error('系统错误')
  })
}