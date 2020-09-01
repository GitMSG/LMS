<template>
  <div id="training-form">
    <form class="new-training-form" @submit.prevent="createTraining">
      <h1>Add Training</h1>
      <div class="training-card">
        <span class="label">Name</span>
        <input type="text" v-model="training.name" placeholder="Training Name.." autofocus />
        <br />

        <span class="label">Provider</span>
        <input type="text" v-model="training.provider" placeholder="Provider.."/>
        <br />

        <span class="label">Topic/Description</span>
        <input type="text" v-model="training.topic" placeholder="Topic.."/>
        <br />

        <span class="label">Date</span>
        <input type="date" class="short-input" v-model="training.date" />
        <br />

        <span class="label">Select Training Type</span>
        <select v-model="type" class="short-input" @change="showType($event.target.value)">
          <option value="complianceTime">Compliance</option>
          <option value="electiveTime">Elective</option>
        </select><br />
        <div v-if="compliance === true">
          <span class="label">Compliance Hours</span>
          <input
            :bind="training.complianceTime"
            @input="handleInputCompliance($event.target.value)"
            class="short-input"
            placeholder="Hours.."
          /><br />
        </div>
        <div v-if="compliance === false">
          <span class="label">Elective Hours</span>
          <input 
            :bind="training.electiveTime" 
            @input="handleInputElective($event.target.value)"
            class="short-input"
            placeholder="Hours"
             /><br />
        </div>

        <span class="label">Select Proof Type</span>
        <select v-model="proof" class="short-input" @change="showProof($event.target.value)">
          <option value="photo">Upload Photo</option>
          <option value="link">Paste Link</option>
        </select>
        <br />

        <div v-if="link === true">
          <span class="label">Link</span>
          <input v-model="training.proof" placeholder="Paste A Link ..." />
          <br />
        </div>
        <div class="dropzone-div" v-if="dropzone === true">
          <span class="training-label">Enter Image of Certificate</span>
          <vue-dropzone
            id="dropzone"
            :options="dropzoneOptions"
            v-on:vdropzone-sending="addFormData"
            @vdropzone-success="getSuccess"
            v-model="training.proof"
          ></vue-dropzone>
        </div>
      </div>
      <button class="training-button" type="submit">Add</button>
    </form>
  </div>
</template>

<script>
import auth from "@/auth.js";
import vue2Dropzone from "vue2-dropzone";
import "vue2-dropzone/dist/vue2Dropzone.min.css";
export default {
  props: {
    profileId: Number,
  },
  components: {
    vueDropzone: vue2Dropzone,
  },
  data() {
    return {
      proof: "",
      type: "",
      compliance: null,
      dropzone: false,
      link: false,
      training: {
        name: "",
        provider: "",
        topic: "",
        date: "",
        proof: "",
        complianceTime: 0,
        electiveTime: 0,
      },

      dropzoneOptions: {
        url: `https://api.cloudinary.com/v1_1/goshorn/image/upload`,
        thumbnailWidth: 150,
        maxFilesize: 2.0,
        acceptedFiles: ".jpg, .jpeg, .png, .gif",
      },
      post: {
        proof: "",
      },
    };
  },
  methods: {
    showType(t) {
      if (t === "complianceTime") {
        this.compliance = true;
      } else {
        this.compliance = false;
      }
    },
    showProof(p) {
      if (p === "photo") {
        this.dropzone = true;
        this.link = false;
      }
      if (p === "link") {
        this.link = true;
        this.dropzone = false;
      }
    },
    handleInputCompliance(value) {
      this.training.complianceTime = value * 60;
    },
    handleInputElective(value) {
      this.training.electiveTime = value * 60;
    },
    createTraining() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/addTraining/${this.profileId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + auth.getToken(),
          },
          body: JSON.stringify(this.training),
        }
      )
        .then((response) => {
          if (response.ok) {
            //close()
            this.$router.push({ name: "profile" });
            this.$router.go();
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
      this.training.proof = response.secure_url;
    },
  },
};
</script>

<style scoped>
.new-training-form {
  margin: auto;
  color: white;
  padding: 0 px 30px;
  background-color: rgba(32, 33, 36, 0.7);
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
}
.training-card {
  display: block;
  max-width: 500px;
  margin: auto;
  text-align: right;
  
}
.dropzone-div {
  text-align: center;
  width: 100%;
  margin-top: 20px;
}
#dropzone {
  margin: auto;
  width: 250px;
  height: 250px;
  border: none;
  border-radius: 2px;
}
.label {
  margin: 5px;
}
input {
  padding: 5px;
  margin: 10px;
  border: none;
  border-radius: 2px;
}
.short-input{
  padding: 5px;
  margin: 10px;
  width: 125px;
  border: none;
  border-radius: 2px;
}
.dropdown {
  padding: 5px;
  margin: 10px;
  border: none;
  border-radius: 2px;
}
h1 {
  font-weight: lighter;
  margin: 30px 0px 0px 0px;
}
.training-button {
  background-color: rgba(36, 104, 143, 1);
  color: white;
  padding: 5px 20px;
  text-decoration: none;
  font-size: 16px;
  border-radius: 4px;
  border: none;
  margin: 10px;
  cursor: pointer;
  align-self: center;
}
</style>