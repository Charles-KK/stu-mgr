<template>
  <div class="leave-record-manage-wrap">
    <el-row v-if="$store.state.role === 'student'">
      <el-col :span="4">
        <el-button type="primary" plain @click="dialogFormVisible = true">申请请假</el-button>
      </el-col>
    </el-row>
    <el-dialog title="请假" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="请假原因">
          <el-input v-model="form.reason" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="请假时间" >
          <el-col :span="9">
            <el-date-picker type="datetime" placeholder="选择时间" value-format="yyyy-MM-dd HH:mm:ss"
                            v-model="form.leaveTime" style="width: 80%;"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="9">
            <el-date-picker type="datetime" placeholder="选择时间" value-format="yyyy-MM-dd HH:mm:ss"
                            v-model="form.returnTime" style="width: 80%;"></el-date-picker>
          </el-col>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRecord">确 定</el-button>
      </div>
    </el-dialog>
    <el-row>
      <el-table
              :data="tableData"
              style="width: 100%">
        <el-table-column
                prop="studentName"
                label="学生姓名"
                width="150">
        </el-table-column>
        <el-table-column
                prop="reason"
                label="请假原因"
                width="200">
        </el-table-column>
        <el-table-column
                prop="leaveTime"
                label="开始时间"
                width="180">
        </el-table-column>
        <el-table-column
                prop="returnTime"
                label="结束时间"
                width="180">
        </el-table-column>
        <el-table-column
                prop="applyTime"
                label="申请时间"
                width="180">
        </el-table-column>
        <el-table-column
                prop="managerName"
                label="审批人"
                width="150">
        </el-table-column>
        <el-table-column label="操作" v-if="$store.state.role === 'manager'">
          <template slot-scope="scope">
            <el-button
                    size="mini"
                    :disabled="scope.row.managerId"
                     @click="approve(scope.row.recordId)">{{scope.row.managerId ? '已同意' : '同意'}}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-row>
      <el-pagination
              layout="total, prev, pager, next, jumper"
              :page-size="20"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :total="total">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "LeaveRecordManage",
    data() {
      return {
        tableData: [],
        total: 0,
        currentPage: 1,
        dialogFormVisible: false,
        form: {}
      }
    },
    created() {
      this.loadRecords();
    },
    methods: {
      loadRecords() {
        this.$request({
          url: '/leave',
          params: {
            currentPage: this.currentPage
          }
        }).then(r => {
          if (r.code === '200') {
            this.tableData = r.data.list
            this.total = r.data.total
          }
        })
      },
      addRecord() {
        this.dialogFormVisible = false
        this.$request({
          url: '/leave/add',
          method: 'POST',
          data: this.form
        }).then(r => {
          if (r.code === '200') {
            this.form = {}
            this.loadRecords()
          }
        })
      },
      approve(recordId) {
        this.$request({
          url: '/leave/approve',
          method: 'POST',
          params: {
            recordId: recordId
          }
        }).then(r => {
          if (r.code === '200') {
            this.loadRecords()
          }
        })
      },
      handleCurrentChange(page) {
        this.currentPage = page
        this.loadRecords()
      }
    }
  }
</script>

<style scoped lang="scss">
  .leave-record-manage-wrap {
    height: 100%;
    background-color: #fff;
    padding: 5px;

  }
</style>