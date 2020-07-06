<template>
    <div id="profile-form">
    <h1>Creat Your Profile</h1>
    <form class="new-profile-form" @submit.prevent="createProfile">
        <div class="profile-card">
           
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
                        <span class="label">Profile Picture: </span>
                        <vue-dropzone id="dropzone" 
                                    :options="dropzoneOptions" 
                                    v-on:vdropzone-sending="addFormData" 
                                    @vdropzone-success="getSuccess" 
                                    v-model="profile.profilePic">
                        </vue-dropzone>
                    </div>
        </div>
        <button class="profile-button" type="submit" >Add Profile</button>
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
                    profilePic: '',
                    
                    
                },
                dropzoneOptions: {
                    url: `https://api.cloudinary.com/v1_1/goshorn/image/upload` ,
                    thumbnailWidth: 150,
                    maxFilesize: 2.0,
                    acceptedFiles: ".jpg, .jpeg, .png, .gif"
                },
                post: {
                    profilePic: '',
                    
                },
                             
            }
        },
        methods:{
            createProfile() {
               
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/createProfile`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + auth.getToken(),     
                },
                body: JSON.stringify(this.profile)
                      
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
                formData.append("api_key", `${process.env.CLOUD_KEY}`);
                formData.append("upload_preset", "goshornpreset"); // my upload preset
                formData.append("timestamp", (Date.now() / 1000) | 0);
                formData.append("tags", "vue-app");
            },
            getSuccess(file, response) {
                this.profile.profilePic = response.secure_url;
            }
        },
        created(){
           
        }
        
    }
</script>

<style scoped>
#profile-form{
    width:90vw;
    height:90vh;
    color:white;
    padding-top:50px;
    }
.profile-card{
    border:1px solid black;
    display: block;
    max-width:450px;
    margin:auto;
}    
#dropzone{
    width:200px;
    height:200px;
}

</style>