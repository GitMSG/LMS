<template>
    <div>
    <form class="new-profile-form" @submit.prevent="createProfile">
        <div class="profile-form">
            <div class="row">
                <div class="column-one">
                    <div class="profileInput">
                        <span class="label">First Name: </span><input type="text" v-model="profile.firstName">
                    </div>
                    <div class="profileInput">
                        <span class="label">Last Name: </span><input type="text" v-model="profile.lastName">
                    </div>
                    <div class="profileInput">
                        <span class="label">Role: </span><input type="text" v-model="profile.role">
                    </div>
                    <div class="profileInput">
                        <span class="label">Start Date: </span><input type="text" v-model="profile.startDate">
                    </div>
                    <div class="profileInput">
                        <span class="label">End Date: </span><input type="text" v-model="profile.endDate">
                    </div>
                </div>
                <div class="imageDiv">
                    <div class="profileInput">
                        <span class="label">Profile Picture: </span><vue-dropzone id="dropzone" :options="dropzoneOptions" v-on:vdropzone-sending="addFormData" @vdropzone-success="getSuccess" v-model="profile.picture"></vue-dropzone>
                    </div>
                </div>
            </div>
        </div>
        <button class="profile-button" type="submit" :disabled="!isValidForm" >Add Profile</button>
    </form>

    </div>
</template>

<script>
import auth from '@/auth.js'
import vue2Dropzone from 'vue2-dropzone'
import 'vue2-dropzone/dist/vue2Dropzone.min.css'
    export default {
         components: {
        vueDropzone: vue2Dropzone
        },
        data() {
            return {
                profile: {
                    firstName: '',
                    lastName: '',
                    role: '',
                    startDate: '',
                    endDate: '',
                    image: ''
                },
                dropzoneOptions: {
                    url: 'https://api.cloudinary.com/v1_1/brillhart/image/upload' /*this came with dropzone example --> 'https://httpbin.org/post'*/,
                    thumbnailWidth: 150,
                    maxFilesize: 2.0,
                    acceptedFiles: ".jpg, .jpeg, .png, .gif"
                },
                post: {
                    image: '',
                    caption: ''
                }
            }
        },
        methods:{
            createProfile() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/addUserProfile`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + auth.getToken(),     
                },
                body: JSON.stringify(this.profile),
                })
                .then((response) => {
                    if(response.ok) {
                       
                       // this.$router.push({name: 'confirmation', params: {confirmationObject: 'Brewery'}});
                    }
                    //return response.json();
                })
                .catch((err) => console.error(err));
            },
             addFormData(file, xhr, formData) {
                formData.append("api_key", "652428861445774");
                formData.append("upload_preset", "brillhartpreset"); // my upload preset
                formData.append("timestamp", (Date.now() / 1000) | 0);
                formData.append("tags", "vue-app");
            },
            getSuccess(file, response) {
                this.profile.picture = response.secure_url;
            }
        }
        
    }
</script>

<style scoped>

</style>