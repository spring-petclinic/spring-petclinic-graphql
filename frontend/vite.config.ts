import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import path from "path";

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    port: 3080,
    proxy: {
      "/api": "http://localhost:9977",
      "/graphql": "http://localhost:9977",
      "/graphqlws": {
        target: "ws://localhost:9977",
        ws: true,
      },
    },
  },
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  plugins: [react()],
});
