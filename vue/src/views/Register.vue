<template>
  <div class="wrapper">
    <div
        style="margin: 100px auto; background-color: #fff; width: 350px; height: 400px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 40px"><b>趣 书 阁</b></div>
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form :model="user" :rules="rules" ref="userForm">
          <el-form-item prop="nickname">
            <el-input placeholder="请输入昵称" size="medium" prefix-icon="el-icon-user" v-model="user.nickname"></el-input>
          </el-form-item>
        </el-form>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请再次输入密码" size="medium" prefix-icon="el-icon-lock" show-password
                    v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登陆</el-button>
          <el-button type="primary" size="small" autocomplete="off" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {setRoutes} from "@/router";

export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 4, message: '用户名长度要大于或等于4', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 8, message: '密码长度要大于或等于8', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '请再次输入密码', trigger: 'blur'},
          {min: 8, message: '密码长度要大于或等于8', trigger: 'blur'}
        ],
        nikename:[
          {required: true, message: '请输入你的昵称', trigger: 'blur'},
          {min: 1 ,max:8, message: '密码长度要大于或等于8', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs['userForm'].validate((valid)=>{
        if(valid) {
          if(this.user.password !== this.user.confirmPassword) {
            this.$message.error("两次输入密码不一致");
            return false;
          }
          this.request.post("/user/register",this.user).then(res =>{
            if(res.code === '200'){
              console.log(this.user)
              this.$message.success("注册成功");
              this.$router.push('/login')
            } else{
              this.$message.error(res.msg);
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
  overflow: hidden;
}
</style>
