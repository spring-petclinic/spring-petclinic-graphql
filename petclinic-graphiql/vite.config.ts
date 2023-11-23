import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3081,
    proxy: {
      "/api": "http://localhost:9977",
      "/graphql": "http://localhost:9977",
      "/graphqlws": {
        target: "ws://localhost:9977",
        ws: true,
      },
    },
  },
});
