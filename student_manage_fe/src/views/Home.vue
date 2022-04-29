<template>
  <div class="home">
    <el-container class="outer-container">
      <el-header>
        <el-row>
          <el-col :span="4" class="book-manage-title"> </el-col>
          <el-col :span="4" :offset="20">
            <el-dropdown>
              <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item><el-link @click="logout">注销登录</el-link> </el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>    
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-header>
      <el-container class="main-container">
        <el-aside width="130px">
          <el-menu
            :default-active="defaultActive"
            class="el-menu-vertical-demo"
            @select="onSelect"
          >
            <el-menu-item index="1">
              <i class="el-icon-s-order"></i>
              <span slot="title">请假管理</span>
            </el-menu-item>
            <el-menu-item index="2" v-if="$store.state.role === 'manager'">
              <i class="el-icon-user"></i>
              <span slot="title">用户管理</span>
            </el-menu-item>
              <el-menu-item index="3" v-if="$store.state.role === 'manager'">
              <i class="el-icon-reading"></i>
              <span slot="title">数据视图</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      defaultActive: "1",
      idx2Path: {
        1: "/home/leaveRecord",
        2: "/home/userManage",
        3: "/home/displayboard"
      },
      path2Idx: {
        "/home/leaveRecord": "1",
        "/home/userManage": "2",
        "/home/displayboard": "3",
      },
    };
  },
  created() {
    this.defaultActive = this.path2Idx[this.$route.path];
  },
  methods: {
    onSelect(idx) {
      console.log(idx);
      this.$router.push(this.idx2Path[idx]);
    },
    logout() {
      // console.log(123);
      this.$request({
        url: "/auth/logout",
      }).then((r) => {
        if (r.code === "200") {
          this.$router.push("/login");
        }
      });
    },
  },
};
</script>

<style lang="scss">
.home,
.outer-container,
.main-container {
  height: 100%;
}
.el-header {
  background-color: dodgerblue;
  color: #fff;
  text-align: center;
  line-height: 60px;
  .book-manage-title {
    line-height: 30px;
    .title-zh {
      font-weight: bold;
      font-size: 24px;
    }
    .title-en {
      font-size: 12px;
    }
  }
  #header-avatar {
    background: url("../assets/img/avatar.png") center;
    -webkit-background-size: cover;
    background-size: cover;
    cursor: pointer;
    width: 50px;
    height: 50px;
    border-radius: 50%;
    position: absolute;
    right: 30px;
    top: 5px;
  }
}

.el-aside {
  background-color: #fff;
  color: #333;
  text-align: center;
  height: 100%;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  height: 100%;
}
</style>
