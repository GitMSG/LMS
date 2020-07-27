<template>
    <div id="profile-form">
    
    <form class="new-profile-form" @submit.prevent="createProfile">
        <h1>Create Your Profile</h1>
        <div class="profile-card">
           
                    
                        <span class="label">First Name </span><input type="text" v-model="profile.firstName"><br/>
                  
                        <span class="label">Last Name </span><input type="text" v-model="profile.lastName"><br/>

                         <span class="label">Select Location </span>
                        <select v-model="profile.campusShortCode" class="dropdown">
                        <!-- <option disabled value="">Select Location</option> -->
                        <option value="CLE" >CLE</option>
                        </select><br/>
                 
                        <span class="label">Role </span><input type="text" v-model="profile.role"><br/>
                  
                        <span class="label">Start Date </span><input type="date" v-model="profile.startDate"><br/>
                  
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
                    campusShortCode: '',
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
                       
                        this.$router.push({name: 'profile'/* , params: {confirmationObject: 'Brewery'} */});
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
.new-profile-form{
    margin:auto;
    height:100vh;
    color:white;
    padding:30px;
   
    }
#profile-form{
    display:flex;
    flex-direction: column;
    justify-content: center;
    margin:auto;

}
.profile-card{
    margin:auto;
    max-width:500px;
    background-color:rgba(32, 33, 36, .7 );
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),0 1px 10px 0 rgba(0, 0, 0, 0.12);
    text-align:right;
}
.dropzone-div{
    text-align:center;
    width:100%;
    margin-top:20px;
    
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
    font-weight:lighter;
}
.profile-button{
    padding:5px;
    margin:5px;
}

</style>