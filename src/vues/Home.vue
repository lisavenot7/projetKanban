<script setup>
import Navbar from "../components/Navbar.vue"
import Bandeau from "../components/Bandeau.vue"

import {ref,computed} from 'vue'

</script>

<template>
<Navbar />
  <div class="home">
    <Bandeau />
  </div>
</template>

<style >
html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  background-color: #0F171E;
}

.home {
  width: 100%;
  min-height: 100vh;
  background-color: #0F171E;
}
</style>