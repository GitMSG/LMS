<template>
    <div id="userInfo">
        <span>
        <form @submit.prevent="changeUserRole" class="role-form">
                <span class="role-username">{{username}}</span>
                <select v-model="user.role" class="role-select">
                    <option value="user" v-if="this.$props.currentRole != 'user'">User</option>
                    <option value="admin" v-if="this.$props.currentRole != 'admin'">Admin</option>
                </select>
            <button type="submit" class="role-submit">Submit Change</button>
        </form>
        </span>
    </div>
</template>

<script>
import auth from '@/auth.js'

export default {
    props: {
        id: Number,
        username: String,
        currentRole: String
    },
    data() {
        return {
            user: {
                username: this.$props.username,
                role: '',
                id: this.$props.id
            }
        }
    },
    methods: {
        changeUserRole(){
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/changeUserRole`, {
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