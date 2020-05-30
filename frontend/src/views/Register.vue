<template>
  <div id="register" class="register-text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="register-tag">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        There were problems registering this user.
      </div>
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="register-form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="register-form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <input
        type="password"
        id="confirmPassword"
        class="register-form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <router-link :to="{ name: 'login' }" class="login-router">
        Have an account?
      </router-link>
      <button class="register-submit" type="submit">
        Create Account
      </button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
    };
  },
  methods: {
    register() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/register`, {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.user),
      })
        .then((response) => {
          if (response.ok) {
            this.$router.push({ path: '/login', query: { registration: 'success' } });
          } else {
            this.registrationErrors = true;
          }
        })

        .then((err) => console.error(err));
    },
  },
};
</script>

<style>
.sr-only{
  display:inline-block;
  font-size: 20px;
}
.register-form-control{
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
}
.register-tag{
  font-size: 50px;
  margin-top: 45px;
  font-family: fantasy;
  color: rgb(240, 201, 73);
  padding-top: 5px;
}
.register-text-center{
  background: dimgray;
  padding-bottom: 100%;
}
.register-submit{
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
.register-submit:hover{
    background-color: white;
    border: 2px solid rgb(240, 201, 73);
}
.login-router{
  display: block;
  color: rgb(240, 201, 73);
  margin-bottom: 8px;
}
</style>
