<template>
  <div style="height: 100%;">
    <el-container style="height: 100%;">
      <el-aside :width="sideWidth+'px'"
                style="background-color: rgb(238, 241, 246);height: 100%;box-shadow:2px 0 6px rgb(0 21 41/35%) ">
        <Aside :isCollapse="isCollapse"/>
      </el-aside>
      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapseBtnClass="collapseBtnClass" @asideCollapse="collapse" :user="user"/>
        </el-header>
        <el-main>
          <!--          表示当前页面子路由会在router-view里面展示-->
          <router-view @refreshUser="getUser"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  name: 'Manage',
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      user: {}

    }
  },
  components: {
    Aside,
    Header
  },
  created() {
    this.getUser();
  },
  methods: {
    collapse() { //点击收缩按钮触发
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.sideWidth = 64;
        this.collapseBtnClass = 'el-icon-s-unfold';
      } else {
        this.sideWidth = 200;
        this.collapseBtnClass = 'el-icon-s-fold';
      }
    },
    getUser() {
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : "";
      return this.request.get("/user/username/" + username).then(res => {
        this.user = res.data;
      })
    },
  }
}
</script>
