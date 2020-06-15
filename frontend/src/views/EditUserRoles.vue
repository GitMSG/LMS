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
      fetch('http://localhost:8080/AuthenticationApplication/api/users',
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
.permission-page{
  background: dimgray;
 
}
.permission-header{
 
  font-family: fantasy;
  color: rgb(240, 201, 73);

}
.permission-item{
    list-style-type: none;
    border: 2px solid black;
    width: 45%;
    margin-bottom: 5px;
    background: rgb(75, 75, 75);
}
.permission-email{
    color: rgb(240, 201, 73);
}
.permission-select{
    float:right;
}
.permission-submit{
    float: right;
}
/* .permission-form{
    padding: 2px 5px;
}
 */
</style>