<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import { useRouter } from "vue-router"
import { computed, ref, onMounted } from 'vue'

const router = useRouter()


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
  error.value = ""

  if (mdp.value ==="" 
    || mdpConfirm.value==="" 
    || nom.value==="" 
    || prenom.value==="" 
    || mail.value==="") {
    error.value = "Les champs doivent être remplis"
    return
  }

  if (mdp.value !== mdpConfirm.value) {
    error.value = "Les mots de passe ne correspondent pas"
    return
  }
  
  const registerUserDto = {
    email: mail.value,
    password: mdp.value,
    prenom:prenom.value,
    nom:nom.value
  }
  try {
    const response = await fetch("http://localhost:10056/auth/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(registerUserDto)
    })

    if (!response.ok) {
      const data = await response.json()
      error.value = data.message || "Un autre compte existe déjà avec ce mail"
      return
    }

    const data = await response.json()
    console.log("Compte créé:", data)
    router.push("/admin/utilisateurs")
  } catch (err) {
    console.error(err)
    error.value = "Impossible de contacter le serveur"
  }
}

const admin = ref(false)

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
      <h1>Ajouter un utilisateur</h1>
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
      <p v-if="error" class="error">&nbsp{{ error }}</p>
    </div>
  </div>
</template>

