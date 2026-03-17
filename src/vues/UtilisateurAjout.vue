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

const isValidEmail = (email) => {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
}

const isStrongPassword = (password) => {
  return /^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$/.test(password)
}

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

  if (!isValidEmail(mail.value)) {
    error.value = "Le format du mail est invalide"
    return
  }

  if (!isStrongPassword(mdp.value)) {
  error.value = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un caractère spécial"
  return
}

  if (mdp.value !== mdpConfirm.value) {
    error.value = "Les mots de passe ne correspondent pas"
    return
  }
  let adm

  if(admin.value){
    adm=1
  }  else {
    adm=0
  }
  
  
  const registerUserDto = {
    email: mail.value,
    password: mdp.value,
    prenom:prenom.value,
    nom:nom.value,
    isAdmin:adm
  }
  try {
    const response = await fetch("http://localhost:10056/comptes", {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(registerUserDto)
      
    })
    const text = await response.text()
    let data = null
    try {
      data = text ? JSON.parse(text) : null
    } catch (err) {
      console.warn("Impossible de parser le JSON :", text)
    }

    if (!response.ok) {
      error.value = (data && data.message) || `Erreur serveur ${response.status}`
      return
    }

    console.log("Compte créé :", data)
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

const showPassword = ref(false)
const togglePassword = () => {
  showPassword.value = !showPassword.value
}
const showPassword2 = ref(false)
const togglePassword2 = () => {
  showPassword2.value = !showPassword2.value
}
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>Ajouter un utilisateur</h1>
      <input v-model="nom" placeholder="Nom" />
      <input v-model="prenom" placeholder="Prenom" />
      <input v-model="mail" placeholder="Mail" />
      <div class="input-password">
          <input
            v-model="mdp"
            :type="showPassword ? 'text' : 'password'"
            placeholder="Mot de passe"
          />
          <span class="eye" @click="togglePassword">
            <svg v-if="!showPassword" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" stroke="black" fill="none" stroke-width="2">
              <path d="M1 12C3.5 7 7.5 4 12 4s8.5 3 11 8c-2.5 5-6.5 8-11 8S3.5 17 1 12z"/>
              <circle cx="12" cy="12" r="3"/>
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" stroke="black" fill="none" stroke-width="2">
              <path d="M1 1l22 22"/>
              <path d="M17.94 17.94A10.94 10.94 0 0112 20C7.5 20 3.5 17 1 12a21.86 21.86 0 015.06-6.94"/>
            </svg>
          </span>
        </div>
        <div class="input-password">
          <input
            v-model="mdpConfirm"
            :type="showPassword2 ? 'text' : 'password'"
            placeholder="Confirmer le mot de passe"
          />
          <span class="eye" @click="togglePassword2">
            <svg v-if="!showPassword2" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" stroke="black" fill="none" stroke-width="2">
              <path d="M1 12C3.5 7 7.5 4 12 4s8.5 3 11 8c-2.5 5-6.5 8-11 8S3.5 17 1 12z"/>
              <circle cx="12" cy="12" r="3"/>
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" stroke="black" fill="none" stroke-width="2">
              <path d="M1 1l22 22"/>
              <path d="M17.94 17.94A10.94 10.94 0 0112 20C7.5 20 3.5 17 1 12a21.86 21.86 0 015.06-6.94"/>
            </svg>
          </span>
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

