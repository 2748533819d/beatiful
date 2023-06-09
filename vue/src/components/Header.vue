<template>
  <div style=" line-height: 60px;display: flex">
    <div style="flex : 1">
      <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapse"></span>
    </div>
    <el-dropdown style="width: 150px;cursor: pointer;text-align: right">
      <div style="display: inline-block">
        <span>{{ user.nickname }}<i class="el-icon-arrow-down" style="margin-left: 5px"></i></span>
      </div>

      <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
        <el-dropdown-item style="font-size: 14px;padding: 5px 0">
          <router-link to="/person">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px;padding: 5px 0">
          <router-link to="/password">修改密码</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px;padding: 5px 0">
          <span style="text-decoration: none" @click="logout"> 退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import router, {resetRouter} from "@/router";

export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    user: Object,
  },
  data(){
    return {
    }
  },
  methods: {
    collapse() {
      this.$emit("asideCollapse")
    },
    logout(){
      localStorage.removeItem("user")
      localStorage.removeItem("menus")
      router.push("/login")
      resetRouter();
      this.$message.success("退出成功");
    }
  }
}
</script>

<style scoped>

</style>