<template>
  <div id="login" class="login-text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="login-tag">Please Sign In</h1>
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div class="alert alert-success" role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="login-form">
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="login-form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="login-form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <router-link :to="{ name: 'register' }" class="new-account-router">Need an account?</router-link>
      <button type="submit" class="login-submit">Sign in</button>
      </div>
    </form>
  </div>
</template>

<script>
import auth from '../auth';

export default {
  name: 'login',
  components: {},
  data() {
    return {
      user: {
        username: '',
        password: '',
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/login`, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
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
              token = token.replace(/"/g, '');
            }
            auth.saveToken(token);
            this.$router.push('/');
            this.$router.go();
          }
        })
        .catch((err) => console.error(err));
    },
  },
};
</script>

<style>
/* .login-form{
  text-align: center;
  display: block;
} */
.sr-only{
  display:inline-block;
  font-size: 20px;
}
.login-form-control{
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
}
.login-tag{
  font-size: 50px;
  margin-top: 45px;
  font-family: fantasy;
  color: rgb(240, 201, 73);
  padding-top: 5px;
}
.login-text-center{
  background: dimgray;
  padding-bottom: 100%;
}
.login-submit{
    background-color: rgb(240, 201, 73);
    border: none;
    color: black;
    padding: 10px 40px;
    text-align: center;
    text-decoration: none;
    display: block;
    font-size: 20px;
    border-radius: 8px;
    transition-duration: 0.4s;
    border: 2px solid rgb(240, 201, 73);
}
.login-submit:hover{
    background-color: white;
    border: 2px solid rgb(240, 201, 73);
}
.new-account-router{
  display: block;
  color: rgb(240, 201, 73);
  margin-bottom: 8px;
}

</style>
