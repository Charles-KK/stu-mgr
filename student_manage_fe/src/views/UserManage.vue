<template>
  <div class="user-manage-wrap">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">学生管理</el-menu-item>
      <el-menu-item index="2">管理员管理</el-menu-item>
    </el-menu>
    <div class="student-list" v-if="activeIndex === '1'">
      <el-row>
        <el-col :span="6">
          <el-button type="primary" plain @click="showStudent = true">添加学生信息</el-button>
          <el-button type="primary" plain>导入学生信息</el-button>
        </el-col>
      </el-row>
      <el-row class="select-option">
        <el-col class="select-option-tags" :span="20">
          <el-tag v-for="tag in filterTags" :key="tag">{{tag}}</el-tag>
        </el-col>
        <el-col class="select-option-tags" :span="4">
          <el-button type="primary" plain @click="showFilter = true">筛选条件</el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-table
                :data="studentList"
                style="width: 100%">
          <el-table-column
                  prop="studentNo"
                  label="学号"
                  width="150">
          </el-table-column>
          <el-table-column
                  prop="studentName"
                  label="姓名"
                  width="120">
          </el-table-column>
          <el-table-column
                  prop="gender"
                  label="性别"
                  width="50">
          </el-table-column>
          <el-table-column
                  prop="department"
                  label="学院"
                  width="120">
          </el-table-column>
          <el-table-column
                  prop="majority"
                  label="专业"
                  width="150">
          </el-table-column>
          <el-table-column
                  prop="grade"
                  label="年级"
                  width="100">
          </el-table-column>
          <el-table-column
                  prop="classNo"
                  label="班级"
                  width="100">
          </el-table-column>
          <el-table-column
                  prop="graduateYear"
                  label="毕业年份"
                  width="100">
          </el-table-column>
          <el-table-column
                  prop="sourceCity"
                  label="城市"
                  width="120">
          </el-table-column>
          <el-table-column
                  prop="address"
                  label="地址"
                  width="300">
          </el-table-column>
        </el-table>
      </el-row>

    </div>
    <div class="manager-list" v-else>
      <el-row>
        <el-col :span="4">
          <el-button type="primary" plain @click="showManager = true">添加管理员信息</el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-table
                :data="managerList"
                style="width: 100%">
          <el-table-column
                  prop="managerName"
                  label="姓名"
                  width="180">
          </el-table-column>
          <el-table-column
                  prop="organization"
                  label="单位"
                  width="180">
          </el-table-column>
          <el-table-column
                  prop="position"
                  label="职位"
                  width="180">
          </el-table-column>
          <el-table-column
                  prop="tel"
                  label="电话"
                  width="200">
          </el-table-column>
        </el-table>
      </el-row>
    </div>
    <el-row>
      <el-pagination
              layout="total, prev, pager, next, jumper"
              :page-size="20"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :total="total">
      </el-pagination>
    </el-row>
    <el-dialog title="添加学生信息" :visible.sync="showStudent">
      <el-form :model="studentInfo">
        <el-form-item label="学号">
          <el-input v-model="studentInfo.studentNo"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="studentInfo.studentName"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="studentInfo.gender" label="男">男</el-radio>
          <el-radio v-model="studentInfo.gender" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="学院">
          <el-input v-model="studentInfo.department"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="studentInfo.majority"></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="studentInfo.grade"></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="studentInfo.classNo"></el-input>
        </el-form-item>
        <el-form-item label="毕业年份">
          <el-input v-model="studentInfo.graduateYear"></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="studentInfo.sourceCity"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="studentInfo.address"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showStudent = false">取 消</el-button>
        <el-button type="primary" @click="addStudent">确 定</el-button>
      </div>
    </el-dialog>
    <!--筛选条件-->
    <el-dialog title="添加学生信息" :visible.sync="showFilter">
      <el-form :model="studentCondition">
        <el-form-item label="学号">
          <el-input v-model="studentCondition.studentNo"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="studentCondition.studentName"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="studentCondition.gender" label="男">男</el-radio>
          <el-radio v-model="studentCondition.gender" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="学院">
          <el-input v-model="studentCondition.department"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="studentCondition.majority"></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="studentCondition.grade"></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="studentCondition.classNo"></el-input>
        </el-form-item>
        <el-form-item label="毕业年份">
          <el-input v-model="studentCondition.graduateYear"></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="studentCondition.sourceCity"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showFilter = false">取 消</el-button>
        <el-button type="primary" @click="loadStudentList">确 定</el-button>
      </div>
    </el-dialog>

    <!--添加管理员-->
    <el-dialog title="添加管理员信息" :visible.sync="showManager">
      <el-form :model="managerInfo">
        <el-form-item label="姓名">
          <el-input v-model="managerInfo.managerName"></el-input>
        </el-form-item>
        <el-form-item label="单位">
          <el-input v-model="managerInfo.organization"></el-input>
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="managerInfo.position"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="managerInfo.tel"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showManager = false">取 消</el-button>
        <el-button type="primary" @click="addManager">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "UserManage",
    data() {
      return {
        activeIndex: '1',
        studentList: [],
        managerList: [],
        currentPage: 1,
        total: 0,
        showStudent: false,
        showManager: false,
        showFilter: false,
        studentInfo: {},
        managerInfo: {},
        studentCondition: {}
      }
    },
    created() {
      this.loadData()
    },
    computed: {
      filterTags() {
        let tags = []
        if (this.studentCondition.studentNo) {
          tags.push("学号: " + this.studentCondition.studentNo)
        }
        if (this.studentCondition.studentName) {
          tags.push("姓名: " + this.studentCondition.studentName)
        }
        if (this.studentCondition.gender) {
          tags.push("性别: " + this.studentCondition.gender)
        }
        if (this.studentCondition.department) {
          tags.push("学院: " + this.studentCondition.department)
        }
        if (this.studentCondition.majority) {
          tags.push("专业: " + this.studentCondition.majority)
        }
        if (this.studentCondition.grade) {
          tags.push("年级: " + this.studentCondition.grade)
        }
        if (this.studentCondition.classNo) {
          tags.push("班级: " + this.studentCondition.classNo)
        }
        if (this.studentCondition.graduateYear) {
          tags.push("毕业年份: " + this.studentCondition.graduateYear)
        }
        if (this.studentCondition.sourceCity) {
          tags.push("城市: " + this.studentCondition.sourceCity)
        }
        return tags
      }
    },
    methods: {
      handleSelect(idx) {
        this.activeIndex = idx
        this.currentPage = 1;
        this.loadData()
      },
      handleCurrentChange(page) {
        this.currentPage = page
        this.loadData()
      },
      loadData() {
        if (this.activeIndex === '1') this.loadStudentList()
        else this.loadManagerList()
      },
      loadStudentList() {
        this.showFilter = false
        this.$request({
          url: '/student',
          method: 'POST',
          data: {
            currentPage: this.currentPage,
            condition: this.studentCondition
          }
        }).then(r => {
          if (r.code === '200') {
            this.studentList = r.data.list
            this.total = r.data.total
          }
        })
      },
      loadManagerList() {
        this.$request({
          url: '/manager',
          params: {
            currentPage: this.currentPage
          }
        }).then(r => {
          if (r.code === '200') {
            this.managerList = r.data.list
            this.total = r.data.total
          }
        })
      },
      addStudent() {
        this.showStudent = false
        this.$request({
          url: '/student/add',
          method: 'POST',
          data: this.studentInfo
        }).then(r => {
          if (r.code === '200') {
            this.loadStudentList()
          }
        })
      },
      addManager() {
        this.showManager = false
        this.$request({
          url: '/manager/add',
          method: 'POST',
          data: this.managerInfo
        }).then(r => {
          if (r.code === '200') {
            this.loadManagerList()
          }
        })
      }
    }
  }
</script>

<style scoped lang="scss">
  .user-manage-wrap {
    height: 100%;
    background-color: #fff;
    padding: 5px;

    .student-list, .manager-list {
      padding-top: 10px;
    }

    .select-option {
      text-align: left;
      margin: 10px 30px;
    }
  }
</style>