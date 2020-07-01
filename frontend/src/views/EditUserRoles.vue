<template>
    <div id="editUsersPage" class="permission-page">
        <h2 class="permission-header">User Info</h2>
        <ul class="permission-group">
            <li v-for="user in users" v-bind:key="user.id" class="permission-item">
                <User :email = user.email :currentpermission = user.permission :id = user.id />
            </li>
        </ul>
    </div>
</template>

<script>
import auth from '@/auth.js'
import User from '@/components/User.vue'

export default {
    components: {
        User
    },
    data() {
        return {
            users: []
        }
    },

    created() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/users`,
        {
        method: 'GET',
        headers: {
        Authorization: 'Bearer ' + auth.getToken(),
        },
        credentials: 'same-origin',
        })
        .then ((response) => {     
            return response.json()
            })  
        .then ((theData) => {   
        this.users = theData;
        })
        .catch((err) => {
        console.log(err);
        })
    }
}
</script>

<style>
#editUsersPage{
  width:100vw;
  height:100vh;
}
.permission-header{
 
  font-family: 'roboto condensed';
  font-weight:lighter;
  color: #add8e6;

}
.permission-item{
    list-style-type: none;
    border: none;
    width: 60%;
    margin: 20px 10px;
   
}
.permission-email{
    color: white;
    margin:20px;
}
.permission-submit{
    float: right;
}

 
</style>