<template>
  <div
    ref="toast"
    :class="{ 'bg-success': type === 'SUCCESS', 'bg-danger': type === 'ERROR' }"
    class="overlay-toast toast align-items-center text-white border-0 position-fixed me-4 mt-4 end-0 p-3 shadow"
    role="alert"
    aria-live="assertive"
    aria-atomic="true"
  >
    <div class="d-flex">
      <div class="toast-body">
        <strong>{{ message }}</strong>
      </div>
      <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
    </div>
  </div>
</template>

<script lang="ts" src="./Toast.component.ts"></script>

<style>
.overlay-toast {
  z-index: 1;
}
</style>
