<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="图书名" suffix-icon="el-icon-search"
                v-model="bookName"></el-input>
      <el-input style="width: 200px" placeholder="ISBN码" suffix-icon="el-icon-message" class="ml-5"
                v-model="isbnCode"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
        class="ml-5"
        confirm-button-text='确定'
        cancel-button-text='再想想'
        icon="el-icon-info"
        icon-color="red"
        title="您确定批量删除这些数据吗？"
        @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <el-upload action="http://localhost:9090/book/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="图书ID" align="center"></el-table-column>
      <el-table-column prop="bookName" label="图书名" align="center"></el-table-column>
      <el-table-column prop="bookAuthor" label="图书作者" align="center"></el-table-column>
      <el-table-column prop="bookType" label="图书类别" align="center"></el-table-column>
      <el-table-column prop="bookLocation" label="图书位置" align="center"></el-table-column>
      <el-table-column prop="bookStatus" label="图书状态(是否借出)" align="center"></el-table-column>
      <el-table-column prop="bookDescription" label="图书描述" align="center"></el-table-column>
      <el-table-column prop="isbnCode" label="ISBN码" align="center"></el-table-column>



      <el-table-column label="操作" align="center" width="170">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
            class="ml-5"
            confirm-button-text='确定'
            cancel-button-text='再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定删除吗？"
            @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="图书名">
          <el-input v-model="form.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书作者">
          <el-input v-model="form.bookAuthor" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书类型">
          <el-input v-model="form.bookType" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书位置">
          <el-input v-model="form.bookLocation" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书状态">
          <el-input v-model="form.bookStatus" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图书描述">
          <el-input v-model="form.bookDescription" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="ISBN码">
          <el-input v-model="form.isbnCode" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      tableData: [],
      multipleSelection: [],
      pageNum: 1,
      pageSize: 10,
      bookName:'',
      isbnCode:'',
      total: 0,
      dialogFormVisible: false,
      form: {},
    }
  },
  created() {
    this.load();
  },
  methods: {
    reset() {
      this.isbnCode=''
      this.bookName=''
      this.load();
    },
    load() {
      this.request.get("/book/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          bookName: this.bookName,
          isbnCode: this.isbnCode,
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
    save() {
      this.request.post("/book/save", this.form).then(res => {
        if (res.code==='200') {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error("保存失败");
        }
      })
    },
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      this.load();
    },
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    handleEdit(row) {
      this.form = row;
      this.dialogFormVisible = true;
    },
    del(id) {
      this.request.delete("/book/" + id).then(res => {
        if (res.code==='200') {
          this.$message.success("删除成功");
          this.load();
        } else {
          this.$message.error("删除失败");
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;

    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id);
      this.request.post("/book/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      })
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum = pageNum;
      this.load();
    },
    exp() {
      window.open("http://localhost:9090/book/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("文件导入成功");
      this.load();
    }
  },
}
</script>


<style>
.headerBg {
  background: #eee !important;
}
</style>