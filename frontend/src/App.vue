<template>
<body :style="image">
  <nav-bar v-if="aUser != null" />
  <admin-nav v-if="this.userRole == 'admin'" />
  <div class="app">
    <router-view />
  </div>
</body>
</template>

<script>
import auth from "@/auth.js";
import NavBar from "@/components/NavBar.vue";
import AdminNav from "@/components/AdminNav.vue";
export default {
  components: {
    NavBar,
    AdminNav,
  },
  data() {
    return {
      aUser: auth.getUser().sub,
      userRole: auth.getUser().rol,
      image: {
        background: `url(${require("@/assets/TE_LMS2.png")})no-repeat`,
        backgroundPosition: "center",
        backgroundRepeat: "no-repeat",
        backgroundAttachment: "fixed",
      },
    };
  },
};
</script>

<style>
body {
  text-align: center;
  margin: 0;
  font-family: "Roboto Condensed", sans-serif;
  background: rgba(37, 85, 112, 1);
}
</style>