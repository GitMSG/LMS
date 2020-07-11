<template>
    <div id="training-form">
    <h1>Add Training</h1>
    <form class="new-training-form" @submit.prevent="createTraining">
        <div class="training-card">
           
                        <span class="label">Name </span><input type="text" v-model="training.name"><br/>
                  
                        <span class="label">Provider </span><input type="text" v-model="training.provider"><br/>
                  
                        <span class="label">Topic/Description </span><input type="text" v-model="training.topic"><br/>
                 
                        <span class="label">Date </span><input type="date" v-model="training.date"><br/>

                        <span class="label">Compliance </span><input type="checkbox" v-model="training.isCompliance" id="isCompliance"><br/>
                  
                        <span class="label">Hours</span><input  :bind="training.minutes" @input="handleInput($event.target.value)"><br/>
                  
                    <div class="dropzone-div">
                        <span class="training-label">Enter Image of Certificate </span>
                        <vue-dropzone id="dropzone" 
                                    :options="dropzoneOptions" 
                                    v-on:vdropzone-sending="addFormData" 
                                    @vdropzone-success="getSuccess" 
                                    v-model="training.proof">
                        </vue-dropzone>
                    </div>
        </div>
        <button class="training-button" type="submit" >Add</button>
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
                
                training: {
                    name: '',
                    provider: '',
                    topic: '',
                    date: '',
                    isCompliance: false,
                    proof: '',
                    minutes: null,
                },
                
                dropzoneOptions: {
                    url: `https://api.cloudinary.com/v1_1/goshorn/image/upload` ,
                    thumbnailWidth: 150,
                    maxFilesize: 2.0,
                    acceptedFiles: ".jpg, .jpeg, .png, .gif"
                },
                post: {
                    proof: '',
                    
                },
                             
            }
        },
        methods:{
            handleInput(value){
                this.training.minutes = value * 60;
            },
            createTraining() {
              
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/addTraining`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + auth.getToken(),     
                },
                body: JSON.stringify(this.training)
                      
                })
                .then((response) => {
                    if(response.ok) {
                       
                        this.$router.push({name: 'profile'/* , params: {confirmationObject: 'Brewery'} */});
                        this.$router.go();
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
                this.training.proof = response.secure_url;
            }
        },
        created(){
           
        }
        
    }
</script>

<style scoped>
#training-form{
    
    height:80vh;
    color:white;
    padding:30px;
    /* font-family: 'roboto condensed'; */
    }
.training-card{
    display: block;
    max-width:500px;
    margin:auto;
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
h1{
    font-weight:lighter;
}
.training-button{
    padding:5px;
    margin:5px;
}

</style>