<template>
  <div class="auth-container">
    <div class="bg"></div>
    <div class="content">
      <div class="title-info">
        <h2>智慧校园系统登录</h2>
      </div>
      <div class="auth-form">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="登录" name="first">
          <el-form>
            <div class="item">
              <el-input
                placeholder="请输入用户名"
                v-model="username"
                prefix-icon="el-icon-user"
              ></el-input>
            </div>
            <div class="item">
              <el-input 
                placeholder="请输入密码" 
                v-model="password"
                prefix-icon="el-icon-lock"
                show-password
                @keyup.enter.native="doLogin"></el-input>
            </div>
            <el-button :loading="loading" @click="doLogin">登录</el-button>
            <div class="login-type">
              <el-radio v-model="loginType" label="student">学生</el-radio>
              <el-radio v-model="loginType" label="manager">管理员</el-radio>
            </div>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
    </div>  
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      activeName: "first",
      username: "",
      password: "",
      loading: false,
      loginType: "student",
    };
  },
  methods: {
    doLogin() {
      this.loading = true;
      this.$request({
        url:
          "/auth" +
          (this.loginType === "student" ? "/studentLogin" : "/managerLogin"),
        method: "POST",
        data: {
          name: this.username,
          password: this.password,
        },
      })
        .then((res) => {
          if (res.code === "200") {
            this.$message.success("登录成功");
            this.$router.push("/home");
          } else {
            this.$message.error("登录失败");
          }
          this.loading = false;
        })
        .catch((err) => {
          this.$message.error("系统错误");
          this.loading = false;
        });
    },
  },
};
</script>
<style scoped lang="scss">
.auth-container {
  display: flex;
  justify-content: center;
  // border: 1px solid #eaeaea;
  // border-radius: 0 0 20px #eee;
  .bg {
    position: fixed;
    background-size: 100%;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-image: url(https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg);
    background-repeat: no-repeat;
  }

   .content {
     margin-top: 150px;
      .title-info {
        align-items: center;
        justify-content: center;
        text-align: center;
      }
      width: 400px;
      height: 100px;
      .auth-form {
        width: 450px;
        margin: 0 auto;
        .item {
          margin-top: 10px;
        }
        button {
          margin-top: 10px;
          width: 100%;
        }
        .login-type {
          margin-top: 20px;
        }
      }
  }

}
</style>