const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/auth': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      },
      '/termini': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      },
      '/zadaci': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
        logLevel: 'debug'
      }

    }
  }
});