<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import { useRouter ,useRoute} from "vue-router"
import { ref, onMounted } from 'vue'

import usersData from '../bdd/users.json'

const router = useRouter()
const route = useRoute()

const pseudoParam = route.params.id

const user = usersData.find(u => u.pseudo === pseudoParam)

const pseudo = ref(user ? user.pseudo : "")
const nom = ref(user ? user.nom : "")
const prenom = ref(user ? user.prenom : "")
const mail = ref(user ? user.mail : "")
const admin = ref(user ? Boolean(user.isAdmin) : false)

const showPasswordFields = ref(false)
const mdp = ref("")
const mdpConfirm = ref("")
const error = ref("")
const togglePasswordFields = () => {
  showPasswordFields.value = !showPasswordFields.value
}

const annuler = async () => {
  router.push("/admin/utilisateurs")
}
const valider = async () => {
  
  router.push("/admin/utilisateurs")
}

const token = localStorage.getItem("jwtToken")
onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "0") {
    router.push("/private")
  }
})
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1 v-if="user">Modifier l'utilisateur {{user.pseudo}}</h1>
      <h1 v-else>Modifier l'utilisateur </h1>
      <input v-model="pseudo" placeholder="Pseudo" />
      <input v-model="nom" placeholder="Nom" />
      <input v-model="prenom" placeholder="Prenom" />
      <input v-model="mail" placeholder="Mail" />
      
      <button class="boutonsNav" @click="togglePasswordFields">
        {{ showPasswordFields ? "Annuler le changement de mot de passe" : "Changer le mot de passe" }}
      </button>

      <div v-if="showPasswordFields" class="password-fields">
        <input v-model="mdp" type="password" placeholder="Mot de passe" />
        <input v-model="mdpConfirm" type="password" placeholder="Confirmer le mot de passe" />
      </div>

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
      <p v-if="error" class="error">&nbsp{{ error }}</p>
    </div>
  </div>
</template>

