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
          //修改总条数记录分页
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
    },
    
    
    // TODO...
    uploadStudent(param) {
      const data = new FormData();
      data.append('file', param.file);
      fileRequest({
        url: '/import',
        method: 'POST',
        data: data,
      }).then( r => {
        if (r.code === '200'){
          this.$message.success('上传成功');
        } else {
          this.$message.error('上传失败');
        }
      }).catch(err => {
        this.$message.error('系统错误')
      })
    },
    downloadStudent(){
      
    }
  }
}