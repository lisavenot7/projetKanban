<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import Bandeau from "../components/BandeauUtilisateur.vue"

import {ref,computed} from 'vue'

</script>

<template>
<Navbar />
  <div class="home">
    <Bandeau />
  </div>
</template>
