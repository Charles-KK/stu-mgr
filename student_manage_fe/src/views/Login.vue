<template>
  <div id="login-wrap" class="clearfix">
    <div id="login-bg">

      <div id="login-form" class="container-fluid">
        <el-col :span="24">
          <div id="login-form-title">
            登录
          </div>
          <div id="login-form-name">
            <el-input placeholder="用户名（姓名）" v-model="username"></el-input>
          </div>
          <div id="login-form-pass">
            <el-input placeholder="密码（学生默认后学号六位）" type="password" v-model="password" show-password
                      @keyup.enter.native="doLogin"
            ></el-input>
          </div>
          <div id="login-form-submit" class="clearfix">
            <el-button type="primary" :loading="loading" @click="doLogin" >登录</el-button>
          </div>
          <div class="login-type">
            <el-radio v-model="loginType" label="student">学生</el-radio>
            <el-radio v-model="loginType" label="manager">管理员</el-radio>
          </div>
        </el-col>

      </div>
    </div>

  </div>
</template>

<script>

  export default {
    name: "Login",
    data(){
      return {
        blockActive: false,
        username: '',
        password: '',
        loading: false,
        loginType: 'student'
      }
    },
    methods: {
      doLogin(){
        this.loading = true;
        this.$request({
          url: '/auth' + (this.loginType === 'student' ? '/studentLogin' : '/managerLogin'),
          method: 'POST',
          data: {
            name: this.username,
            password: this.password
          }
        }).then ( res => {
          if(res.code === '200'){
            this.$message.success('登录成功');
            this.$router.push('/home')
          } else {
            this.$message.error('登录失败');
          }
          this.loading = false;
        }).catch( err => {
          this.$message.error('系统错误');
          this.loading = false;
        })
      }
    },
    mounted() {
      setInterval(() => {
        this.blockActive = !this.blockActive;
      },5000);
      setTimeout( () => {
        this.blockActive = !this.blockActive;
      },200);
    }
  }
</script>

<style scoped lang="scss">
  #login-wrap{
    background: url("../assets/img/loginbg.jpg") no-repeat;
    background-size: cover;
    height: 100%;

    #login-bg{
      height: 100%;
      background-color: rgba(82,101,249,0.3);
      overflow: hidden;
      #login-bg-block-top{
        width: 5000px;
        height: 1000px;
        background-color: rgba(204, 229, 249, .9);
        transform: rotate(15deg) translate(-300px, -375px);
        transition: 5s;
        &.block-active{
          transform: rotate(15deg) translate(0, -425px);
        }
      }
      #login-bg-block-bottom{
        width: 5000px;
        height: 1000px;
        background-color: rgba(204, 229, 249, .9);
        transform: rotate(15deg) translate(-600px, 225px);
        transition: 5s;
        &.block-active{
          transform: rotate(15deg) translate(-600px, 175px);
        }
      }
    }
  }

  #login-welcome{
    position: absolute;
    top: 300px;
    left: 300px;
    text-align: center;
    font-weight: bold;
    text-shadow: 5px 5px 5px #777;
    #welcome-system-name{
      font-size: 56px;
      color: #eee;
      margin-bottom: 20px;
    }
    #welcome-system-name-eng{
      font-size: 24px;
      color: #eee;
    }
  }

  #login-form{
    height: 290px;
    width: 300px;
    padding: 40px 15px;
    border-radius: 5px;
    background-color: rgba(255,255,255,.8);
    box-shadow: 0 0 15px #666;

    position: absolute;
    right: 0;
    top: 0;
    left: 0;
    bottom: 0;
    margin: auto;

    #login-form-avatar{
      background: url("../assets/img/avatar.png") center ;
      background-size: cover;
      border-radius: 50%;
      width: 100px;
      height: 100px;
      position: absolute;
      top: -50px;
      left: 100px;
    }
    #login-form-title{
      font-size: 24px;
      text-align: center;
      color: dodgerblue;
      margin: 25px;
      font-weight: bold;
    }

    #login-form-name, #login-form-pass, #login-form-submit{
      margin: 15px;
    }

    #login-form-submit .el-button{
      width: 240px;
    }

    .login-type {
      text-align: center;
    }
  }
</style>