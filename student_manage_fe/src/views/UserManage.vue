<template>
  <div class="user-manage-wrap">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">学生管理</el-menu-item>
      <el-menu-item index="2">管理员管理</el-menu-item>
    </el-menu>

    <!-- search module -->
    <div>
      <el-input 
            v-model="search" 
            placeholder="输入「姓名」关键字来搜索"
            style="width:250px;margin-top:20px"
            @click="loadData"
            clearable
            ></el-input>
      <el-button 
          type="primary"
          style="margin-left:5px"
          @click="handleSearch(search)">查找</el-button>
    </div>
    
    <div class="student-list" v-if="activeIndex === '1'">
      <el-row>
        <el-col style="margin-top:16px">
          <el-button type="primary" plain @click="showAddStuForm" style="margin-bottom:20px">添加学生信息</el-button>
          <el-button type="primary" plain>导入学生信息</el-button>
        </el-col>
      </el-row>
      <!-- filter modules -->
      <el-row class="select-option">
        <el-col style="margin-bottom:16px">
            <el-button type="primary" plain @click="showFilter = true">筛选条件</el-button>
        </el-col>
        <el-col class="select-option-tags">
          <el-tag v-for="tag in filterTags" 
                  :key="tag"
                  :disable-transitions="true" 
                  closable
                  effect="dark"
                  @close="handleTagClose(tag)">
            {{tag}}
          </el-tag>
          <!-- <el-input
            class="input-new-tag"
            v-if="inputVisible"
            v-model="inputValue"
            ref="saveTagInput"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
          >
          </el-input>
          <el-button v-else class="button-new-tag" size="small" @click="showInput" >+ 新增筛选条件</el-button> -->
        </el-col>
      </el-row>
      <el-row>
        <el-table
                :data="studentList"
                stripe
                style="width: 100%"
                :default-sort = "{prop:'studentNo', order:'descending'}">
          <el-table-column
                  prop="studentNo"
                  label="学号"
                  sortable>
          </el-table-column>
          <el-table-column
                  prop="studentName"
                  label="姓名"
                  >
          </el-table-column>
          <el-table-column
                  prop="gender"
                  label="性别"
                  >
          </el-table-column>
          <el-table-column
                  prop="department"
                  label="学院"
                  >
          </el-table-column>
          <el-table-column
                  prop="majority"
                  label="专业"
                  >
          </el-table-column>
          <el-table-column
                  prop="grade"
                  label="年级"
                  >
          </el-table-column>
          <el-table-column
                  prop="classNo"
                  label="班级"
                  >
          </el-table-column>
          <el-table-column
                  prop="graduateYear"
                  label="毕业年份"
                  >
          </el-table-column>
          <el-table-column
                  prop="sourceCity"
                  label="城市"
                  >
          </el-table-column>
          <el-table-column
                  prop="address"
                  label="地址"
                  >
          </el-table-column>
          <!-- options column -->
          <el-table-column 
                label="操作"
                fixed="right"
                width="100">
            <template slot-scope="scope">
              <el-button
                type="primary"
                size="mini"
                @click="handleEdit(scope.row)"
                >编辑</el-button>
              <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="handleDelete(scope.row)"
              >
              <el-button slot="reference" type="danger">删除</el-button>
              </el-popconfirm>            
            </template>
         </el-table-column>
        </el-table>
      </el-row>
      
    <el-row>
      <!-- 分页 -->
      <el-pagination
              layout="sizes,total, prev, pager, next, jumper"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[5, 10, 15, 20]"
              :page-size='pageSize'
              :total="total">
      </el-pagination>
    </el-row>
    
    </div>
    <!-- managers list -->
    <div class="manager-list" v-else>
      <el-row>
        <el-col>
          <el-button type="primary" plain @click="showAddManagerForm" style="margin:10px">添加管理员信息</el-button>
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
          <el-table-column 
                label="操作"
                >
            <template slot-scope="scope">
              <el-button
                type="primary"
                size="mini"
                style="margin-right:5px"
                @click="handleEdit(scope.row)"
                >编辑</el-button>
              <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="handleDelete(scope.row)"
              >
              <el-button slot="reference" type="danger" size="mini">删除</el-button>
              </el-popconfirm>            
            </template>
         </el-table-column>
        </el-table>
        </el-row>
        <el-row>
            <el-pagination
              layout="sizes,total, prev, pager, next, jumper"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[5, 10, 15, 20]"
              :page-size='pageSize'
              :total="total">
            </el-pagination>
         </el-row>
    </div>
  <!-- edit dialog -->
    <el-dialog title="编辑学生信息" 
               :visible.sync="showEditStu" 
               style="width:80%"
               custom-class="dialog-form"
               @close="resetForm('studentInfo')">
      <!-- show student form -->
      <el-form :model="studentInfo" ref="studentInfo">
        <el-form-item label="学号">
          <!-- 禁止修改学号！ -->
          <el-input v-model="studentInfo.studentNo" style="width:80%" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="studentInfo.studentName" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="性别" >
          <!-- 禁止修改性别 -->
          <el-radio v-model="studentInfo.gender" label="男" disabled>男</el-radio>
          <el-radio v-model="studentInfo.gender" label="女" disabled>女</el-radio>
        </el-form-item>
        <el-form-item label="学院">
          <el-input v-model="studentInfo.department" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="studentInfo.majority" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="studentInfo.grade" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="studentInfo.classNo" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="毕业年份">
          <el-input v-model="studentInfo.graduateYear" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="studentInfo.sourceCity" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="studentInfo.address" style="width:80%"></el-input>
        </el-form-item>
      </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="showEditStu = false;closeDialog('studentInfo');">取 消</el-button>
          <el-button type="primary" @click="edit">确 定</el-button>
          <!-- @keyup.enter.native="edit"? -->
      </div>
    </el-dialog>
    <!-- show manager edit form -->
    <el-dialog title="编辑管理员信息" 
            :visible.sync="showEditManager" 
            style="width:80%"
            custom-class="dialog-form"
            @close="resetForm('managerInfo')">
      <el-form :model="managerInfo" ref="managerInfo">
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
        <el-button @click="showEditManager = false; closeDialog('managerInfo');">取 消</el-button>
        <el-button type="primary" @click="edit">确 定</el-button>
      </div>
    </el-dialog>
    <!-- add student-->
    <el-dialog title="添加学生信息" 
               :visible.sync="showStudent" 
               style="width:80%"
               custom-class="dialog-form">
      <div style="margin-bottom:10px;color:#ccc">注意：请按规范填写，完整填写各项内容后才可成功提交</div>

      <el-form :model="studentInfo" label-position="right" label-width="70px">
        <el-form-item label="学号">
          <el-input v-model="studentInfo.studentNo" placeholder="学号要求6位数以上" style="width:90%"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="studentInfo.studentName" style="width:90%" ></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="studentInfo.gender" label="男">男</el-radio>
          <el-radio v-model="studentInfo.gender" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="学院">
          <el-input v-model="studentInfo.department" style="width:90%" placeholder="按学院全称+学院规范填写，如计算机学院"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="studentInfo.majority" style="width:90%" placeholder="填写专业全称，如计算机科学与技术"></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="studentInfo.grade" style="width:90%" placeholder="按四位年份填写，如2018"></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="studentInfo.classNo" style="width:90%"></el-input>
        </el-form-item>
        <el-form-item label="毕业年份">
          <el-input v-model="studentInfo.graduateYear" style="width:90%" placeholder="按四位年份填写，如2022"></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="studentInfo.sourceCity" style="width:90%" placeholder="精确填写XX市（县）"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="studentInfo.address" style="width:90%" placeholder="请填写完整地址"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showStudent = false">取 消</el-button>
        <el-button type="primary" @click="addStudent">确 定</el-button>
      </div>
    </el-dialog>

    <!--add manager-->
    <el-dialog title="添加管理员信息" 
             :visible.sync="showManager" 
             style="width:80%"
             custom-class="dialog-form"> 

      <el-form :model="managerInfo" label-width="80px">
        <el-form-item label="姓名">
          <el-input v-model="managerInfo.managerName" style="width:90%"></el-input>
        </el-form-item>
        <el-form-item label="单位">
          <el-input v-model="managerInfo.organization" style="width:90%"></el-input>
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="managerInfo.position" style="width:90%"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="managerInfo.tel" style="width:90%"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showManager = false">取 消</el-button>
        <el-button type="primary" @click="addManager">确 定</el-button>
      </div>
    </el-dialog>

    <!--filters options-->
    <el-dialog title="筛选条件"  
               :visible.sync="showFilter"
               custom-class="dialog-form"
               style="width:80%"
               :before-close="cancelFilter"
               >
      <el-form :model="studentCondition" label-position="right" label-width="80px">
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
        <el-form-item label="城市">
          <el-input v-model="studentCondition.sourceCity"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelFilter">取 消</el-button>
        <el-button type="primary" @click="loadStudentList">确 定</el-button>
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
        //从后台查到数据放这里
        studentList: [],
        managerList: [],
        currentPage: 1,
        pageSize: 10,
        total: 0,
        // 查找关键字（name)
        search:  '',
        //dialog
        showStudent: false,
        showManager: false,
        showEditStu: false,
        showEditManager: false,
        //filter
        showFilter: false,
        inputVisible: false,
        inputValue: '',
        //form data 渲染到web界面上
        studentInfo: {},
        managerInfo: {},
        studentCondition: {},  
        // filter
        tags:[]
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
      handleTagClose(tag) {   
        // console.log(typeof tag);
        if(tag.indexOf('学号') !== -1) {
          delete this.studentCondition.studentNo 
        } else if(tag.indexOf('姓名') !== -1) {
          delete this.studentCondition.studentName
        } else if(tag.indexOf('性别') !== -1) {
          delete this.studentCondition.gender
        } else if(tag.indexOf('学院') !== -1) {
         delete this.studentCondition.department
        } else if(tag.indexOf('专业') !== -1) {
          this.studentCondition.majority = {}
        } else if(tag.indexOf('班级') !== -1) {
          delete this.studentCondition.classNo
        } else if(tag.indexOf('城市') !== -1) {
          delete this.studentCondition.sourceCity
        }
        const item = this.filterTags.indexOf(tag);
        this.filterTags.splice(item, 1);
        this.loadStudentList()
      },
      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },
      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.filterTags.push(inputValue);
        }
        this.loadStudentList();
        this.inputVisible = false;
        this.inputValue = '';
      },
      cancelFilter() {
        this.showFilter = false;
        this.studentCondition = {};
      }
      ,
      handleCurrentChange(page) {
        this.currentPage = page
        this.loadData()
      },
      showAddStuForm() {
        this.studentInfo = {};
        this.showStudent = true;
      },
      showAddManagerForm() {
        this.managerInfo = {};
        this.showManager = true;
      },
      //实现Element表单关闭后清空字段内容?不生效 还是我自己写了哎
      // resetForm(formName) {
      //     this.$refs[formName].resetFields();
      //     this.showEdit = false;
      // }
      handleSearch(name) {
        if(name === '') {
          this.loadData()
        }else if(this.activeIndex === '1') {
           this.$request({
            url: '/student/search/'+ name,
            params: {
              currentPage: this.currentPage,
              pageSize: this.pageSize,
            }
          }).then(r => {
            if(r.code === '200') {
              this.studentList = r.data.list
              this.total = r.total
            }
          })         
        }
           else {
          this.$request({
            url: '/manager/search/'+ name,
            params: {
              currentPage: this.currentPage,
              pageSize: this.pageSize,
            }
          }).then(r => {
            if(r.code === '200') {
              this.managerList = r.data.list
              this.total = r.total
            }
          })
        }
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
            pageSize: this.pageSize,
            search: this.search,
            condition: this.studentCondition
          }
        }).then(r => {
          //
          if (r.code === '200') {
            //将后端数据渲染到前端
            this.studentList = r.data.list
            //修改总条数记录 
            this.total = r.data.total
          }
        })
      },
      loadManagerList() {
        this.$request({
          url: '/manager',
          //Get请求
          params: {
            currentPage: this.currentPage,
            data: {
               currentPage: this.currentPage,
               pageSize: this.pageSize,
            }
          }
        }).then(r => {
          if (r.code === '200') {
            this.managerList = r.data.list
            this.total = r.data.total
          }
        })
      },
      addStudent() {
        this.$request({
          url: '/student/add',
          method: 'POST',
          data: this.studentInfo
        }).then(r => {
          if (r.code === '200') {
            this.loadStudentList();
            this.$message({
               message: '添加成功',
               type: 'success'
            })
          }
        })
        this.showStudent = false;
      },
      // update student or manager info
      edit() {
        if(this.activeIndex ==='1') {
           this.$request({
            url: '/student/edit',
            method: 'PUT',
            data: this.studentInfo
          }).then(r => {
            if (r.code === '200') {
              // console.log(this.studentInfo)
              this.loadStudentList()
              this.$message({
               message: '更新成功',
               type: 'success'
              })    
          } else {
              this.$message({
               message: 'res.message',
               type: 'error'
            })
          }
          }) 
          this.showEditStu = false
          // console.log(this.studentInfo);
        } else {
          this.$request({
            url: '/manager/edit',
            method: 'PUT',
            data: this.managerInfo
          }).then(r => {
            if (r.code === '200') {
              this.loadManagerList()
              // console.log(this.managerInfo)
              this.$message({
               message: '更新成功',
               type: 'success'
            })
          } else {
              this.$message({
               message: '更新失败',
               type: 'error'
            })
          }
          })
          this.showEditManager = false
        }
        
      },
      handleEdit(row){
        // console.log(row);
        //deep clone
        if(this.activeIndex === '1') {
          this.studentInfo = JSON.parse(JSON.stringify(row))
          this.showEditStu = true
        } else {
          this.managerInfo = JSON.parse(JSON.stringify(row))
          this.showEditManager = true
        }
      },
      handleDelete(row){
        // console.log(row);
        // console.log(row.studentId);
        if(this.activeIndex === '1') {
          this.$request({
          url: '/student/' + row.studentId,
          method: 'DELETE',
          data: row.studentId
        }).then(r => {
          if(r.code === '200') {
               this.loadStudentList();
               this.$message({
               message: '删除成功',
               type: 'success'
            })
        } else {
            this.loadStudentList()
            this.$message({
            message: '删除失败',
            type: 'error' })
        }
      })
    }else {
        this.$request({
          url: '/manager/' + row.managerId,
          method: 'DELETE',
          data: row.managerId
        }).then(r => {
          if(r.code === '200') {
               this.loadManagerList()
               this.$message({
               message: '删除成功',
               type: 'success'
            })
        }else {
               this.loadManagerList()
               this.$message({
               message: '删除失败',
               type: 'error' })
        }
        })
        this.loadManagerList()
      }
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
      padding-top: 5px;
    }

    .select-option {
      text-align: left;
      margin: 10px 30px;
    }
    
  }
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>