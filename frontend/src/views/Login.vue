<template>
<div>
<h1 class="login-tag">Sign In</h1>
  <div id="login" class="login-text-center">
    <form @submit.prevent="login">
      
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid email and password!
      </div>
      <div class="alert alert-success" role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
     
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
      <br/>
      <label for="password" class="label">Password</label>
      <input
        type="password"
        id="password"
        class="login-form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <!-- <router-link :to="{ name: 'register' }" class="new-account-router">Need an account?</router-link> -->
      <button type="submit" class="login-submit">Sign In</button>
      
    </form>
  </div>
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
        email: '',
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
#login {
  min-width:415px;
  max-width:50%;
  display: flex;
  justify-content:center;
  align-items:center;
  margin:auto;
  color:white;
  background-color:#202124;
  border-radius:4px;
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
  text-align:right;
  padding:20px;
}
.label {
  padding:20px;
}
input {
  width:250px;
}
.login-form-control{
  align-content:center;
  border-radius:2px;
  padding: 5px ;
  margin: 10px 0;
  border:none;
  text-align:center;
}
.login-tag{
  
  font-weight:lighter;
  color: #202124;
  padding-top: 5px;
}

.login-submit{
  
    background-color: #336699;
    border: none;
    color: black;
    padding: 5px 20px;
    margin:auto;
    margin-top:20px;
    text-align: center;
    text-decoration: none;
    display: block;
    font-size: 20px;
    border-radius: 4px;
   
   
}
.login-submit:hover{
    background-color: white;
    border: 1px solid #336699;
    color:#336699;
}
/* .new-account-router{
  display: block;
  color: rgb(240, 201, 73);
  margin-bottom: 8px;
} */

</style>
