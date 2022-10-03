<template>
  <header class="jhlite-header">
    <div class="jhlite-header--slot">
      <a class="jhlite-logo" href="/">
        <img class="jhlite-logo--icon" src="../../../../content/JHipster-Lite-neon-blue.png" alt="JHipster bow tie" width="48" />
        <span class="jhlite-logo--text">JHipster lite</span>
      </a>
    </div>
    <div class="jhlite-header--slot -expand"></div>
    <div class="jhlite-header--slot">
      <nav aria-label="Main navigation">
        <ul class="jhlite-nav">
          <li class="jhlite-nav--item">
            <a class="jhlite-nav-item" href="landscape">
              <span class="jhlite-icon-text">
                <span class="jhlite-icon-text--icon">
                  <IconVue name="map" aria-label="Icon map" title="Module landscape" />
                </span>
                <span class="jhlite-icon-text--text">Landscape</span>
              </span>
            </a>
          </li>
          <li class="jhlite-nav--item">
            <a class="jhlite-nav-item" href="patches">
              <span class="jhlite-icon-text">
                <span class="jhlite-icon-text--icon">
                  <IconVue name="puzzle" aria-label="Icon puzzle" title="Patch list" />
                </span>
                <span class="jhlite-icon-text--text">Patches</span>
              </span>
            </a>
          </li>
          <li class="jhlite-nav--item">
            <a
              class="jhlite-nav-item"
              href="https://github.com/jhipster/jhipster-lite/blob/main/documentation/guides.md"
              target="_blank"
              rel="noopener"
            >
              <span class="jhlite-icon-text">
                <span class="jhlite-icon-text--icon">
                  <IconVue name="book" aria-label="Icon book" title="GitHub JHipster Lite documentation" />
                </span>
                <span class="jhlite-icon-text--text">Documentation</span>
              </span>
            </a>
          </li>
          <li class="jhlite-nav--item">
            <a class="jhlite-nav-item" href="./swagger-ui.html" target="_blank">
              <span class="jhlite-icon-text">
                <span class="jhlite-icon-text--icon">
                  <IconVue name="binoculars" aria-label="Icon binoculars" title="Swagger UI" />
                </span>
                <span class="jhlite-icon-text--text">Swagger UI</span>
              </span>
            </a>
          </li>
          <li class="jhlite-nav--item">
            <a class="jhlite-nav-item" href="https://www.jhipster.tech/" target="_blank" rel="noopener">
              <span class="jhlite-icon-text">
                <span class="jhlite-icon-text--icon">
                  <IconVue name="jhipster" aria-label="JHipster logo" title="JHipster website" />
                </span>
                <span class="jhlite-icon-text--text -lg-only">JHipster</span>
              </span>
            </a>
          </li>
          <li class="jhlite-nav--item">
            <a class="jhlite-nav-item" href="https://github.com/jhipster/jhipster-lite" target="_blank" rel="noopener">
              <span class="jhlite-icon-text">
                <span class="jhlite-icon-text--icon">
                  <IconVue name="github-circled" aria-label="GitHub logo" title="JHipster Lite GitHub page" />
                </span>
                <span class="jhlite-icon-text--text -lg-only">GitHub</span>
              </span>
            </a>
          </li>
          <li class="jhlite-nav--item">
            <a class="jhlite-nav-item" href="https://twitter.com/jhipster" target="_blank" rel="noopener">
              <span class="jhlite-icon-text">
                <span class="jhlite-icon-text--icon">
                  <IconVue name="twitter" aria-label="Twitter logo" title="JHipster Twitter" />
                </span>
                <span class="jhlite-icon-text--text -lg-only">Twitter</span>
              </span>
            </a>
          </li>
          <li class="jhlite-nav--item">
            <a class="jhlite-nav-item" href="https://opencollective.com/generator-jhipster" target="_blank" rel="noopener">
              <span class="jhlite-icon-text">
                <span class="jhlite-icon-text--icon">
                  <IconVue name="open-collective" aria-label="Open Collective logo" title="Open Collective JHipster page" />
                </span>
                <span class="jhlite-icon-text--text -lg-only">Open Collective</span>
              </span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  </header>
</template>

<script lang="ts" src="./Header.component.ts"></script>
