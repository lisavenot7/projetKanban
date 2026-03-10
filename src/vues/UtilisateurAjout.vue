<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import { useRouter } from "vue-router"
import { computed, ref } from 'vue'

const router = useRouter()


const pseudo = ref("")
const nom = ref("")
const prenom = ref("")
const mail = ref("")
const mdp = ref("")
const mdpConfirm = ref("")
const error = ref("")


const annuler = async () => {
  router.push("/admin/utilisateurs")
}
const valider = async () => {
  
  router.push("/admin/utilisateurs")
}

const admin = ref(false)
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>Ajouter un utilisateur</h1>
      <input v-model="pseudo" placeholder="Pseudo" />
      <input v-model="nom" placeholder="Nom" />
      <input v-model="prenom" placeholder="Prenom" />
      <input v-model="mail" placeholder="Mail" />
      <input v-model="mdp" type="password" placeholder="Mot de passe" />
      <input v-model="mdpConfirm" type="password" placeholder="Confirmer le mot de passe" />

        
      <div class="checkbox-container">
        <label class="checkbox-label">
          <input type="checkbox" v-model="admin" />
          <h3 :style="{ marginTop: '5px' }">Administrateur</h3>
        </label>
      </div>
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button> 
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
      <h2 v-if="error" class="error">&nbsp{{ error }}</h2>
    </div>
  </div>
</template>

