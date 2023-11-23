import colors from "tailwindcss/colors";
import form from "@tailwindcss/forms";
/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  darkMode: "media",
  theme: {
    colors: {
      gray: colors.neutral,
      indigo: colors.indigo,
      red: colors.rose,
      yellow: colors.amber,
      "spr-white": "#fff",
      "spr-black": "#191e1e",
      "spr-gray-dark": "#333",
      "spr-blue": "#086dc3",
      "spr-green": "#6bb536",
      "spr-green-dark": "#458b17",
      "spr-green-light": "#ebf2f2",
      "spr-red": "#FF3C38",
    },
    fontFamily: {
      "open-sans": ["Open Sans", "sans-serif"],
      metro: ["Metropolis", "sans-serif"],
      helvetica: ["Helvetica", "Arial", "sans-serif"],
    },
    extend: {},
  },
  variants: {
    extend: {
      cursor: ["disabled"],
    },
  },
  plugins: [form],
};
