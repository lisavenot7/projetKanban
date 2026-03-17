<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import { useRouter ,useRoute} from "vue-router"
import { ref, onMounted } from 'vue'

import usersData from '../bdd/users.json'

const router = useRouter()
const route = useRoute()

const idUser = route.params.id

const user = ref(null)

async function monProfil() {
  try {
    const response = await fetch(`http://localhost:10056/comptes/${idUser}`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      credentials: "include"
    })
    if (!response.ok) {
      console.error("Erreur récupération profil", response.status)
      return
    }

    const res = await response.json()
    user.value = res.data

    nom.value = user.value.nom
    prenom.value = user.value.prenom
    admin.value = user.value.cptIsAdmin === 1 

  } catch (err) {
    console.error("Impossible de récupérer le profil", err)
  }
}

const nom = ref("")
const prenom = ref("")
const admin = ref(false)

const showPasswordFields = ref(false)
const mdp = ref("")
const mdpConfirm = ref("")
const error = ref("")
const togglePasswordFields = () => {
  showPasswordFields.value = !showPasswordFields.value
}

const showPassword = ref(false)
const togglePassword = () => {
  showPassword.value = !showPassword.value
}
const showPassword2 = ref(false)
const togglePassword2 = () => {
  showPassword2.value = !showPassword2.value
}

const annuler = async () => {
  router.push("/admin/utilisateurs")
}

const isStrongPassword = (mdp) => {
  return /^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$/.test(mdp)
}

const valider = async () => {
  let registerUserDto

  if (nom.value==="" 
    || prenom.value==="") {
    error.value = "Les champs doivent être remplis"
    return
  }

  let adm
  if(admin.value){
    adm=1
  } else {
    adm=0
  }

  if(showPasswordFields.value){
    if (mdp.value ==="" 
      || mdpConfirm.value==="") {
      error.value = "Les champs mots de passes doivent être remplis"
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
    registerUserDto = {
      cptMdp: mdp.value,
      prenom:prenom.value,
      nom:nom.value,
      cptIsAdmin :adm
    }
    console.log("changement de mdp",registerUserDto)
  } else {
    registerUserDto = {
      prenom:prenom.value,
      nom:nom.value,
      cptIsAdmin :adm
    }
  }

  try {
    const response = await fetch(`http://localhost:10056/comptes/${idUser}`, {
      method: "PATCH",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(registerUserDto)
    })
    const data = await response.json()
    if (!response.ok) {
      error.value = data.message || "ERREUR LORS DE LA MODIFICATION"
      return
    }
    user.value = data.data
    router.push("/admin/utilisateurs")
  } catch (err) {
    console.error("Impossible de modifier votre profil", err)
    error.value = "Erreur serveur"
  }
}

const token = localStorage.getItem("jwtToken")
onMounted(() => {
  const adminDroit = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (adminDroit === "0") {
    router.push("/private")
  }
  monProfil()
})
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1 v-if="user">Modifier l'utilisateur {{user.pseudo}}</h1>
      <h1 v-else>Modifier l'utilisateur </h1>
      <input v-model="nom" placeholder="Nom" />
      <input v-model="prenom" placeholder="Prenom" />
      
      <button class="boutonsNav" @click="togglePasswordFields">
        {{ showPasswordFields ? "Annuler le changement de mot de passe" : "Changer le mot de passe" }}
      </button>

      <div v-if="showPasswordFields" >
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

