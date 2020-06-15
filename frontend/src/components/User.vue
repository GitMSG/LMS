<template>
    <div id="userInfo">
        <span>
        <form @submit.prevent="changeUserpermission" class="permission-form">
                <span class="permission-email">{{email}}</span>
                <select v-model="user.permission" class="permission-select">
                    <option value="user" v-if="this.$props.currentpermission != 'user'">User</option>
                    <option value="admin" v-if="this.$props.currentpermission != 'admin'">Admin</option>
                </select>
            <button type="submit" class="permission-submit">Submit Change</button>
        </form>
        </span>
    </div>
</template>

<script>
import auth from '@/auth.js'

export default {
    props: {
        id: Number,
        email: String,
        currentpermission: String
    },
    data() {
        return {
            user: {
                email: this.$props.email,
                permission: '',
                id: this.$props.id
            }
        }
    },
    methods: {
        changeUserpermission(){
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/changeUserPermission`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + auth.getToken(),     
                },
                body: JSON.stringify(this.user),
                })
                .then((response) => {
                    if(response.ok) {
                        this.$router.push({name: 'confirmation', params: {confirmationObject: 'User'}});
                    }
                    // return response.json();
                })
                .catch((err) => console.error(err));
        }
    }
}
</script>