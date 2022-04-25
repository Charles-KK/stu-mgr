<template>
  <div class="home">
    <el-container class="outer-container">
      <el-header>
        <el-row>
          <el-col :span="4" class="book-manage-title">
          </el-col>
          <el-col :span="4" :offset="20">
            <el-link @click="logout">注销登录</el-link>
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
  name: 'Home',
  data() {
    return {
      defaultActive: '1',
      idx2Path: {
        '1': '/home/leaveRecord',
        '2': '/home/userManage',
      },
      path2Idx: {
        '/home/leaveRecord': '1',
        '/home/userManage': '2',
      }
    }
  },
  created() {
    this.defaultActive = this.path2Idx[this.$route.path]
  },
  methods: {
    onSelect(idx) {
      console.log(idx);
      this.$router.push(this.idx2Path[idx])
    },
    logout() {
      this.$request({
        url: '/auth/logout'
      }).then(r => {
        if (r.code === '200') {
          this.$router.push('/login')
        }
      })

    }
  }
}
</script>

<style lang="scss">
  .home, .outer-container, .main-container {
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
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    height: 100%;
  }
</style>
