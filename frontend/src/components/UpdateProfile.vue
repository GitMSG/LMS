<template>
    <div id="profile-form">
    <div class="new-profile-form" >
        <h1>Edit Profile</h1>
        <div class="profile-card">
            <div class="text-inputs">
            <span class="label">First Name </span><input type="text" v-model="profile.firstname"><br/>
            <span class="label">Last Name </span><input type="text" v-model="profile.lastname"><br/>
            <span class="label">Role </span><input type="text" v-model="profile.role"><br/>
            <span class="label">Start Date </span><input type="date" v-model="profile.startDate"><br/>
            <span class="label">Select Location </span>
            <select v-model="profile.campusShortCode" class="dropdown">
            <option value="CLE" >CLE</option>
            <option value="CBUS" >CBUS</option>
            <option value="CINCY" >CINCY</option>
            <option value="PGH" >PGH</option>
            <option value="PHL" >PHL</option>
            </select><br/>
            </div>
            <div class="dropzone-div">
                <span class="profile-label">Profile Picture </span>
                <vue-dropzone id="dropzone" 
                            :options="dropzoneOptions" 
                            v-on:vdropzone-sending="addFormData" 
                            @vdropzone-success="getSuccess" 
                            v-model="profile.profilePic">
                </vue-dropzone>
            </div>
        </div>
        <button class="profile-button" type="submit" @click="createProfile" >Update</button>
        <button class="profile-button" @click="$emit('toggle-update-profile')" >Cancel</button>
    </div>

    </div>
</template>

<script>
import auth from '@/auth.js'
import vue2Dropzone from 'vue2-dropzone'
import 'vue2-dropzone/dist/vue2Dropzone.min.css'
    export default {
        props:{
             profile: {
                    firstname: '',
                    lastname: '',
                    campusShortCode: '',
                    role: '',
                    startDate: '',
                    profilePic: '',
                },
        },
        name:"UpdateProfile",
        components: {
        vueDropzone: vue2Dropzone
        },
        data() {
            return {
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
                                this.$router.go()
                            }
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
        
    }
</script>

<style scoped>
.new-profile-form{
     background-color:rgba(32, 33, 36, .7 );
     box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
    color:white;
    padding:10px;
    max-width:75%;
   margin:auto;
    }
#profile-form{
    min-height:100vh;
}
.profile-card{
    display: flex;
    justify-content: center;
    align-content: center;
   /*  margin:auto; */
    
    text-align:right;
}
.text-inputs{
    margin-top: 20px;
}
.dropzone-div{
    text-align:center;
   /*  padding-bottom: 20px; */
   
    
}    
#dropzone{
    margin:auto;
    width:250px;
    height:250px;
    border:none;
    border-radius:2px;
}
.label{
    margin:5px;
}
input{
    padding:5px;
    margin:10px;
    border:none;
    border-radius:2px;    
}
.dropdown{
    padding:5px;
    margin:10px;
    border:none;
    border-radius:2px;   
}
h1{
    margin-top:10px;
    font-weight:lighter;
}
.profile-button{
  background-color: rgba(36, 104, 143, 1);
  color: white;
  padding: 5px 20px;
  max-width: 25%;
  text-decoration: none;
  font-size: 16px;
  border-radius: 4px;
  border: none;
  margin: 10px;
  cursor: pointer;
}

</style>