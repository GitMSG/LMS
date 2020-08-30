<template>
  <div id="login-page" :style="image">
    <div class="loading" v-if="isLoading">
      <img class="loading-image" src="@/assets/TE_bur2.gif" />
    </div>
    <div id="login" class="login-text-center">
      <form @submit.prevent="login">
        <div
          class="alert alert-danger"
          role="alert"
          v-if="invalidCredentials"
        >Invalid email and password!</div>
        <label for="email" class="label">Email</label>
        <input
          type="text"
          id="email"
          class="login-form-control"
          placeholder="Email"
          v-model="user.email"
          required
          autofocus
        />
        <br />
        <label for="password" class="label">Password</label>
        <input
          type="password"
          id="password"
          class="login-form-control"
          placeholder="Password"
          v-model="user.password"
          required
        />
        <button type="submit" class="login-submit">Sign In</button>
      </form>
    </div>
  </div>
</template>

<script>
import auth from "../auth";

export default {
  name: "login",
  components: {},
  data() {
    return {
      isLoading: true,
      user: {
        email: "",
        password: "",
      },
      image: {
        background: `url(${require("@/assets/TE_LMS2.png")}) no-repeat`,
        backgroundPosition: "center",
      },
      invalidCredentials: false,
    };
  },
  created() {
    setTimeout(() => {
      this.isLoading = false;
    }, 750);
  },
  methods: {
    login() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/login`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.user),
      })
        .then((response) => {
          if (response.ok) {
            return response.text();
          } else {
            this.invalidCredentials = true;
          }
        })
        .then((token) => {
          if (token != undefined) {
            if (token.includes('"')) {
              token = token.replace(/"/g, "");
            }
            auth.saveToken(token);
              this.$router.push({ path: "/profile", params: this.email });
              this.$router.go();                          
          }
        })
        .catch((err) => console.error(err));
    },
  },
};
</script>

<style>
#login-page {
  height: 100vh;
  display: flex;
}
#login {
  min-width: 415px;
  max-width: 35%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
  color: white;
  background-color: rgba(32, 33, 36, 0.9);
  border-radius: 4px;
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
  text-align: right;
  padding: 20px;
}

.label {
  padding: 20px;
}
input {
  width: 250px;
}
.login-form-control {
  align-content: center;
  border-radius: 2px;
  padding: 5px;
  margin: 10px 0;
  border: none;
  text-align: center;
}
.login-tag {
  font-weight: lighter;
  color: white;
  padding-top: 5px;
}
.loading {
  background-color: rgba(32, 33, 36, 1);
  margin: 0px;
  width: 100vw;
  height: 100vh;
  position: absolute;
  z-index: 1000;
}
.loading-image {
  margin-top: 20%;
  left: 50%;
}

.login-submit {
  background-color: #336699;
  border: none;
  color: black;
  padding: 5px 20px;
  margin: auto;
  margin-top: 20px;
  text-align: center;
  text-decoration: none;
  display: block;
  font-size: 20px;
  border-radius: 4px;
}
.login-submit:hover {
  background-color: white;
  border: 1px solid #336699;
  color: #336699;
}
</style>
