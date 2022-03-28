const fs = require("fs");

const data = fs.readFileSync("./build/index-orig.html", "utf-8");

const { BACKEND_HOST } = process.env;

if (!BACKEND_HOST) {
  throw new Error("BACKEND_HOST not set!");
}

console.log({ BACKEND_HOST });

const newData = data
  .replaceAll("__BACKEND__", BACKEND_HOST)

fs.writeFileSync("./build/index.html", newData);
