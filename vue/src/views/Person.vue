<template>
  <el-card style="width: 500px;">
    <el-form label-width="80px" size="small">
      <el-form-item label="用户名">
        <el-input v-model="form.username" disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确认修改</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="$router.back()">返 回</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
// import {serverIp} from "../../public/config";

export default {
  name: "Person",
  data() {
    return {
      // serverIp: serverIp,
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getUser().then(res => {
      console.log(res)
      this.form = res
    })
  },
  methods: {
    async getUser() {
      return (await this.request.get("/user/username/" + this.user.username)).data
    },
    save() {
      this.request.post("/user/save", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          // 触发父级更新User的方法
          this.$emit("refreshUser")
          this.getUser().then(res=>{
            res.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user",JSON.stringify(res))
          })
          this.$router.back()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
  }
}
</script>

<style>
</style>
