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
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg">
      <el-table-column prop="id" label="图书ID" align="center"></el-table-column>
      <el-table-column prop="bookName" label="图书名" align="center"></el-table-column>
      <el-table-column prop="bookAuthor" label="图书作者" align="center"></el-table-column>
      <el-table-column prop="bookType" label="图书类别" align="center"></el-table-column>
      <el-table-column prop="bookLocation" label="图书位置" align="center"></el-table-column>
      <el-table-column prop="bookStatus" label="图书状态(是否借出)" align="center"></el-table-column>
      <el-table-column prop="bookDescription" label="图书描述" align="center"></el-table-column>
      <el-table-column prop="isbnCode" label="ISBN码" align="center"></el-table-column>
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
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      bookName:'',
      isbnCode:'',
      total: 0,
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
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum = pageNum;
      this.load();
    },
  },
}
</script>


<style>
.headerBg {
  background: #eee !important;
}
</style>